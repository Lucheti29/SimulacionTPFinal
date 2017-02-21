package com.github.luksdlt92.simulacion;

import com.github.luksdlt92.simulacion.constant.Seniority;
import com.github.luksdlt92.simulacion.constant.Technology;
import com.github.luksdlt92.simulacion.model.instance.SimulationInstance;
import com.github.luksdlt92.simulacion.parser.StageParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Simulation {

    private static final BufferedReader mReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        System.out.println("Queres cargar un escenario para realizar la simulacion? (S/N)");
        SimulationInstance simulation = mReader.readLine().equalsIgnoreCase("s") ? useParser() : useConsole();

        if (simulation != null) 
        	simulation.run();
        else 
        	System.out.print("Algo salio mal!");
    }

    private static SimulationInstance useConsole() {
        try {
            SimulationInstance.Builder builder = SimulationInstance.Builder.newInstance();

            System.out.println("Ingresa la cantidad de personas en QA");
            builder.setQAPeopleAmount(Integer.valueOf(mReader.readLine()));

            System.out.println("Ingresa la cantidad de proyectos Android");
            builder.setProjectsAmount(Technology.ANDROID, Integer.valueOf(mReader.readLine()));

            System.out.println("Ingresa la cantidad de proyectos iOs");
            builder.setProjectsAmount(Technology.IOS, Integer.valueOf(mReader.readLine()));

            System.out.println("Ingresa la cantidad de proyectos Web");
            builder.setProjectsAmount(Technology.WEB, Integer.valueOf(mReader.readLine()));

            System.out.println("Ingresa la cantidad de desarrolladores Junior en Android");
            builder.setSeniorityAmount(Technology.ANDROID, Seniority.JUNIOR, Integer.valueOf(mReader.readLine()));

            System.out.println("Ingresa la cantidad de desarrolladores SemiSenior en Android");
            builder.setSeniorityAmount(Technology.ANDROID, Seniority.SEMISENIOR, Integer.valueOf(mReader.readLine()));

            System.out.println("Ingresa la cantidad de desarrolladores Senior en Android");
            builder.setSeniorityAmount(Technology.ANDROID, Seniority.SENIOR, Integer.valueOf(mReader.readLine()));

            System.out.println("Ingresa la cantidad de desarrolladores Junior en iOs");
            builder.setSeniorityAmount(Technology.IOS, Seniority.JUNIOR, Integer.valueOf(mReader.readLine()));

            System.out.println("Ingresa la cantidad de desarrolladores SemiSenior en iOs");
            builder.setSeniorityAmount(Technology.IOS, Seniority.SEMISENIOR, Integer.valueOf(mReader.readLine()));

            System.out.println("Ingresa la cantidad de desarrolladores Senior en iOs");
            builder.setSeniorityAmount(Technology.IOS, Seniority.SENIOR, Integer.valueOf(mReader.readLine()));

            System.out.println("Ingresa la cantidad de desarrolladores Junior en Web");
            builder.setSeniorityAmount(Technology.WEB, Seniority.JUNIOR, Integer.valueOf(mReader.readLine()));

            System.out.println("Ingresa la cantidad de desarrolladores SemiSenior en Web");
            builder.setSeniorityAmount(Technology.WEB, Seniority.SEMISENIOR, Integer.valueOf(mReader.readLine()));

            System.out.println("Ingresa la cantidad de desarrolladores Senior en Web");
            builder.setSeniorityAmount(Technology.WEB, Seniority.SENIOR, Integer.valueOf(mReader.readLine()));

            return builder.build();
        } catch (IOException e) {
            System.out.println(Simulation.class.getSimpleName() + e.getMessage());
        }

        return null;
    }

    private static SimulationInstance useParser() {
        try {
            System.out.println("¿Cual es el nombre del archivo?");
            String fileName = mReader.readLine();

            File file = new File("stages/" + fileName);
            if (file.exists()) {
                try {
                    StageParser parser = StageParser.newInstance();
                    return parser.parseFile(file).build();
                } catch (Exception e) {
                    System.out.println(Simulation.class.getSimpleName() + e.getMessage());
                }
            } else {
                System.out.println("¡El archivo no existe!");
            }
        } catch (IOException e) {
            System.out.println(Simulation.class.getSimpleName() + e.getMessage());
        }
        return null;
    }
}