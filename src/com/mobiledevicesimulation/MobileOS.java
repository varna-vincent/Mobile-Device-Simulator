package com.mobiledevicesimulation;

import com.mobiledevicesimulation.apps.CameraApp;
import com.mobiledevicesimulation.apps.EmailApp;
import com.mobiledevicesimulation.apps.SharedDocs;

import java.util.ArrayList;
import java.util.List;

public class MobileOS {

    private int cpuUsage;
    private int power;
    private List<Process> processes;

    public MobileOS() {
        init();
    }

    public void init() {
        processes = new ArrayList<>();
        processes.add(new EmailApp());
        processes.add(new CameraApp());
        processes.add(new SharedDocs());
    }
}
