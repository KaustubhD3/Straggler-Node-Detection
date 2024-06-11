package com.algo;

import java.util.ArrayList;

public class QLearning {
  public static double ci=0.0;
  public static String VAH="";
  public static double CVD=0.0;
  
  public static void main(String[] args)
  {
	  cpuload.memoryload();
	  ProcessData();
  }
    public static String ProcessData()
    {
       String s=null;
       
       return s;
    }   
    // Function for sorting the weight
    public static double getMax(double [] inputArray){ 
    double maxValue = inputArray[0]; 
    for(int i=1;i < inputArray.length;i++){ 
      if(inputArray[i] > maxValue){ 
         maxValue = inputArray[i]; 
      } 
    } 
    return maxValue; 
  }
}