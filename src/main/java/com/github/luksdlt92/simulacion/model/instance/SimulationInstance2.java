package com.github.luksdlt92.simulacion.model.instance;

import com.github.luksdlt92.simulacion.constant.Seniority;
import com.github.luksdlt92.simulacion.constant.TechnologyEnum;

import java.util.*;

/*
 * SimulationInstance: es la transcripcion tal cual del diagrama, al estilo Alfiero
 * SimulationInstance2: algo más civilizada, no al estilo Alfiero
 */
public class SimulationInstance2 {

	private static final double PORCENTAJE_DE_PRIORIDAD = 0.1;
	private static final int HOURS_DEV_WORK_PER_DAY = 6;

	private int CPP;// Cantidad de puntos de complejidad con prioridad a probar por QA
	private int CP; // Cantidad de puntos de complejidad a probar por QA
	private int CPD[][]; // Cantidad de puntos de complejidad a desarrollar por equipo
	
	private final int qaPeopleAmount; //Equipo de QA
    private final int[] projectsAmount; //[technology]
    private final int[][] technologySeniorities; //[technology][seniority]
    private final int cantSprintsFinal; //TODO agregar a las opciones 
    private int deltaT = 0;
    private final SimulationResults results;

    private final Map<Integer, List<Team>> teamsPerTech = new HashMap<Integer, List<Team>>();

    private SimulationInstance2(int peopleAmount, int[] projectsAmount, int[][] technologySeniorities, int cantSprintsFinal) {
        this.qaPeopleAmount = peopleAmount;
        this.projectsAmount = projectsAmount;
        this.technologySeniorities = technologySeniorities;
        this.cantSprintsFinal = cantSprintsFinal;
        this.results = new SimulationResults(this);
    }
    
    public void run() {
        System.out.println("Inicializando simulacion");
        init();

		System.out.println("Corriendo simulacion");
        while (deltaT <= cantSprintsFinal) {
        	deltaT++;

        	// Se calculan los puntos a estimar por cada equipo y cuánto efectivamente hicieron
			// Luego se actualiza CPD para calcular los resultados
        	for (int technology : this.teamsPerTech.keySet()) {
				int i = 0;
				for (Team team : this.teamsPerTech.get(technology)) {
					this.CPD[technology][i] = team.estimateSprint(); // Se setean los puntos estimados
					this.CPD[technology][i] = this.CPD[technology][i] - team.developSprint(); // Se restan los puntos hechos
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
						sumarPuntosSobrantes(this.CPD[technology][team] * -1);
						pointsPerTeam = this.CPD[technology][team];
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
        	
        	EquipoQA qa = new EquipoQA(this);//SACAR instanciacion DEL WHILE. Poner como var de inst y en iniciarlizar() TODO
        	qa.test();
        	
        	if( this.results.isAlgunEquipoFallo() ){
        		this.results.increaseSprintFallidos();
        	}
        	if( this.results.isAlgunEquipoOcioso() ){
        		this.results.increaseSprintOciosos();
        	}
        }
        
        this.results.calcularResultados();
        this.results.imprimir();
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

	public int getQAPeopleAmount() {
		return this.qaPeopleAmount;
	}
	public int[] getProjectsAmount() {
		return this.projectsAmount;
	}
	public int[][] getTechnologySeniorities() {
		return this.technologySeniorities;
	}
    public int getCantEquipos() {
    	int cantEquipos = 0;
    	for(TechnologyEnum unaTecnologia : TechnologyEnum.values()){
    		cantEquipos += this.projectsAmount[unaTecnologia.getId()];
    	}
		return cantEquipos;
	}
    public int getCantEquipos(int iTech) {
    	int cantEquipos = this.projectsAmount[ iTech ];
		return cantEquipos;
	}
    
    public int getCantSprintsFinal() {
		return this.cantSprintsFinal;
	}
    
	public int getCPD(int tech, int sen){
		return this.CPD[tech][sen];
	}
	public void sumarCPD(int tech, int sen, double puntos){
		this.CPD[tech][sen] += puntos;
	}
	public void restarCPD(int tech, int sen, double puntos){
		this.CPD[tech][sen] -= puntos;
	}
	public void limpiarCPD(int tech, int sen){
		this.CPD[tech][sen] = 0;
	}
	
	public int getCP(){
		return this.CP;
	}
	public void sumarCP(double puntos){
		this.CP += puntos;
	}
	public void restarCP(double puntos){
		this.CP -= puntos;
	}
	public void limpiarCP(){
		this.CP = 0;
	}
	public boolean completaronPuntosComunes(){
		return this.CP <= 0;
	}
	
	public int getCPP(){
		return this.CPP;
	}
	public void sumarCPP(double puntos){
		this.CPP += puntos;
	}
	public void restarCPP(double puntos){
		this.CPP -= puntos;
	}
	public void limpiarCPP(){
		this.CPP = 0;
	}
	public boolean completaronPuntosPrioritarios(){
		return this.CPP <= 0;
	}
	
	public void setAlgunEquipoFallo(boolean bol){
		this.results.setAlgunEquipoFallo(bol);
	}
	
	public void setAlgunEquipoOcioso(boolean bol){
		this.results.setAlgunEquipoOcioso(bol);
	}
	
	public void sumarPuntosNoCumplidos(int puntos){
		this.results.sumarPuntosNoCumplidos(puntos);
	}
	
	public void sumarPuntosSobrantes(int puntos){
		this.results.sumarPuntosSobrantes(puntos);
	}
	
	public void increaseNoCompletaPrioridad(){
		this.results.increaseNoCompletaPrioridad();
	}
	
	public void increaseNoCompletaComun(){
		this.results.increaseNoCompletaComun();
	}
	
	public void sumarPuntosNoProbados(int puntos){
		this.results.sumarPuntosNoProbados(puntos);
	}
	
	public void increaseQaOcioso(){
		this.results.increaseQaOcioso();
	}
	
	public void sumarPuntosSobrantesQa(double puntos){
		this.results.sumarPuntosSobrantesQa(puntos);
	}

	public static class Builder {

		private int cantSprintsFinal;
		private int mQAPeopleAmount;
		private int[] mProjectsAmount = new int[3];
		private int[][] mTechnologySeniorities = new int[3][3];

		private Builder() {}

		public static Builder newInstance() {
			return new Builder();
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

		public SimulationInstance2 build() {
			System.out.println("La cantidad de gente de QA es " + this.mQAPeopleAmount);

			for (int i = 0; i < this.mProjectsAmount.length; i++) {
				System.out.println("En la tecnologia " + i + " hay " + this.mProjectsAmount[i]);
			}


			for (int i = 0; i < this.mTechnologySeniorities.length; i++) {
				for (int i2 = 0; i2 < this.mTechnologySeniorities[i].length; i2++) {
					System.out.println("En la tecnologia " + i + " hay " + this.mTechnologySeniorities[i][i2] + " de nivel " + i2);
				}
			}

			return new SimulationInstance2(this.mQAPeopleAmount, this.mProjectsAmount, this.mTechnologySeniorities, this.cantSprintsFinal);
		}
	}
}
