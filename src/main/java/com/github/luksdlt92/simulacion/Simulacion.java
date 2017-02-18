package com.github.luksdlt92.simulacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Simulacion {

    public static void main(String[] args) throws IOException {
        // Init
        // Start simulation
        // Update state vars
        // Check if it's the end
        // Get the results
        // Print the results

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Ingresa un valor");
        String input = br.readLine();
        System.out.println("It works! El valor fue " + input);
    }
}