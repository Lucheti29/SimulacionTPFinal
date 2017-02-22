package com.github.luksdlt92.simulacion.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import com.github.luksdlt92.simulacion.model.instance.SimulationResults;

public class CSVUtil {

	private static final char DEFAULT_SEPARATOR = ',';
	private static final char DEFAULT_QUOTE = '"';
	private static final String DIRECTORY = "csv/";
	private static final String DEFAULT_PARTIAL_FILE_NAME = "resultados_stage_";
	private static final String[] HEADERS = {"deltaT", 
											 "porcentajeQaNoCompletaPrioridad", 
											 "porcentajeQaNoCompletaComun",
											 "porcentajeSprintsFallidos",
											 "porcentajeSprintsOciosos",
											 "porcentajeQaOcioso",
											 "promedioPuntosSobrantesPorSprintYEquipo",
											 "promedioPuntosFaltantesPorSprintYEquipo"}; 
	
	
	
	public static FileWriter openCsv(int stageId) {
		FileWriter writer;
		String fileName = getFileName(stageId);
		try {
			File dir = new File(DIRECTORY);
			if (!dir.exists())
				dir.mkdirs();
			String completePath = dir.getAbsolutePath() + File.separator + fileName;
			File csv = new File(completePath);
			writer = new FileWriter(csv, Boolean.TRUE);
			return writer;
		} catch (IOException e) {
			System.out.println("Ocurrió un error abriendo el CSV \n" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public static FileWriter closeCsv(FileWriter writer) {
		if(writer == null) return null;
		
		try {
			writer.flush();
			writer.close();
			return writer;
		} catch (IOException e) {
			System.out.println("Ocurrió un error cerrando el CSV");
		}
		return null;
	}

	/**
	 * @desc 			Recibe un FileWriter del CSV abierto. 
	 * 					No abre, ni cierra el archivo. Solo escribe una linea en el writer 
	 *					Si es el primer deltaT agrega linea de headers. 
	 * 
	 * @param writer	
	 * @param deltaT	delta T del instante que se calcularon los resultados
	 * @param r			directamente el objeto de resultados de SimulationInstance
	 */
	public static void writeLine(FileWriter writer, Integer deltaT, SimulationResults r) {
		List<String> values = Arrays.asList(String.valueOf(deltaT), 
											String.valueOf(r.getPorcentajeQaNoCompletaPrioridad()),
											String.valueOf(r.getPorcentajeQaNoCompletaComun()),
											String.valueOf(r.getPorcentajeSprintsFallidos()),
											String.valueOf(r.getPorcentajeSprintsOciosos()),
											String.valueOf(r.getPorcentajeQaOcioso()),
											String.valueOf(r.getPromedioPuntosSobrantesPorSprintYEquipo()),
											String.valueOf(r.getPromedioPuntosFaltantesPorSprintYEquipo()));
		writeLine(writer, deltaT, values);
	}
	
	/**
	 * @desc 			Recibe un FileWriter del CSV abierto. 
	 * 					No abre, ni cierra el archivo. Solo escribe una linea en el writer
	 * 					Si es el primer deltaT agrega linea de headers. 
	 * 
	 * @param writer	
	 * @param deltaT	delta T del instante que se calcularon los resultados
	 * @param linea		String con la linea a insertar
	 */
	public static void writeLine(FileWriter writer, Integer deltaT, List<String> linea) {
		if(writer==null) return;
		
		if(deltaT.intValue() == 1){
			try {
				writeLine(writer, Arrays.asList(HEADERS));
			} catch (IOException e) {
				System.out.println("Ocurrió un error  el CSV");
				return;
			}
		}
		
		try {
			writeLine(writer, linea);
		} catch (IOException e) {
			System.out.println("Ocurrió un error  el CSV");
			return;
		}
	}
	
	/**
	 * @desc  						Abre csv, escribe y cierra el csv.
	 * 
	 * @param stageId : 			id del stage para identificar el archivo
	 * @param cantSprintsTotal : 	valor final del deltaT (cantidad de sprint final)
	 * @param lineas : 				conjunto de datos. Supone el array ordenado cronologicamente por deltaT
	 */
	public static void storeResults(int stageId, int cantSprintsTotal, List<List<String>> lineas) {
		if(lineas.size() != cantSprintsTotal){
			System.out.println(CSVUtil.class.getSimpleName() + " - La cantidad de resultados difiere de la cantidad de sprints total");
			return;
		}
		
		FileWriter writer = openCsv(stageId);
		int iDeltaT = 0;
		for(List<String> unaLinea : lineas){
			iDeltaT++;
			writeLine(writer, iDeltaT, unaLinea);
		}
			
		closeCsv(writer);		
	}
	
	/* 				Private methods 			*/
	/* 				Private methods 			*/
	/* 				Private methods 			*/
	
	private static void writeLine(Writer w, List<String> values) throws IOException {
		writeLine(w, values, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
	}

	private static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

		boolean first = true;
		separators = separators == ' ' ? DEFAULT_SEPARATOR : separators;

		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			if (!first) {
				sb.append(separators);
			}
			if (customQuote == ' ') {
				sb.append(followCVSformat(value));
			} else {
				sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
			}

			first = false;
		}
		sb.append("\n");
		w.append(sb.toString());
	}

	private static String followCVSformat(String value) {
		String result = value;
		if (result.contains("\"")) {
			result = result.replace("\"", "\"\"");
		}
		return result;
	}
	
	private static String getFileName(int stageId){
		return DEFAULT_PARTIAL_FILE_NAME + String.valueOf(stageId) + ".csv";
	}
}
