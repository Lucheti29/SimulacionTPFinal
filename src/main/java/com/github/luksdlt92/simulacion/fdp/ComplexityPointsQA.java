package com.github.luksdlt92.simulacion.fdp;

import static java.lang.Math.*;

public class ComplexityPointsQA {

	private static final double COTA_SUPERIOR = 1000;
	private static final double COTA_INFERIOR = 0;
	
    public static double getPointsTestedPerHour() {
    	
    	double r = random();
    	double resParcial = pow( (1/(pow(r,1.88651)) - 1), 0.3894991041520604);
        
        double resultadoFinal = (0.10399 * resParcial + 15.176 ) / ( resParcial );
        
        if( resultadoFinal <= COTA_SUPERIOR && resultadoFinal >= COTA_INFERIOR) 
    		return resultadoFinal;
    	else 
    		return getPointsTestedPerHour();
    }
}
