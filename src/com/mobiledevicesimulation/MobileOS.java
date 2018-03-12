package com.mobiledevicesimulation;

import com.mobiledevicesimulation.apps.CameraApp;
import com.mobiledevicesimulation.apps.EmailApp;
import com.mobiledevicesimulation.apps.SharedDocs;
import com.mobiledevicesimulation.utils.Utils;

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

    private boolean powerSavingMode;
    private boolean skipBackgroundExecution;

    public MobileOS(double power) {

        this.power = power;
        updatePowerSavingMode();
        init();

    }

    public void init() {

        processes = new ArrayList<>();
        processes.add(new EmailApp());
        processes.add(new CameraApp());
        processes.add(new SharedDocs());

        run();
    }

    public void run() {

        try {
            getRealProcesses();
            simulateFakeProcesses();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void simulateFakeProcesses() throws InterruptedException {

        while(power >= 0) {

            for(Process process : processes) {

                if (process.getStatus().equals("Foreground")) {
                    process.executeinForeground(powerSavingMode, power);
                    power -= process.getPowerConsumed();
                } else {
                    if(!skipBackgroundExecution) {
                        process.executeinBackground(powerSavingMode, power);
                        power -= process.getPowerConsumed();
                    } else {
                        System.out.println(String.format("\n%s is skipping background processes to conserve power", process.getName()));
                    }
                }
                Thread.sleep(1000);
                updatePowerSavingMode();
            }
        }

        System.out.println("\nKilling Processes.\nYour phone is switching off...");
    }

    private void toggle(boolean skipBackgroundExecution) {
        this.skipBackgroundExecution = !skipBackgroundExecution;
    }

    private void updatePowerSavingMode() {

        powerSavingMode = power <= 20;

        if(powerSavingMode) {
            toggle(skipBackgroundExecution);
            System.out.println("\nSystem is running now on Power Saving Mode");
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
				System.out.println(String.format("\nReal process %s: It has been executed for %s time, it occupies %s CPU and %s memory.", pidStr, exeTime, cpu, mem));
                parseExeTime(exeTime);
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
    			System.out.println("Remaining CPU power = " + Utils.round(power) + "%");
    		} catch (NumberFormatException ex) {
    			ex.printStackTrace();
    		}
    }

}
