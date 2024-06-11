package com.algo;

public class CPUusage {

	public static void main(String[] args)
	{
		//while(true)
		{
		calcCPU();
//		try {
////			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		}
	}
	public static Double calcCPU()
	{
		long total, free, used;
		//int mb = 1024 * 1024;
		Runtime runtime;
		runtime = Runtime.getRuntime();
//		runtime.gc();
		total = runtime.totalMemory();
		free = runtime.freeMemory();
		used = total - free;

		double perUsed = ((double) used / (double) total) * 100;
		double perFree = ((double) free / (double) total) * 100;
//		if (perUsed > 99) {
//			perUsed = 95;
//			perFree = 5;
//		}
		
		System.out.println(total+"Percent Used: " + perUsed + "%");
		System.out.println(free+"Percent Free: " + perFree + "%");
		return perUsed;

		
	}
	
}
