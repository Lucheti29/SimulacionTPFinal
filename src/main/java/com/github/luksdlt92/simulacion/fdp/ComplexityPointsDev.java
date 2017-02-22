package com.github.luksdlt92.simulacion.fdp;

import com.github.luksdlt92.simulacion.constant.Seniority;
import com.github.luksdlt92.simulacion.constant.Technology;
import static java.lang.Math.*;

public class ComplexityPointsDev {

    public static double getEstimatedPointsPerSprint(int technology, int seniority) {

    	double r = Math.random();
    	double a, b;
    	a = 0.517; b = 0.906;
		r = r * (b - a) + a;
		
    	if( technology == Technology.ANDROID )
    		return r * 0.62;
    	if( technology == Technology.IOS )
    		return r * 0.78;
    	if( technology == Technology.WEB )
    		return r * 0.8;
    	
        return 0;
    }

    public static double getCompletedPointsPerHour(int technology, int seniority) {
    	
    	double r = Math.random();
    	double a,b,u,o,k,y;
    	
    	if( technology == Technology.ANDROID){
    		
    		if( seniority == Seniority.JUNIOR ){
    			o = 0.08766;
    			u = 0.4473;
    			return r * tan(0.5 * PI * (2*o - 1) ) + u; //Cauchy
    		}
    		
    		if( seniority == Seniority.SENIOR ){
    			o = 0.32013;
    			u = 0.56187;
    			return u - r * log(-log(o)); //Gumbel Max
    		}
    		
    		if( seniority == Seniority.SEMISENIOR ){
    			k = 0.21769;
    			o = 0.2153;
    			u = 0.40632;
    			return ( pow(-log(k), -r) * (o * pow(-log(k),r) - u*r * (pow(-log(k),r) - o))  )/ r; //Gen. Extreme Value TODO: Checkear si esta bien
    		}
    		
    	}
    	
    	if( technology == Technology.IOS){
    		
    		if( seniority == Seniority.JUNIOR ){
    			k = 0.72588;
    			a = 4.2057;
    			b = 0.62311;
    			return pow(-1 + pow(a,-1/k),-1/r) * b; //Dagum 3P
    		}
    		
    		if( seniority == Seniority.SENIOR ){
    			a = 2.3355;
    			b = 0.56858;
    			y = 0.05016;
    			return pow(-log(a),-1/r) * (y * pow(-log(a),1/r) + b); //Frechet 3P
    		}
    		
    		if( seniority == Seniority.SEMISENIOR ){
    			a = 2.8117;
    			b = 0.68126;
    			y = -0.0688;
    			return (-b*r + b*y - y)/(b - 1); //Log-Logistic
    		}
    	}
    	
    	if( technology == Technology.WEB ){
    		
    		if( seniority == Seniority.JUNIOR ){
    			k = 0.39185;
    			o = 0.27681;
    			u = 0.37305;
    			return ( pow(-log(k), -r) * (o * pow(-log(k),r) - u*r * (pow(-log(k),r) - o))  )/ r; //Gen. Extreme Value
    		}
    		
    		if( seniority == Seniority.SENIOR ){
    			k = 0.34258;
    			o = 0.392;
    			u = 0.3306;
    			return -( pow(1-k,-r) * (o*pow(1-k,r) - u*r*pow(1-k,r) - o) )/r; //Gen Pareto
    		}
    		
    		if( seniority == Seniority.SEMISENIOR ){
    			a = 2.4153;
    			b = 0.93766;
    			return b * pow(-log(1-a),1/r); //Weibull 2P
    		}
    	}
        return 0;
    }
}
