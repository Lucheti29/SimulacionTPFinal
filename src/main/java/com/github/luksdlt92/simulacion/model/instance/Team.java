package com.github.luksdlt92.simulacion.model.instance;

import com.github.luksdlt92.simulacion.constant.Seniority;
import com.github.luksdlt92.simulacion.fdp.ComplexityPointsDev;

import java.util.Random;

public class Team {

	private static final int DAYS_PER_SPRINT = 10;
	
    private final int technology;
    private final int[] devs = new int[3];
    private final int hoursWork;

    //Los hago public para hacer mas rapido
    public double estimatedPoints = 0;
    public double developedPoints = 0;
    public double stockPuntos = 0;

    public Team(int technology, int devsSenior, int devsSemiSenior, int devsJunior, int hoursWork) {
        this.technology = technology;
        this.devs[Seniority.JUNIOR] = devsJunior;
        this.devs[Seniority.SEMISENIOR] = devsSemiSenior;
        this.devs[Seniority.SENIOR] = devsSenior;
        this.hoursWork = hoursWork;
    }

    /**
     * Calcula los puntos estimados por el equipo para el sprint
     * Tiene que llamarse por cada sprint
     */
    public double estimateSprint() {
        /*// for each seniority
        for (int seniority = 0; seniority < devs.length; seniority++) {
            // for each dev
            for (int i = 0; i < devs[seniority]; i++) {
                estimatedPoints += ComplexityPointsDev.getEstimatedPointsPerSprint(technology, seniority);
            }
        }*/
    	
    	/*estimatedPoints = new Double(ComplexityPointsDev.getEstimatedPointsPerSprint(this.technology, -1) 
    			* hoursWork * DAYS_PER_SPRINT * this.cantDevs() ).intValue();*/

        this.estimatedPoints = 0;

    	int i = 0;
    	while (i < cantDevs()) {
    	    i++;
    	    // El x10 es porque son 10 dias el sprint
            this.estimatedPoints += ComplexityPointsDev.getEstimatedPointsPerSprint(this.technology, hoursWork * 10);
        }
    	
    	//System.out.println("Estimated points: " + new Double(estimatedPoints).toString() );

        return this.estimatedPoints;
    }

    /**
     * Calcula los puntos efectivamente hechos por el equipo para el sprint
     * Tiene que llamarse por cada sprint, después de estimateSprint preferentemente
     */
    public double developSprint() {
    	
    	this.developedPoints = 0;
        // for each seniority
        for (int seniority = 0; seniority < devs.length; seniority++) {
            // for each dev
            for (int i = 0; i < devs[seniority]; i++) {
                // for each hour of work
                for (int i2 = 0; i2 < hoursWork * DAYS_PER_SPRINT * getRandom(0.9, 1); i2++) {
                    this.developedPoints += ComplexityPointsDev.getCompletedPointsPerHour(technology, seniority);
                }
            }
        }

        //developedPoints = new Double(ComplexityPointsDev.getEstimatedPointsPerSprint(this.technology, hoursWork)).intValue();
        
    	//System.out.println("Developed points: " + new Double(developedPoints).toString() );
    	
        return new Double(this.developedPoints).intValue();
    }

    private static double getRandom(double min, double max) {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }

    /**
     * Limpia los puntos estimados y desarrollados
     * Tiene que llamarse al finalizar un sprint o al iniciarse uno nuevo
     */
    public void cleanUp() {
        estimatedPoints = 0;
        developedPoints = 0;
    }
    
    private int cantDevs(){
    	int sum = 0;
    	for(int i=0;i<devs.length;i++){
    		sum += devs[i];
    	}
    	return sum;
    }
}
