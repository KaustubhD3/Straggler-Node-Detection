package com.admin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.connection.Dbconn;

public class Qlearnining {

	public static void Qlearnining_process(InputStream inputStream,String filename)
	{
		ArrayList<String> llist = new ArrayList<String>();
        ArrayList<String> datalist = new ArrayList<String>();
        try {
        // VM log dataset file
        
        BufferedReader br = new BufferedReader(new InputStreamReader(
				inputStream));
        String line = "";
			while ((line = br.readLine()) != null) {
			    int reward = 0;
			    int penalty = 0;
			    String finaldata = line;
			    String[] data = line.split(",");
			    int cpuload = Integer.valueOf(data[2]);
			    int memoryload = Integer.valueOf(data[3]);
			    int prunnin = Integer.valueOf(data[4]);
			    int pqueue = Integer.valueOf(data[5]);
			    int Ubrand = Integer.valueOf(data[6]);
			    int Utask = Integer.valueOf(data[7]);
			    int cpurack = Integer.valueOf(data[8]);
			    int Prack = Integer.valueOf(data[9]);
			    int Rammemory = Integer.valueOf(data[10]);
			    int Timeprocess = Integer.valueOf(data[11]);
			    double Total_Attributes = 10;

			    if (cpuload >= 40 && cpuload <= 55) {
			        reward++;
			    } else {
			        penalty++;
			    }

			    if (memoryload >= 30 && memoryload <= 55) {
			        reward++;
			    } else {
			        penalty++;
			    }
			    if (prunnin >= 10 && prunnin <= 55) {
			        reward++;
			    } else {
			        penalty++;
			    }
			    if (pqueue >= 40 && pqueue <= 60) {
			        reward++;
			    } else {
			        penalty++;
			    }
			    if (Ubrand >= 30 && Ubrand <= 60) {
			        reward++;
			    } else {
			        penalty++;
			    }
			    if (Utask >= 4 && Utask <= 7) {
			        reward++;
			    } else {
			        penalty++;
			    }
			    if (cpurack >= 50 && cpurack <= 70) {
			        reward++;
			    } else {
			        penalty++;
			    }
			    if (Prack >= 50 && Prack <= 70) {
			        reward++;
			    } else {
			        penalty++;
			    }
			    if (Rammemory >= 10 && Rammemory <= 45) {
			        reward++;
			    } else {
			        penalty++;
			    }
			    if (Timeprocess >= 100 && Timeprocess <= 250) {
			        reward++;
			    } else {
			        penalty++;
			    }
			    String label = "";
			    System.out.println("reward=>" + reward + "penalty" + penalty);

			    double weight1 = (penalty / Total_Attributes);
			    double weight2 = (reward / Total_Attributes);

			    if (weight1 > weight2) {
			        label = "Straggler";
			    } else {
			        label = "Non_straggler";

			    }

			    finaldata = finaldata + "," + label;
			    datalist.add(finaldata);

			}
		
            FileWriter myWriter = new FileWriter(Dbconn.mainInputfile+filename);
            for (int i = 0; i < datalist.size(); i++) {
                myWriter.write(datalist.get(i));
                myWriter.write("\n");
            }
            myWriter.close();
            System.out.println("Successfully write to the file.");
            CrossValidation.cross_validation(filename);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
	}
	
	
    public static void main(String[] args) throws Exception {
        ArrayList<String> llist = new ArrayList<String>();
        ArrayList<String> datalist = new ArrayList<String>();
        
        // VM log dataset file
        
        BufferedReader br = new BufferedReader(new FileReader(
                "D:\\PHD\\NMU Atul Dusane\\data\\vm5log.txt"));
        String line = "";
        while ((line = br.readLine()) != null) {
            int reward = 0;
            int penalty = 0;
            String finaldata = line;
            System.out.println(finaldata);
            String[] data = line.split(",");
            int cpuload = Integer.valueOf(data[2]);
            int memoryload = Integer.valueOf(data[3]);
            int prunnin = Integer.valueOf(data[4]);
            int pqueue = Integer.valueOf(data[5]);
            int Ubrand = Integer.valueOf(data[6]);
            int Utask = Integer.valueOf(data[7]);
            int cpurack = Integer.valueOf(data[8]);
            int Prack = Integer.valueOf(data[9]);
            int Rammemory = Integer.valueOf(data[10]);
            int Timeprocess = Integer.valueOf(data[11]);
            double Total_Attributes = 10;

            if (cpuload >= 40 && cpuload <= 55) {
                reward++;
            } else {
                penalty++;
            }

            if (memoryload >= 30 && memoryload <= 55) {
                reward++;
            } else {
                penalty++;
            }
            if (prunnin >= 10 && prunnin <= 55) {
                reward++;
            } else {
                penalty++;
            }
            if (pqueue >= 40 && pqueue <= 60) {
                reward++;
            } else {
                penalty++;
            }
            if (Ubrand >= 30 && Ubrand <= 60) {
                reward++;
            } else {
                penalty++;
            }
            if (Utask >= 4 && Utask <= 7) {
                reward++;
            } else {
                penalty++;
            }
            if (cpurack >= 50 && cpurack <= 70) {
                reward++;
            } else {
                penalty++;
            }
            if (Prack >= 50 && Prack <= 70) {
                reward++;
            } else {
                penalty++;
            }
            if (Rammemory >= 10 && Rammemory <= 45) {
                reward++;
            } else {
                penalty++;
            }
            if (Timeprocess >= 100 && Timeprocess <= 250) {
                reward++;
            } else {
                penalty++;
            }
            String label = "";
            System.out.println("reward=>" + reward + "penalty" + penalty);

            double weight1 = (penalty / Total_Attributes);
            double weight2 = (reward / Total_Attributes);

            if (weight1 > weight2) {
                label = "Straggler";
            } else {
                label = "Non_straggler";

            }

            finaldata = finaldata + "," + label;
            datalist.add(finaldata);

        }

        try {
            FileWriter myWriter = new FileWriter("D:\\PHD\\NMU Atul Dusane\\data\\vm5logdataset.txt");
            for (int i = 0; i < datalist.size(); i++) {
                myWriter.write(datalist.get(i));
                myWriter.write("\n");
            }
            myWriter.close();
            System.out.println("Successfully write to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

}
