package com.mobiledevicesimulation;

import com.mobiledevicesimulation.apps.CameraApp;
import com.mobiledevicesimulation.apps.EmailApp;
import com.mobiledevicesimulation.apps.SharedDocs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class MobileOS {

    private int cpuUsage;
    private double power;
    private List<Process> processes;
    private int counter = 0;

    private boolean powerSavingMode;

    public MobileOS(double power) {

        this.power = power;
        this.powerSavingMode = power <= 20;
        init();

    }

    public void init() {

        processes = new ArrayList<>();
        processes.add(new EmailApp());
        processes.add(new CameraApp());
        processes.add(new SharedDocs());
    }

//    public void startRunning() {
//
//        while (true) {
//            Process foregroundProcess = processes.get(counter% processes.size());
//            counter++;
//            foregroundProcess.run();
//            try {
//                Thread.sleep(30000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            foregroundProcess.stop();
//
//
//        }
//    }

//
//
//    public void managePower(int index, double remainedPwr) {
//        processes.get(index)
//        for (int i = 0; i < processes.size(); i++)
//    }


    public void run() {

        getRealProcesses();
        
        for(Process process : processes) {

            if(process.getStatus().equals("Foreground")) {
                // pass power status based on remaining power
                process.executeinForeground(powerSavingMode);
                power -= process.getPowerConsumed();
            } else {
               process.executeinBackground(powerSavingMode);
               power -= process.getPowerConsumed();
            }
        }
    }

    private void getRealProcesses() {
        try {
            java.lang.Process p = Runtime.getRuntime().exec("python getProcess.py");
            dealWithFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void dealWithFile() {
		String fileName = "output.txt";
		BufferedReader br = null;
		try {
			File file = new File(fileName);
			br = new BufferedReader(new FileReader(file));
			String temp;
			int i = 0;
			while ((temp = br.readLine()) != null) {
				if (i++ == 0) 
					continue;
				
				String[] infos = temp.split("\\s+");
				if (infos.length < 4) 
					continue;
				String pidStr = infos[0], exeTime = infos[1], cpu = infos[2], mem = infos[3];
				parseExeTime(exeTime);
				System.out.println(String.format("Real process %s: It has been executed for %s time, it occupies %s CPU and %s memory.", pidStr, exeTime, cpu, mem));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(br != null){
				try{
					br.close();
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}
    
    private void parseExeTime(String exeTime) {
    		String[] time = exeTime.split(":");
    		if (time.length < 1) 
    			return;
    		String hourStr = time[0];
    		try {
    			int hour = Integer.parseInt(hourStr);
    			power -= (hour * 1.0 / 30);
    			System.out.println(String.format("Remaining CPU power -- %.2f%%", power));
    		} catch (NumberFormatException ex) {
    			ex.printStackTrace();
    		}
    }

}
