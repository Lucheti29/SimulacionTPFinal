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

    public static void main(String[] args) throws IOException {
        // Init
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SimulationInstance.Builder builder = SimulationInstance.Builder.newInstance();

        System.out.println("¿Querés usar un stage o cargar las variables de control a mano? (S/N)");
        String answer = br.readLine();

        if (answer.equalsIgnoreCase("s")) {
            System.out.println("¿Cuál es el nombre del archivo?");
            String fileName = br.readLine();

            File file = new File("stages/" + fileName);
            if (file.exists()) {
                try {
                    StageParser parser = StageParser.newInstance();
                    builder = parser.parseFile(file);
                } catch (Exception e) {
                    System.out.println(Simulation.class.getSimpleName() + e.getMessage());
                }
            } else {
                System.out.println("¡El archivo no existe!");
            }


        } else if (answer.equalsIgnoreCase("n")) {
            System.out.println("Ingresa la cantidad de personas en QA");
            builder.setQAPeopleAmount(Integer.valueOf(br.readLine()));

            System.out.println("Ingresa la cantidad de proyectos Android");
            builder.setProjectsAmount(Technology.ANDROID, Integer.valueOf(br.readLine()));

            System.out.println("Ingresa la cantidad de proyectos iOs");
            builder.setProjectsAmount(Technology.IOS, Integer.valueOf(br.readLine()));

            System.out.println("Ingresa la cantidad de proyectos Web");
            builder.setProjectsAmount(Technology.WEB, Integer.valueOf(br.readLine()));

            System.out.println("Ingresa la cantidad de desarrolladores Junior en Android");
            builder.setSeniorityAmount(Technology.ANDROID, Seniority.JUNIOR, Integer.valueOf(br.readLine()));

            System.out.println("Ingresa la cantidad de desarrolladores SemiSenior en Android");
            builder.setSeniorityAmount(Technology.ANDROID, Seniority.SEMISENIOR, Integer.valueOf(br.readLine()));

            System.out.println("Ingresa la cantidad de desarrolladores Senior en Android");
            builder.setSeniorityAmount(Technology.ANDROID, Seniority.SENIOR, Integer.valueOf(br.readLine()));

            System.out.println("Ingresa la cantidad de desarrolladores Junior en iOs");
            builder.setSeniorityAmount(Technology.IOS, Seniority.JUNIOR, Integer.valueOf(br.readLine()));

            System.out.println("Ingresa la cantidad de desarrolladores SemiSenior en iOs");
            builder.setSeniorityAmount(Technology.IOS, Seniority.SEMISENIOR, Integer.valueOf(br.readLine()));

            System.out.println("Ingresa la cantidad de desarrolladores Senior en iOs");
            builder.setSeniorityAmount(Technology.IOS, Seniority.SENIOR, Integer.valueOf(br.readLine()));

            System.out.println("Ingresa la cantidad de desarrolladores Junior en Web");
            builder.setSeniorityAmount(Technology.WEB, Seniority.JUNIOR, Integer.valueOf(br.readLine()));

            System.out.println("Ingresa la cantidad de desarrolladores SemiSenior en Web");
            builder.setSeniorityAmount(Technology.WEB, Seniority.SEMISENIOR, Integer.valueOf(br.readLine()));

            System.out.println("Ingresa la cantidad de desarrolladores Senior en Web");
            builder.setSeniorityAmount(Technology.WEB, Seniority.SENIOR, Integer.valueOf(br.readLine()));
        }

        SimulationInstance simulation = builder.build();
        simulation.run();

        // Start simulation
        // Update state vars
        // Check if it's the end
        // Get the results
        // Print the results
    }
}