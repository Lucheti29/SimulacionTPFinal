package com.github.luksdlt92.simulacion.model.instance;

public class SimulationResults {
	
	private SimulationInstance simulation;
	private boolean algunEquipoFallo = Boolean.FALSE;
    private boolean algunEquipoOcioso = Boolean.FALSE;
    private int puntosNoCumplidos = 0;
    private int puntosSobrantes = 0;
    private int puntosNoProbados = 0;
	private int puntosSobrantesQa = 0;
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
    	this.porcentajeSprintsFallidos = this.sprintFallidos / this.simulation.getCantSprintsFinal() * 100;
    	this.porcentajeSprintsOciosos = this.sprintOciosos / this.simulation.getCantSprintsFinal() * 100;
    	this.promedioPuntosSobrantesPorSprintYEquipo = this.puntosSobrantes / (this.simulation.getCantSprintsFinal() * this.simulation.getCantEquipos());
    	this.promedioPuntosFaltantesPorSprintYEquipo = this.puntosNoCumplidos / (this.simulation.getCantSprintsFinal() * this.simulation.getCantEquipos());
    	this.porcentajeQaNoCompletaPrioridad = this.noCompletaPrioridad / this.simulation.getCantSprintsFinal() * 100;
    	this.porcentajeQaNoCompletaComun = this.noCompletaComun / this.simulation.getCantSprintsFinal() * 100;
    	this.porcentajeQaOcioso = this.qaOcioso / this.simulation.getCantSprintsFinal() * 100;
    	this.promedioPuntosSobrantesQa = this.puntosSobrantesQa / this.simulation.getCantSprintsFinal();
    	this.promedioPuntosFaltantesQa = this.puntosNoProbados / this.simulation.getCantSprintsFinal();
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

	public int getPuntosNoCumplidos() {
		return puntosNoCumplidos;
	}

	public void sumarPuntosNoCumplidos(int puntosNoCumplidos){
		this.puntosNoCumplidos += puntosNoCumplidos;
	}
	
	public int getPuntosSobrantes() {
		return puntosSobrantes;
	}

	public void sumarPuntosSobrantes(int puntosSobrantes) {
		this.puntosSobrantes += puntosSobrantes;
	}

	public int getPuntosNoProbados() {
		return puntosNoProbados;
	}

	public void sumarPuntosNoProbados(int puntosNoProbados) {
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

	public void sumarPuntosSobrantesQa(int puntos){
		this.puntosSobrantesQa += puntos;
	}
}
