/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.admin.arff_create.*;
import com.connection.Dbconn;
/**
 *
 * @author Dell
 */
public class CrossValidation {

    public static ArrayList<String> titleset = new ArrayList<>();

    public static void cross_validation(String filename) {
        try {
            ArrayList<String> Finalset = new ArrayList<>();
            ArrayList<String> Trainset = new ArrayList<>();

            ArrayList<String> Testset = new ArrayList<>();
            int i = 0;
            FileReader inputFile;

            inputFile = new FileReader(Dbconn.mainInputfile+filename);

            BufferedReader br = new BufferedReader(inputFile);
            String line;
            while ((line = br.readLine()) != null) {
                if (i == 0) {
                    String title="id,cpuload,memoryload,prunnin,pqueue,Ubrand,Utask,cpurack,Prack,Rammemory,Timeprocess,label";
                    titleset.add(title);
                } else {
                    Finalset.add(line);
                }
                i++;
            }
            br.close();
            System.out.println("Size=>" + Finalset.size());
            int count = Finalset.size();
            int get70 = (count / 100) * 70;
            int get30 = count - get70;
            int counter = 0;
            for (int j = 0; j < count; j++) {
                if (counter < get70) {
                    
                    String[] Parts = Finalset.get(j).split(",");
                    String fd=Parts[0]+","+Parts[2]+","+Parts[3]+","+Parts[4]+","+Parts[5]+","+Parts[6]+","+Parts[7]+","+Parts[8]+","+Parts[9]+","+Parts[10]+","+Parts[11]+","+Parts[12];
                    Trainset.add(fd);
                 
                } else {
                    
                    String[] Parts = Finalset.get(j).split(",");
                    String fd=Parts[0]+","+Parts[2]+","+Parts[3]+","+Parts[4]+","+Parts[5]+","+Parts[6]+","+Parts[7]+","+Parts[8]+","+Parts[9]+","+Parts[10]+","+Parts[11]+","+Parts[12];
                    Testset.add(fd);
                
                }
                counter = counter + 1;
            }
            System.out.println("Train Size=>" + Trainset.size());
            System.out.println("Test Size=>" + Testset.size());
            Testfile(Testset);
            Trainfile(Trainset);

            //Create Arff File
            Train_Arff_file();
            Testing_Arff_file();
        } catch (Exception ex) {
            Logger.getLogger(CrossValidation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws IOException {

        //CrossValidation.cross_validation();

    }

    public static void Trainfile(ArrayList Trainset) {
        File Nfpath = new File(Dbconn.traintext_file);
        try {
            FileOutputStream fos = new FileOutputStream(Nfpath);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    fos));
            for (int i = 0; i < titleset.size(); i++) {
                bw.write(titleset.get(i).toString());
                bw.newLine();
            }
            for (int j = 0; j < Trainset.size(); j++) {
                bw.write(Trainset.get(j).toString());
                bw.newLine();
            }

            bw.close();
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }

    }

    public static void Testfile(ArrayList Testset) {
        File Nfpath = new File(Dbconn.testtext_file);
        try {
            FileOutputStream fos = new FileOutputStream(Nfpath);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    fos));
            for (int i = 0; i < titleset.size(); i++) {
                bw.write(titleset.get(i).toString());
                bw.newLine();
            }
            Iterator itr1 = Testset.iterator();
            while (itr1.hasNext()) {
                // for (String s : demotwitter.alllistdata) {
                String s = itr1.next().toString();
                bw.write(s);
                bw.newLine();
            }
            bw.close();

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        } catch (Exception ex) {
            Logger.getLogger(CrossValidation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
