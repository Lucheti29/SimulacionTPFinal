package com.github.luksdlt92.simulacion;

import com.github.luksdlt92.simulacion.constant.Seniority;
import com.github.luksdlt92.simulacion.constant.Technology;
import com.github.luksdlt92.simulacion.model.instance.SimulationInstance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Simulation {

    public static void main(String[] args) throws IOException {
        // Init
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        SimulationInstance.Builder builder = SimulationInstance.Builder.newInstance();

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

        SimulationInstance simulation = builder.build();
        simulation.run();

        // Start simulation
        // Update state vars
        // Check if it's the end
        // Get the results
        // Print the results
    }
}