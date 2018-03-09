package com.mobiledevicesimulation.apps;

import com.mobiledevicesimulation.Process;

public class SharedDocs extends Process {

    private boolean isPowerSaveModeOn;

    public SharedDocs() {
        System.out.println("Started Shared Docs App");
    }

    @Override
    public void executeinForeground(boolean isPowerSaveModeOn) {

        this.isPowerSaveModeOn = isPowerSaveModeOn;
        // Poll for user input
        consumePower();
    }

    @Override
    public void executeinBackground(boolean isPowerSaveModeOn) {

        this.isPowerSaveModeOn = isPowerSaveModeOn;
        // Network Scan Activity every 30 sec
        // If successful, save changes and sync
        consumePower();
    }

    @Override
    public void consumePower() {

        switch (getStatus()) {
            case "Foreground": setPowerConsumed(isPowerSaveModeOn ? 2.5 : 6); break;
            case "Background": setPowerConsumed(isPowerSaveModeOn ? 2 : 3); break;
        }
    }
}
