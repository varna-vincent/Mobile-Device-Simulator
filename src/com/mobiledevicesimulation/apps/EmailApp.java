package com.mobiledevicesimulation.apps;

import com.mobiledevicesimulation.Process;

public class EmailApp extends Process {

    private boolean isPowerSaveModeOn;

    public EmailApp() {
        System.out.println("Started Email App");
    }

    @Override
    public void executeinForeground(boolean isPowerSaveModeOn) {

        this.isPowerSaveModeOn = isPowerSaveModeOn;
        // Poll for user input
        // Show Ads
        // Write Mail
        consumePower();
    }

    @Override
    public void executeinBackground(boolean isPowerSaveModeOn) {

        this.isPowerSaveModeOn = isPowerSaveModeOn;
        // Network Scan Activity
        // Save changes, load new mails, Sync
        consumePower();
    }

    @Override
    public void consumePower() {

        switch (getStatus()) {
            case "Foreground": setPowerConsumed(isPowerSaveModeOn ? 3.5 : 6); break;
            case "Background": setPowerConsumed(isPowerSaveModeOn ? 1 : 3); break;
        }
    }
}
