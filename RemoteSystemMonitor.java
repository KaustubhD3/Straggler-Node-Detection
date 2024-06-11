package com.algo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RemoteSystemMonitor {
    public static void main(String[] args) {
        String host = "192.168.0.106";
        String user = "Jitu Patil";
        String password = "om";

        try {
            // Establish SSH connection
            ProcessBuilder processBuilder = new ProcessBuilder("ssh", user + "@" + host);
            Process process = processBuilder.start();
            
            // Get input and error streams
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();
            
            // Create reader for input and error streams
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
            
            // Enter password if prompted
            if (inputReader.readLine().contains("password")) {
                process.getOutputStream().write((password + "\n").getBytes());
                process.getOutputStream().flush();
            }
            
            // Execute commands to retrieve system metrics
            process.getOutputStream().write("free\n".getBytes());
            process.getOutputStream().write("top -n1 -b\n".getBytes());
            process.getOutputStream().write("exit\n".getBytes());
            process.getOutputStream().flush();
            
            // Read and print output
            String line;
            while ((line = inputReader.readLine()) != null) {
                System.out.println(line);
            }
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }
            
            // Wait for process to complete
            process.waitFor();
            
            // Close streams
            inputReader.close();
            errorReader.close();
            inputStream.close();
            errorStream.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}