package com.github.luksdlt92.simulacion.model.instance;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.luksdlt92.simulacion.constant.Seniority;
import com.github.luksdlt92.simulacion.csv.CSVUtil;
import com.github.luksdlt92.simulacion.fdp.ComplexityPointsQA;

public class SimulationInstance {

	private static final double PORCENTAJE_DE_PRIORIDAD = 0.1;
	private static final int HOURS_DEV_WORK_PER_DAY = 6;
	private static final int HOURS_QA_WORK_PER_DAY = 8;
	private static final int SPRINTS = 10000;
	
	private int CPP;// Cantidad de puntos de complejidad con prioridad a probar por QA
	private int CP; // Cantidad de puntos de complejidad a probar por QA
	private int CPD[][]; // Cantidad de puntos de complejidad a desarrollar por equipo
	
	private final int stageId;
	private final int qaPeopleAmount; //Equipo de QA
    private final int[] projectsAmount; //[technology]
    private final int[][] technologySeniorities; //[technology][seniority]
    private final int cantSprintsFinal; //TODO agregar a las opciones 
    private int deltaT = 0;
    private final SimulationResults results;

    private final Map<Integer, List<Team>> teamsPerTech = new HashMap<Integer, List<Team>>();

    private SimulationInstance(int peopleAmount, int[] projectsAmount, int[][] technologySeniorities, int cantSprintsFinal, int stageId) {
        this.qaPeopleAmount = peopleAmount;
        this.projectsAmount = projectsAmount;
        this.technologySeniorities = technologySeniorities;
        this.cantSprintsFinal = SPRINTS; // TODO: cambiar por algo configurable
        this.results = new SimulationResults(this);
        this.stageId = stageId;
    }

    public void run() {
		System.out.println("Inicializando simulacion");
		init();

		System.out.println("Corriendo simulacion");
		simulate();

		System.out.println("Resultados");
		results();
	}

	private void init() {
		// Inicialización de las variables de estado
		this.CPP = 0;
		this.CP = 0;
		this.CPD = new int[3][]; // El 3 es por las tecnologías

		// ---------- Teams init ----------
		// for each technology
		for (int technology = 0; technology < projectsAmount.length; technology++) {
			int projectsPerTech = 0;
			List<Team> teams = new LinkedList<Team>();
			// for project
			for (int i = 0; i < projectsAmount[technology]; i++) {
				projectsPerTech++;
				teams.add(new Team(technology, technologySeniorities[technology][Seniority.SENIOR],
						technologySeniorities[technology][Seniority.SEMISENIOR],
						technologySeniorities[technology][Seniority.JUNIOR],
						HOURS_DEV_WORK_PER_DAY));
			}
			this.teamsPerTech.put(technology, teams);
			this.CPD[technology] = new int[projectsPerTech]; // Recién acá tenemos la cantidad de proyectos por tecnología
		}
		// ---------- End Teams init ----------
	}
    
    private void simulate() {
    	
    	// ---------- Open CSV ----------
    	FileWriter writer = CSVUtil.openCsv(this.stageId);
    	
        while (deltaT <= cantSprintsFinal) {
        	deltaT++;

        	resetBooleans();
        	
			// ---------- Start devs ----------
        	// Se calculan los puntos a estimar por cada equipo y cuánto efectivamente hicieron
			// Luego se actualiza CPD para calcular los resultados
        	for (int technology : this.teamsPerTech.keySet()) {
				int i = 0;
				for (Team team : this.teamsPerTech.get(technology)) {
					this.CPD[technology][i] += team.estimateSprint(); // Se setean los puntos estimados
					this.CPD[technology][i] -= team.developSprint(); // Se restan los puntos hechos
					team.cleanUp();
					i++;
				}
			}

			// Se procesan los datos del CPD para empezar a obtener resultados
			// for each technology
			for (int technology = 0; technology < this.CPD.length; technology++) {
        		// for each team
				for (int team = 0; team < this.CPD[technology].length; team++) {
					int pointsPerTeam = 0;

					// Se calcula si hubo puntos sobrantes o no
					if (this.CPD[technology][team] > 0) {
						setAlgunEquipoFallo(Boolean.TRUE);
						sumarPuntosNoCumplidos(this.CPD[technology][team]);
					} else if (this.CPD[technology][team] < 0) {
						setAlgunEquipoOcioso(Boolean.TRUE);
						// Al hacer la resta de estimados menos hechos
						// Si hay más hechos, el número es negativo
						// Por ende, es necesario multiplicarlo por -1
						sumarPuntosSobrantes(Math.abs(this.CPD[technology][team]));
						pointsPerTeam = Math.abs(this.CPD[technology][team]);
						this.CPD[technology][team] = 0;
					}

					// Se destinan los puntos a los stocks de QA
					if (Math.random() < PORCENTAJE_DE_PRIORIDAD) {
						sumarCPP(pointsPerTeam);
					} else {
						sumarCP(pointsPerTeam);
					}
				}
			}

			if (this.results.isAlgunEquipoFallo()) {
				this.results.increaseSprintFallidos();
			}

			if (this.results.isAlgunEquipoOcioso()) {
				this.results.increaseSprintOciosos();
			}
			// ---------- End devs ----------

			calcularQa();
/*			// ---------- Start QA ----------
			// Se calcula el total de horas por día que usa QA para test
        	int qaHoursWork = HOURS_QA_WORK_PER_DAY * qaPeopleAmount;

        	// Se testean los puntos prioritarios
        	while (this.CPP > 0 && qaHoursWork > 0) {
				this.CPP -= ComplexityPointsQA.getPointsTestedPerHour();
				qaHoursWork--;
			}

			if (this.CPP > 0) {
        		// Quedaron puntos prioritarios por probar
				increaseNoCompletaPrioridad();
				sumarPuntosNoProbados(this.CPP + this.CP);
				increaseNoCompletaComun();
			} else if (qaHoursWork > 0) {
        		// Se terminaron los puntos prioritarios y quedan horas de QA
				// Se testean los puntos no prioritarios
				while (this.CP > 0 && qaHoursWork > 0) {
					this.CP -= ComplexityPointsQA.getPointsTestedPerHour();
					qaHoursWork--;
				}
				
				if (this.CP > 0) {
					// Quedaron puntos comunes por probar
					increaseNoCompletaComun();
					sumarPuntosNoProbados(this.CP);
				} else if (qaHoursWork > 0) {
					// Quedaron horas sobrantes de QA
					increaseQaOcioso();
					sumarPuntosSobrantesQa(Math.abs(this.CP));
				}
			}

			// Se resetean las variables 
			//this.CPP = 0;
			//this.CP = 0;
			
			// ---------- End QA ----------
*/        
        	// ---------- Start Write Line CSV ----------
        	this.results.calcularResultados();
        	CSVUtil.writeLine(writer, this.deltaT, this.results);
        	// ---------- End Write Line CSV ----------
        }
        
        // ---------- Close CSV ----------
        CSVUtil.closeCsv(writer);
        // ---------- Close CSV ----------
    }

    private void results() {
		this.results.calcularResultados();
		this.results.imprimir();
	}

	private void sumarCP(double puntos){
		this.CP += puntos;
	}

	private void sumarCPP(double puntos){
		this.CPP += puntos;
	}
	
	private void setAlgunEquipoFallo(boolean bol){
		this.results.setAlgunEquipoFallo(bol);
	}
	
	private void setAlgunEquipoOcioso(boolean bol){
		this.results.setAlgunEquipoOcioso(bol);
	}
	
	private void sumarPuntosNoCumplidos(int puntos){
		this.results.sumarPuntosNoCumplidos(puntos);
	}
	
	private void sumarPuntosSobrantes(int puntos){
		this.results.sumarPuntosSobrantes(puntos);
	}
	
	private void increaseNoCompletaPrioridad(){
		this.results.increaseNoCompletaPrioridad();
	}
	
	private void increaseNoCompletaComun(){
		this.results.increaseNoCompletaComun();
	}
	
	private void sumarPuntosNoProbados(int puntos){
		this.results.sumarPuntosNoProbados(puntos);
	}
	
	public void increaseQaOcioso(){
		this.results.increaseQaOcioso();
	}
	
	public void sumarPuntosSobrantesQa(double puntos){
		this.results.sumarPuntosSobrantesQa(puntos);
	}

	public int getCantSprintsFinal() {
		return this.cantSprintsFinal;
	}

	public int getCantEquipos() {
		int amount = 0;
		for (List<Team> teams : teamsPerTech.values()) {
			amount += teams.size();
		}

		return amount;
	}

	public static class Builder {

		private int stageId;
		private int cantSprintsFinal;
		private int mQAPeopleAmount;
		private int[] mProjectsAmount = new int[3];
		private int[][] mTechnologySeniorities = new int[3][3];

		private Builder() {}

		public static Builder newInstance() {
			return new Builder();
		}

		public Builder setStageId(int stageId) {
			this.stageId = stageId;
			return this;
		}
		
		public Builder setQAPeopleAmount(int peopleAmount) {
			this.mQAPeopleAmount = peopleAmount;
			return this;
		}

		public Builder setProjectsAmount(int technology, int projectsAmount) {
			this.mProjectsAmount[technology] = projectsAmount;
			return this;
		}

		public Builder setSeniorityAmount(int technology, int seniority, int amount) {
			this.mTechnologySeniorities[technology][seniority] = amount;
			return this;
		}

		public SimulationInstance build() {
			System.out.println("La cantidad de gente de QA es " + this.mQAPeopleAmount);

			for (int i = 0; i < this.mProjectsAmount.length; i++) {
				System.out.println("En la tecnologia " + i + " hay " + this.mProjectsAmount[i]);
			}


			for (int i = 0; i < this.mTechnologySeniorities.length; i++) {
				for (int i2 = 0; i2 < this.mTechnologySeniorities[i].length; i2++) {
					System.out.println("En la tecnologia " + i + " hay " + this.mTechnologySeniorities[i][i2] + " de nivel " + i2);
				}
			}

			return new SimulationInstance(this.mQAPeopleAmount, this.mProjectsAmount, this.mTechnologySeniorities, this.cantSprintsFinal, this.stageId);
		}
	}
	
	private void calcularQa(){
		
		// ---------- Start QA ----------
		// Se calcula el total de horas por día que usa QA para test
	   	int qaHoursWork = HOURS_QA_WORK_PER_DAY * qaPeopleAmount;
	   	int puntosProbados = 0;
	   	// Se testean los puntos prioritarios
	   	for(int i=0;i<qaHoursWork;i++){
	   		puntosProbados += new Double(ComplexityPointsQA.getPointsTestedPerHour()).intValue();
	   	}
	   	
	   	//resto puntosProbados a CPP
	   	this.CPP -= puntosProbados;
	   	
	   	if(this.CPP > 0){
	   		//No alcanzo a completar prioridad
	   		increaseNoCompletaPrioridad();
	   		increaseNoCompletaComun();
	   		sumarPuntosNoProbados(-this.CPP + this.CP); //-cpp porque esta en negativo
	   	}else{
	   		//Alcanzo a completar prioridad
	   		
	   		this.CPP = 0; //Vuelvo a 0 el valor de CPP en caso de que este en negativo
	   		
	   		this.CP -= puntosProbados;
	   		
	   		if(this.CP > 0){
	   			//No alcanzo a completar comun
	   			increaseNoCompletaComun();
	   			sumarPuntosNoProbados(this.CP);
	   		}else if(this.CP < 0){
	   			//QA Ocioso
	   			increaseQaOcioso();
	   			sumarPuntosSobrantesQa(-this.CP); //el - porque esta negativo
	   			
	   			this.CP = 0; //Vuelvo el valor a 0
	   		}
	   	}
		
	}
	
	private void resetBooleans(){
		setAlgunEquipoOcioso(Boolean.FALSE);
		setAlgunEquipoFallo(Boolean.FALSE);
	}
}
