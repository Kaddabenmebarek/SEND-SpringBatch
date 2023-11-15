package com.idorsia.research.send.writers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.idorsia.research.send.processors.Constants;


public class SASFileBuilder {

	private String rPath;
	
	public void launchCommand(String scriptPath, String scriptType) throws IOException, InterruptedException {
		
		BufferedReader reader = null;
        try {
        	FileInputStream fi = new FileInputStream(Constants.APP_PROPERTIES_PATH);
			ResourceBundle rb = new PropertyResourceBundle(fi);
			rPath = rb.getString("r_path");
			//processBuilder = new ProcessBuilder("cmd.exe", "/c", "ping -n 3 google.com");
			ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", rPath + " -e source('"+ scriptPath +"')");
			Process process = processBuilder.start();
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			int exitCode = process.waitFor();
			System.out.println("\nExited with error code : " + exitCode);
		} finally {
        	reader.close();
		}
    }
	
}
