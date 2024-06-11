package com.algo;

import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.connection.Dbconn;
import com.sun.management.OperatingSystemMXBean;

public class cpuload {

	public static ArrayList<String>filenamelist = new ArrayList<>();
	public static ArrayList<Integer> chunkId = new ArrayList<>();
	public static ArrayList<Integer> chunksize = new ArrayList<>();
	public static ArrayList<Integer> memoryloadlist = new ArrayList<Integer>();
	public static Map<Integer,Integer> filesizelist=new HashMap<Integer,Integer>(); 
	public static Map<Integer,Integer> memoryloadlistnew=new HashMap<Integer,Integer>(); 
	public static Map<Integer,String> listfilename=new HashMap<Integer,String>();
	public static ArrayList<Integer> Objarray=new ArrayList<Integer>();
	public static void main(String[] args)
	{
		memoryload();
	}
public static void memoryload()
{

	OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    
	Random randomGenerator = new Random();
	Map<Integer,Integer> map=new HashMap<Integer,Integer>();  
	try {
	Connection con = Dbconn.conn();
	Statement st = con.createStatement();
	Statement st1 = con.createStatement();
String ip="192.168.0.";
int copy=1,no=1;
int memoryload;
	    for (int idx = 1; idx < 5; idx++){
	      int randomInt = randomGenerator.nextInt(1024)+1;
	      long totalPhysicalMemory = osBean.getTotalPhysicalMemorySize();

	        // Get free physical memory size
	        long freePhysicalMemory = osBean.getFreePhysicalMemorySize();

	        // Calculate used physical memory
	        long usedPhysicalMemory = totalPhysicalMemory - freePhysicalMemory;

	        // Calculate memory load
	        double memoryLoad = (double) usedPhysicalMemory / totalPhysicalMemory * 1024;
//	        int randomInt = (int) Math.round(memoryLoad);
	       // int randomInt = (int) Math.round(memoryLoad)+randomGenerator.nextInt(10);
	      map.put(randomInt,idx);
	      Objarray.add(randomInt);
	      memoryloadlistnew.put(idx, randomInt);
	      
	    }
	    
	    for(Map.Entry<Integer,Integer> m:map.entrySet()){  
	    if(no<=10)
	      {  
	    	memoryload=(int)m.getKey();// MB load 
	    	memoryloadlist.add(memoryload);
	    	
	    	System.out.println("Generated : " +m.getKey()+"\tMemoryLoad :"+memoryload); 
			no++;
	      }
	   }
	    Collections.sort(memoryloadlist);
	    for(int str: memoryloadlist){
	    	double perUsed=CPUusage.calcCPU();
	    	long load=str*1024;
	    	st.executeUpdate("update jobcreating set MemoryLoad='"+load+"',VMUsage='"+perUsed+"' where IpAdd='"+ip+copy+"'");
	    	st1.executeUpdate("update serverinfo set VMUsage='"+perUsed+"' where IpAdd='"+ip+copy+"'");
			
	    	copy++;
	    }
	} 
	catch (SQLException | ClassNotFoundException e) {
		System.out.println(e);
	}
	
}
}
