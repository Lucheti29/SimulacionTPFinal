package com.github.luksdlt92.simulacion.model.instance;

public class SimulationResults {
	
	private SimulationInstance simulation;
	private boolean algunEquipoFallo = Boolean.FALSE;
    private boolean algunEquipoOcioso = Boolean.FALSE;
    private double puntosNoCumplidos = 0;
    private double puntosSobrantes = 0;
    private double puntosNoProbados = 0;
	private double puntosSobrantesQa = 0;
    private int noCompletaPrioridad = 0;
    private int noCompletaComun = 0;
    private int qaOcioso = 0;
	private int sprintFallidos = 0;
	private int sprintOciosos = 0;
	//Resultados
	private double porcentajeSprintsFallidos = 0;
    private double porcentajeSprintsOciosos = 0;
    private double promedioPuntosSobrantesPorSprintYEquipo = 0;
    private double promedioPuntosFaltantesPorSprintYEquipo = 0;
    private double porcentajeQaNoCompletaPrioridad = 0;
    private double porcentajeQaNoCompletaComun = 0;
    private double porcentajeQaOcioso = 0;
    
    private double promedioPuntosSobrantesQa = 0;
    private double promedioPuntosFaltantesQa = 0;
    
    public SimulationResults(SimulationInstance simulation){
    	this.simulation=simulation;
    }

    public void calcularResultados() {    	
    	this.porcentajeSprintsFallidos = new Double(this.sprintFallidos) / this.simulation.getDeltaT() * 100;
    	this.porcentajeSprintsOciosos = new Double(this.sprintOciosos) / this.simulation.getDeltaT() * 100;
    	this.promedioPuntosSobrantesPorSprintYEquipo = this.puntosSobrantes / (this.simulation.getDeltaT() * this.simulation.getCantEquipos());
    	this.promedioPuntosFaltantesPorSprintYEquipo = this.puntosNoCumplidos / (this.simulation.getDeltaT() * this.simulation.getCantEquipos());
    	this.porcentajeQaNoCompletaPrioridad = new Double(this.noCompletaPrioridad) / this.simulation.getDeltaT() * 100;
    	this.porcentajeQaNoCompletaComun = new Double(this.noCompletaComun) / this.simulation.getDeltaT() * 100;
    	this.porcentajeQaOcioso = new Double(this.qaOcioso) / this.simulation.getDeltaT() * 100;
    	this.promedioPuntosSobrantesQa = this.puntosSobrantesQa / this.simulation.getDeltaT();
    	this.promedioPuntosFaltantesQa = this.puntosNoProbados / this.simulation.getDeltaT();
    }

    public void imprimir(){
    	System.out.println("Porcentaje de veces que QA no alcanzo a probar la totalidad de los puntos de prioridad: "+ porcentajeQaNoCompletaPrioridad);
    	System.out.println("Porcentaje de veces que QA no alcanzo a probar la totalidad de los puntos: "+ porcentajeQaNoCompletaComun);
    	System.out.println("Porcentaje de veces que algun equipo no pudo cumplir con la cantidad de puntos requeridos: " + porcentajeSprintsFallidos);
    	System.out.println("Porcentaje de veces que el equipo del proyecto estuvo ocioso: " + porcentajeSprintsOciosos);
    	System.out.println("Porcentaje de veces que QA estuvo ocioso: "+ porcentajeQaOcioso);
    	System.out.println("Promedio de sobrante de puntos de DEV: "+ promedioPuntosSobrantesPorSprintYEquipo);
    	System.out.println("Promedio de faltante de puntos de DEV: "+ promedioPuntosFaltantesPorSprintYEquipo);
    	System.out.println("Promedio de sobrante de puntos de QA: "+ promedioPuntosSobrantesQa);
    	System.out.println("Promedio de faltante de puntos de QA: "+ promedioPuntosFaltantesQa);
    	
    	System.out.println("Fallidos: "+this.sprintFallidos);
    	System.out.println("Ociosos: "+this.sprintOciosos);
    	System.out.println("Sprint total: "+this.simulation.getCantSprintsFinal());
    }

	public boolean isAlgunEquipoFallo() {
		return algunEquipoFallo;
	}

	public void setAlgunEquipoFallo(boolean algunEquipoFallo) {
		this.algunEquipoFallo = algunEquipoFallo;
	}

	public boolean isAlgunEquipoOcioso() {
		return algunEquipoOcioso;
	}

	public void setAlgunEquipoOcioso(boolean algunEquipoOcioso) {
		this.algunEquipoOcioso = algunEquipoOcioso;
	}

	public double getPuntosNoCumplidos() {
		return puntosNoCumplidos;
	}

	public void sumarPuntosNoCumplidos(double puntosNoCumplidos){
		this.puntosNoCumplidos += puntosNoCumplidos;
	}
	
	public double getPuntosSobrantes() {
		return puntosSobrantes;
	}

	public void sumarPuntosSobrantes(double puntosSobrantes) {
		this.puntosSobrantes += puntosSobrantes;
	}

	public double getPuntosNoProbados() {
		return puntosNoProbados;
	}

	public void sumarPuntosNoProbados(double puntosNoProbados) {
		this.puntosNoProbados += puntosNoProbados;
	}

	public int getNoCompletaPrioridad() {
		return noCompletaPrioridad;
	}

	public void increaseNoCompletaPrioridad(){
		this.noCompletaPrioridad ++;
	}

	public int getNoCompletaComun() {
		return noCompletaComun;
	}

	public void increaseNoCompletaComun(){
		this.noCompletaComun ++;
	}

	public int getQaOcioso() {
		return qaOcioso;
	}

	public void increaseQaOcioso(){
		this.qaOcioso ++;
	}

	public double getPuntosSobrantesQa() {
		return puntosSobrantesQa;
	}

	public void sumarPuntosSobrantesQa(double puntosSobrantesQa){
		this.puntosSobrantesQa += puntosSobrantesQa;
	}

	public int getSprintFallidos() {
		return sprintFallidos;
	}

	public void increaseSprintFallidos(){
		this.sprintFallidos++;
	}

	public int getSprintOciosos() {
		return sprintOciosos;
	}

	public void increaseSprintOciosos(){
		this.sprintOciosos++;
	}

	public double getPorcentajeSprintsFallidos() {
		return porcentajeSprintsFallidos;
	}

	public double getPorcentajeSprintsOciosos() {
		return porcentajeSprintsOciosos;
	}

	public double getPromedioPuntosSobrantesPorSprintYEquipo() {
		return promedioPuntosSobrantesPorSprintYEquipo;
	}

	public double getPromedioPuntosFaltantesPorSprintYEquipo() {
		return promedioPuntosFaltantesPorSprintYEquipo;
	}

	public double getPorcentajeQaNoCompletaPrioridad() {
		return porcentajeQaNoCompletaPrioridad;
	}

	public double getPorcentajeQaNoCompletaComun() {
		return porcentajeQaNoCompletaComun;
	}

	public double getPorcentajeQaOcioso() {
		return porcentajeQaOcioso;
	}
	
    public double getPromedioPuntosSobrantesQa(){
    	return this.promedioPuntosSobrantesQa;
    }
    public double getPromedioPuntosFaltantesQa(){
    	return this.promedioPuntosFaltantesQa;
    }
}
