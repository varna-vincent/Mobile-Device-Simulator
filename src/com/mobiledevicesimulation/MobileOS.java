package com.mobiledevicesimulation;

import com.mobiledevicesimulation.apps.CameraApp;
import com.mobiledevicesimulation.apps.EmailApp;
import com.mobiledevicesimulation.apps.SharedDocs;

import java.util.ArrayList;
import java.util.List;

public class MobileOS {

    private int cpuUsage;
    private double power;
    private List<Process> processes;
    private int counter = 0;

    private boolean powerSavingMode = false;

    public MobileOS(double power) {

        this.power = power;

        if (this.power <= 20) {
            this.powerSavingMode = true;
        }

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
}
