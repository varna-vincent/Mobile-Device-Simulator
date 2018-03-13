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
import java.util.Random;

public class MobileOS {

    private int cpuUsage;
    private double power;
    private List<Process> processes;

    private boolean powerSavingMode;
    private boolean skipBackgroundExecution;
    private boolean displayPowerSaveModeMsg;

    private Random rand;
    private int round;

    public MobileOS(double power) {

        this.rand = new Random();
        this.power = power;
        this.round = 0;
        this.displayPowerSaveModeMsg = true;
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

        int sleepTime = 1000;

        while(power >= 0) {

            if (powerSavingMode) {
                sleepTime = 2000;
            }
            boolean hasForeground = false;
            ++round;

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

                // Terminate sharedDoc if power is extremely low and it hasn't been
                // active for 2 rounds (hours).
                if (process instanceof SharedDocs && powerIsExtremelyLow() && (round
                        - process.getLastForegroundTime() > 4)) {
                    process.setStatus("Terminated");
                }

                // Each process has 1/3 probablity to switch to a different state after
                // each round. While at most one process can be running in foreground.
                if (hasForeground) {
                    // If we have one process runnning in foreground, all other processes
                    // have to be in background.
                    process.setStatus("Background");
                } else if (rand.nextInt(3) == 0 && !process.getStatus().equals("Terminated")) {
                    process.flipStatus();
                }
                if (process.getStatus().equals("Foreground")) {
                    hasForeground = true;
                }

                Thread.sleep(1000);
                updatePowerSavingMode();
            }
        }

        System.out.println("\nKilling Processes.\nYour phone is switching off...");
    }

    private boolean powerIsExtremelyLow() { return power <= 5; }

    private void toggle(boolean skipBackgroundExecution) {
        this.skipBackgroundExecution = !skipBackgroundExecution;
    }

    private void updatePowerSavingMode() {

        powerSavingMode = power <= 20;

        if(powerSavingMode) {
            toggle(skipBackgroundExecution);
        }

        if(displayPowerSaveModeMsg && power <= 20) {

            System.out.println("\nPOWER SAVE MODE ON");
            System.out.println("------------------------------");
            displayPowerSaveModeMsg = !displayPowerSaveModeMsg;
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
