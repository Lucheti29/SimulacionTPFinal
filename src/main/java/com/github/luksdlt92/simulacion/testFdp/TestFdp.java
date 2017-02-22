package com.github.luksdlt92.simulacion.testFdp;

import java.io.IOException;
import java.io.PrintWriter;

import com.github.luksdlt92.simulacion.constant.Seniority;
import com.github.luksdlt92.simulacion.constant.Technology;
import com.github.luksdlt92.simulacion.fdp.*;

public class TestFdp {

	public static void main(String[] args){
		
		try {
			PrintWriter writer = new PrintWriter("testFile.txt", "UTF-8");
			
			int i;

			writer.println("------------->valores fdp Android Jr<-------------");
			for(i=0;i<100;i++){
				writer.println(
						ComplexityPointsDev.
						getCompletedPointsPerHour(Technology.ANDROID, Seniority.JUNIOR));
			}
			
			writer.println("------------->valores fdp Android Ssr<-------------");
			for(i=0;i<100;i++){
				writer.println(
						ComplexityPointsDev.
						getCompletedPointsPerHour(Technology.ANDROID, Seniority.SEMISENIOR));
			}
			
			writer.println("------------->valores fdp Android Sr<-------------");
			for(i=0;i<100;i++){
				writer.println(
						ComplexityPointsDev.
						getCompletedPointsPerHour(Technology.ANDROID, Seniority.SENIOR));
			}
			
			writer.println("------------->valores fdp iOS Jr<-------------");
			for(i=0;i<100;i++){
				writer.println(
						ComplexityPointsDev.
						getCompletedPointsPerHour(Technology.IOS, Seniority.JUNIOR));
			}
			
			writer.println("------------->valores fdp iOS Ssr<-------------");
			for(i=0;i<100;i++){
				writer.println(
						ComplexityPointsDev.
						getCompletedPointsPerHour(Technology.IOS, Seniority.SEMISENIOR));
			}
			
			writer.println("------------->valores fdp iOS Sr<-------------");
			for(i=0;i<100;i++){
				writer.println(
						ComplexityPointsDev.
						getCompletedPointsPerHour(Technology.IOS, Seniority.SENIOR));
			}
			
			writer.println("------------->valores fdp Web Jr<-------------");
			for(i=0;i<100;i++){
				writer.println(
						ComplexityPointsDev.
						getCompletedPointsPerHour(Technology.WEB, Seniority.JUNIOR));
			}
			
			writer.println("------------->valores fdp Web Ssr<-------------");
			for(i=0;i<100;i++){
				writer.println(
						ComplexityPointsDev.
						getCompletedPointsPerHour(Technology.WEB, Seniority.SEMISENIOR));
			}
			
			writer.println("------------->valores fdp Web Sr<-------------");
			for(i=0;i<100;i++){
				writer.println(
						ComplexityPointsDev.
						getCompletedPointsPerHour(Technology.WEB, Seniority.SENIOR));
			}
			
			writer.println("------------->valores estimacion<-------------");
			for(i=0;i<100;i++){
				writer.println(
						ComplexityPointsDev
						.getEstimatedPointsPerSprint(Technology.ANDROID, 1));
			}
			
			writer.close();
			
			System.out.println("Todo piola");
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("QUILOMBOOOO");
			return;
		}
	}
}
