package com.mobiledevicesimulation.apps;

import com.mobiledevicesimulation.Process;

public class EmailApp extends Process {

    private final double POWER_FOREGROUND = 6;
    private final double POWER_BACKGROUND = 3;
    private final double POWER_FOREGROUND_POWERSAVEMODE = 3.5;
    private final double POWER_BACKGROUND_POWERSAVEMODE = 1;

    public EmailApp() {

        super();
        setPowerForeground(POWER_FOREGROUND);
        setPowerBackground(POWER_BACKGROUND);
        setPowerForegroundPowerSaveMode(POWER_FOREGROUND_POWERSAVEMODE);
        setPowerBackgroundPowerSaveMode(POWER_BACKGROUND_POWERSAVEMODE);
        setName("Gmail");
        setStatus("Background");

        System.out.println("Running Gmail in " + getStatus());
    }

    @Override
    public void executeinForeground(boolean isPowerSaveModeOn, double cpuPower) {

        if(hasPowerToExecute(cpuPower, POWER_FOREGROUND_POWERSAVEMODE)) {
            System.out.print("\nGmail: Displaying Ads and rendering the inbox....");
            consumePower(isPowerSaveModeOn);
            displayPower(cpuPower);
        }
    }

    @Override
    public void executeinBackground(boolean isPowerSaveModeOn, double cpuPower) {

        if(hasPowerToExecute(cpuPower, POWER_BACKGROUND_POWERSAVEMODE)) {
            if(cpuPower > 5) {
                System.out.print("\nGmail: Scanning for network connectivity...");
                System.out.print("\nGmail: Syncing the inbox with the cloud...");
                consumePower(isPowerSaveModeOn);
                displayPower(cpuPower);
            } else {
                System.out.println("\nGmail: Low Battery. Automatic Sync Service has been disabled.");
                sleep();
            }
        }
    }
}
