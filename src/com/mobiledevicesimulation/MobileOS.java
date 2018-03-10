package com.mobiledevicesimulation;

import com.mobiledevicesimulation.apps.CameraApp;
import com.mobiledevicesimulation.apps.EmailApp;
import com.mobiledevicesimulation.apps.SharedDocs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MobileOS {

    private int cpuUsage;
    private double power;
    private List<Process> processes;

    private boolean powerSavingMode;

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
                    process.executeinBackground(powerSavingMode, power);
                    power -= process.getPowerConsumed();
                }
                Thread.sleep(1000);
                updatePowerSavingMode();
            }
        }

        System.out.println("\nYour phone is switching off...");
    }

    private void updatePowerSavingMode() {
        
        powerSavingMode = power <= 20;

        if(powerSavingMode) {
            System.out.println("\nSystem is running now on Power Saving Mode");
        }
    }

    private void getRealProcesses() {
        try {
            java.lang.Process p = Runtime.getRuntime().exec("python yourapp.py");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
