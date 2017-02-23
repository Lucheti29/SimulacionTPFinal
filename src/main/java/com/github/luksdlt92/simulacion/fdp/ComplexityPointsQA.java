package com.github.luksdlt92.simulacion.fdp;

import static java.lang.Math.*;

public class ComplexityPointsQA {

    public static double getPointsTestedPerHour() {
    	
    	double r = random();
    	double resParcial = pow( (1/(pow(r,1.88651)) - 1), 0.3894991041520604);
        
        return (0.10399 * resParcial + 15.176 ) / ( resParcial );
    }
}
