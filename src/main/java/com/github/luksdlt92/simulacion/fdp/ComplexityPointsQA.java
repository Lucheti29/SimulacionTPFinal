package com.github.luksdlt92.simulacion.fdp;

import static java.lang.Math.*;

public class ComplexityPointsQA {

    public static double getPointsTestedPerHour() {
    	
    	double k = 0.53008;
    	double a = 2.5674;
    	double b = 15.176;
    	double y = 0.10339;
    	double r = random();
    	
        return pow( (pow(a,-1/k)), -1/r) * (y * pow( (pow(a, -1/k) - 1), 1/r) + b); //Dagum 4P
    }
}
