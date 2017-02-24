package com.github.luksdlt92.simulacion.fdp;

import com.github.luksdlt92.simulacion.constant.Seniority;
import com.github.luksdlt92.simulacion.constant.Technology;
import static java.lang.Math.*;

public class ComplexityPointsDev {
	
	private static final double COTA_SUPERIOR = 3;
	private static final double COTA_INFERIOR = 0;

    public static double getEstimatedPointsPerSprint(int technology, int hoursWork) {

    	double r = Math.random();
    	double rParcial = 0;
		
    	if( technology == Technology.ANDROID )
    		rParcial = 100.8/pow( 1/pow(r,118.483) - 1 , 0.002660069693825978 ) * 0.01 * 0.63;
    	if( technology == Technology.IOS )
    		rParcial = 140.7 * pow( 1/pow(1 - r, 0.0200582) - 1, 0.1487187876444431 ) * 0.01 * 0.82;
    	if( technology == Technology.WEB )
    		rParcial = -107.522 * ( 0.0369413 - pow(1 - pow(1-r,0.380662),0.3364284753061499) ) * 0.01 * 0.7;

        return rParcial * hoursWork;
    }
    
    public static double getCompletedPointsPerHour(int technology, int seniority) {
    	
    	double r = Math.random();
    	double resultado = 0;
    	double b,u,o,k,y;
    	
    	if( technology == Technology.ANDROID){
    		
    		if( seniority == Seniority.JUNIOR ){
    			o = 0.08766;
    			u = 0.4473;
    			resultado = u - o * tan(1.5708 - PI*r); //r * tan(0.5 * PI * (2*o - 1) ) + u; //Cauchy
    		}
    		else if( seniority == Seniority.SENIOR ){
    			o = 0.32013;
    			u = 0.56187;
    			resultado = u - r * log(-log(o)); //Gumbel Max
    		}
    		else if( seniority == Seniority.SEMISENIOR ){
    			k = 0.21769;
    			o = 0.2153;
    			u = 0.40632;
    			resultado = (0.989021 - 0.582701 * pow(-log(r),0.21769) ) / ( pow(-log(r),0.21769) );
    			//Gen. Extreme Value TODO: Checkear si esta bien
    		}
    	}
    	else if( technology == Technology.IOS){
    		
    		if( seniority == Seniority.JUNIOR ){
    			k = 0.72588;
    			b = 0.62311;
    			resultado = b/pow( (1/pow(r,1.37764) - 1),0.237772546);//pow(-1 + pow(a,-1/k),-1/r) * b; //Dagum 3P
    		}
    		else if( seniority == Seniority.SENIOR ){
    			b = 0.56858;
    			y = 0.05016;
    			double z = 0.4281738385784628;
    			resultado = (0.05016 * pow(-log(r),z) + 0.78525 )/(pow(-log(r),z)); 
    			//pow(-log(a),-1/r) * (y * pow(-log(a),1/r) + b); //Frechet 3P
    		}
    		else if( seniority == Seniority.SEMISENIOR ){
    			b = 0.68126;
    			y = -0.0688;
    			resultado = (-b*r + b*y - y)/(b - 1); //Log-Logistic
    		}
    	}
    	else if( technology == Technology.WEB ){

    		if( seniority == Seniority.JUNIOR ){
    			k = 0.39185;
    			o = 0.27681;
    			u = 0.37305;
    			resultado = (0.706418 - 0.333368 * pow(-log(r), 0.39185) ) / pow(-log(r),0.39185);
    			//( pow(-log(k), -r) * (o * pow(-log(k),r) - u*r * (pow(-log(k),r) - o))  )/ r; //Gen. Extreme Value
    		}
    		else if( seniority == Seniority.SENIOR ){
    			k = 0.34258;
    			o = 0.392;
    			u = 0.3306;
    			resultado = -( pow(1-k,-r) * (o*pow(1-k,r) - u*r*pow(1-k,r) - o) )/r; //Gen Pareto
    		}
    		else if( seniority == Seniority.SEMISENIOR ){
    			b = 0.93766;
    			resultado = 0.93766 * pow(-log(1-r),0.4140272429925889); //b * pow(-log(1-a),1/r); //Weibull 2P
    		}
    	}
    	
    	if( resultado <= COTA_SUPERIOR && resultado >= COTA_INFERIOR) 
    		return resultado;
    	else 
    		return getCompletedPointsPerHour(technology, seniority);
    }
}
