package com.github.luksdlt92.simulacion.model.instance;

import com.github.luksdlt92.simulacion.fdp.ComplexityPointsDev;

public class Developer {
	
	private SimulationInstance2 simulation;
	private int project;
	private int seniority;
	//Estas dos sacar en un objeto
	private int technology;
	private double porcentajePrioridad; 
	
	public Developer(SimulationInstance2 simulation, int project, int technology, int seniority, double porcentajePrioridad){
		this.simulation = simulation;
		this.setProject(project);
		this.seniority = seniority;
		this.technology = technology;
		this.porcentajePrioridad = porcentajePrioridad;
	}
	
	public void desarrollar(){
		double IPD = ComplexityPointsDev.getEstimatedPointsPerSprint(this.technology);
		this.simulation.sumarCPD(this.technology, this.seniority, IPD);
    	
    	double DP = ComplexityPointsDev.getCompletedPointsPerHour(this.technology, this.seniority);
		this.simulation.restarCPD(this.technology, this.seniority, DP);
    	
    	if(this.simulation.getCPD(this.technology, this.seniority) > 0){
    		
    		this.simulation.setAlgunEquipoFallo(Boolean.TRUE);
    		this.simulation.sumarPuntosNoCumplidos(this.simulation.getCPD(this.technology, this.seniority));
    	}
    	
    	if(this.simulation.getCPD(this.technology, this.seniority) < 0){
    		
    		this.simulation.setAlgunEquipoOcioso(Boolean.TRUE);
    		this.simulation.sumarPuntosSobrantes(this.simulation.getCPD(this.technology, this.seniority));
    		DP -= this.simulation.getCPD(this.technology, this.seniority);
    		this.simulation.limpiarCPD(this.technology, this.seniority); 
    	}
    	
    	double R = Math.random();
    	if(R > this.porcentajePrioridad)
    		this.simulation.sumarCP(DP);
    	else
    		this.simulation.sumarCPP(DP);
	}

	public int getProject() {
		return project;
	}

	public void setProject(int project) {
		this.project = project;
	}
}
