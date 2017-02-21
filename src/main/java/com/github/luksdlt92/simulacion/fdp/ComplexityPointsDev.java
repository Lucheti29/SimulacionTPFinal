package com.github.luksdlt92.simulacion.fdp;

import com.github.luksdlt92.simulacion.constant.*;
import static java.lang.Math.*;

public class ComplexityPointsDev {

    public static double getEstimatedPointsPerSprint(int technology, int seniority) {
    	
    	double r = Math.random();
    	double u;
    	double o;
    	
    	if( technology == Technology.ANDROID){
    		
    		if( seniority == Seniority.JUNIOR ){
    			o = 0.08766;
    			u = 0.4473;
    			return r * tan(0.5 * PI * (2*o - 1) ) + u;
    		}
    		
    		if( seniority == Seniority.SENIOR ){
    			o = 0.32013;
    			u = 0.56187;
    			return u - r * log(-Math.log(o));
    		}
    		
    		if( seniority == Seniority.SEMISENIOR ){
    			double k = 0.21769;
    			o = 0.2153;
    			u = 0.40632;
    			return ( pow(-log(k), -r) * (o * pow(-log(k),r) - u*r * (pow(-log(k),r) - o))  )/ r;
    		}
    		
    	}
    	
        return 0;
    }

    public static double getCompletedPointsPerHour(int technology, int seniority) {
    	
    	
    	
        return 0;
    }
}
