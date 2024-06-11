package com.algo;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import com.sun.management.OperatingSystemMXBean;

public class SystemMonitor {
    public static void main(String[] args) {
    	OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

        // Get the CPU load
        double cpuLoad = osBean.getProcessCpuLoad()*100; // Convert to percentage
        System.out.println("CPU Load: " + cpuLoad);

        //for (int i = 0; i < 5; i++) 
        {
			
		
     // Get total physical memory size
        long totalPhysicalMemory = osBean.getTotalPhysicalMemorySize();

        // Get free physical memory size
        long freePhysicalMemory = osBean.getFreePhysicalMemorySize();

        // Calculate used physical memory
        long usedPhysicalMemory = totalPhysicalMemory - freePhysicalMemory;

        // Calculate memory load
        double memoryLoad = (double) usedPhysicalMemory / totalPhysicalMemory * 1024;
long load=(long)(memoryLoad);
// Print memory load information
int intValue2 = (int) Math.round(memoryLoad);
System.out.println("Math.round(): " + intValue2);
        System.out.println(load+"Memory Load: " + memoryLoad);
    
        }
        
//        
//        // Memory usage
//        MemoryUsage heapMemoryUsage = memoryBean.getHeapMemoryUsage();
//        long usedHeapMemory = heapMemoryUsage.getUsed();
//        long maxHeapMemory = heapMemoryUsage.getMax();
//        System.out.println(usedHeapMemory+"Heap Memory Usage: " + usedHeapMemory / (1024 * 1024) + " MB / " + maxHeapMemory / (1024 * 1024) + " MB");
//        
//        long heapUsed = heapMemoryUsage.getUsed();
//        long heapMax = heapMemoryUsage.getMax();
//
//        
//        // Calculate heap memory load
//        double heapMemoryLoad = (double) heapUsed / heapMax * 100;
//        System.out.println(heapMemoryLoad*1024);
//        // Free memory size
////        long freePhysicalMemory = osBean.getFreePhysicalMemorySize();
////        System.out.println("Free Memory: " + freePhysicalMemory);
    }
}
