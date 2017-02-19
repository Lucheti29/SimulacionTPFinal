package com.github.luksdlt92.simulacion.model.instance;

import com.github.luksdlt92.simulacion.constant.Technology;
import com.github.luksdlt92.simulacion.fdp.ComplexityPointsDev;
import com.github.luksdlt92.simulacion.fdp.ComplexityPointsQA;

@SuppressWarnings("unused")//TODO remove
public class SimulationInstance {
	
	private int CPP;//Cantidad de puntos de complejidad con prioridad a probar por QA
	private int CP; //Cantidad de puntos de complejidad a probar por QA
	private int CPD[][]; //Cantidad de puntos de complejidad a desarrollar por equipo
	private final int cantSprintsFinal = 100000; //TODO agregar a las opciones 
	private final int mQAPeopleAmount; //Equipo de QA
    private final int[] mProjectsAmount; //[technology]
    private final int[][] mTechnologySeniorities; //[technology][seniority]
    
    private int deltaT = 0;
    private boolean algunEquipoFallo = Boolean.FALSE;
    private boolean algunEquipoOcioso = Boolean.FALSE;
    private int puntosNoCumplidos = 0;
    private int puntosSobrantes = 0;
    private int puntosNoProbados = 0;
    private int noCompletaPrioridad = 0;
    private int noCompletaComun = 0;
    private int qaOcioso = 0;
	private double puntosSobrantesQa = 0;
	private int sprintFallidos = 0;
	private int sprintOciosos = 0;
    private final double ANDROID_PORCENTAJE_PRIORIDAD = 10;
    private final double IOS_PORCENTAJE_PRIORIDAD = 10;
    private final double WEB_PORCENTAJE_PRIORIDAD = 10;
    //Resultados
    private double porcentajeSprintsFallidos = 0;
    private double porcentajeSprintsOciosos = 0;
    private double promedioPuntosSobrantesPorSprintYEquipo = 0;
    private double promedioPuntosFaltantesPorSprintYEquipo = 0;
    private double porcentajeQaNoCompletaPrioridad = 0;
    private double porcentajeQaNoCompletaComun = 0;
    private double porcentajeQaOcioso = 0;

    private SimulationInstance(int peopleAmount, int[] projectsAmount, int[][] technologySeniorities) {
        this.mQAPeopleAmount = peopleAmount;
        this.mProjectsAmount = projectsAmount;
        this.mTechnologySeniorities = technologySeniorities;
    }
    
    public void run() {
        System.out.println("Running");
        
        inicializar(); //TODO
        
        while(deltaT <= cantSprintsFinal){
        	deltaT++;
        	
        	algunEquipoFallo = Boolean.FALSE;
        	for (int iProject = 0; iProject < this.mProjectsAmount[Technology.ANDROID]; iProject++) {
        		for (int iSeniority = 0; iSeniority < this.mTechnologySeniorities[Technology.ANDROID].length; iSeniority++) {
        			desarrollo(iSeniority);
        		}
        	}
        	pruebaQa();
        }
        
        calcularResultados();
        imprimir();
    }
    
    private void inicializar(){
    	
    }
    
    private void desarrollo(int iSeniority){
    	double IPD = ComplexityPointsDev.getEstimatedPointsPerSprint(Technology.ANDROID);
    	CPD[Technology.ANDROID][iSeniority] += IPD;
    	
    	double DP = ComplexityPointsDev.getCompletedPointsPerHour(Technology.ANDROID, iSeniority);
    	CPD[Technology.ANDROID][iSeniority] -= DP;
    	
    	if(CPD[Technology.ANDROID][iSeniority] > 0){
    		algunEquipoFallo = Boolean.TRUE;
    		puntosNoCumplidos += CPD[Technology.ANDROID][iSeniority];
    	}
    	if(CPD[Technology.ANDROID][iSeniority] < 0){
    		algunEquipoOcioso = Boolean.TRUE;
    		puntosSobrantes += CPD[Technology.ANDROID][iSeniority];
    		DP -= CPD[Technology.ANDROID][iSeniority];
    		CPD[Technology.ANDROID][iSeniority] = 0;
    	}
    	
    	double R = Math.random();
    	if(R > ANDROID_PORCENTAJE_PRIORIDAD)
    		CP += DP;
    	else
    		CPP += DP;
    }
    private void pruebaQa(){
    	double PPD = ComplexityPointsQA.getPointsTestedPerHour();
    	CPP -= PPD;
    	
    	if(CPP>0){//No completaron puntos prioritarios
    		noCompletaPrioridad++;
    		puntosNoProbados += CPP + CP;
    		if(CP > 0)
    			noCompletaComun++;
    	} else {
    		PPD = -CPP;
    		CP -= PPD;
    		
    		if(CP>0){ //No completaron puntos comunes
    			noCompletaComun++;
    			puntosNoProbados+=CP;
    		} else if(CP<0) {
    			qaOcioso++;
    			puntosSobrantesQa += -CP;
    			//Cantidad mayor a una hora TODO
    		}
    	}
    	
    	if(algunEquipoFallo){
    		sprintFallidos++;
    	}
    	if(algunEquipoOcioso){
    		sprintOciosos++;
    	}
    }
    private void calcularResultados(){
    	int cantEquipos = mProjectsAmount.length; //TODO esto es asi ?
    	
    	porcentajeSprintsFallidos = sprintFallidos / cantSprintsFinal* 100;
	    porcentajeSprintsOciosos = sprintOciosos / cantSprintsFinal * 100;
	    promedioPuntosSobrantesPorSprintYEquipo = puntosSobrantes / (cantSprintsFinal * cantEquipos);
	    promedioPuntosFaltantesPorSprintYEquipo = puntosNoCumplidos / (cantSprintsFinal * cantEquipos);
	    porcentajeQaNoCompletaPrioridad = noCompletaPrioridad / cantSprintsFinal * 100;
	    porcentajeQaNoCompletaComun = noCompletaComun / cantSprintsFinal * 100;
	    porcentajeQaOcioso = qaOcioso / cantSprintsFinal * 100;
    }
    private void imprimir(){
    	System.out.println(": " + porcentajeSprintsFallidos);
    	System.out.println(": " + porcentajeSprintsOciosos);
    	System.out.println(": "+ promedioPuntosSobrantesPorSprintYEquipo);
    	System.out.println(": "+ promedioPuntosFaltantesPorSprintYEquipo);
    	System.out.println(": "+ porcentajeQaNoCompletaPrioridad);
    	System.out.println(": "+ porcentajeQaNoCompletaComun);
    	System.out.println(": "+ porcentajeQaOcioso);
    }
    
    public static class Builder {

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

            return new SimulationInstance(this.mQAPeopleAmount, this.mProjectsAmount, this.mTechnologySeniorities);
        }
    }

	public int getmQAPeopleAmount() {
		return this.mQAPeopleAmount;
	}

	public int[] getmProjectsAmount() {
		return this.mProjectsAmount;
	}

	public int[][] getmTechnologySeniorities() {
		return this.mTechnologySeniorities;
	}

}
