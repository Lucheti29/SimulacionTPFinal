package com.github.luksdlt92.simulacion.model.instance;

import com.github.luksdlt92.simulacion.fdp.ComplexityPointsQA;

public class EquipoQA {

	private SimulationInstance2 simulation;
	
	public EquipoQA(SimulationInstance2 simulation){
    	this.simulation=simulation;
    }
	
	public void test(){
		double PPD = ComplexityPointsQA.getPointsTestedPerHour();
    	this.simulation.restarCPP(PPD); 
    	
    	if(! this.simulation.completaronPuntosPrioritarios()){ 
    		
    		this.simulation.increaseNoCompletaPrioridad();
    		this.simulation.sumarPuntosNoProbados(this.simulation.getCPP()+this.simulation.getCP()); 
    		
    		if(! this.simulation.completaronPuntosComunes()){
    			this.simulation.increaseNoCompletaComun();
	    	}
    		
    	} else {
    		
    		PPD = Math.negateExact(this.simulation.getCPP());
    		this.simulation.restarCP(PPD);
    		
    		if(! this.simulation.completaronPuntosComunes()){ //No completaron puntos comunes
    			this.simulation.increaseNoCompletaComun();
    			this.simulation.sumarPuntosNoProbados( this.simulation.getCP() );
    			
    		} else if(this.simulation.getCP()<0) {
    			
    			this.simulation.increaseQaOcioso();
    			this.simulation.sumarPuntosSobrantesQa( Math.negateExact( this.simulation.getCP()) );
    			
    			//Cantidad mayor a una hora TODO
    		}
    	}
    }
}
