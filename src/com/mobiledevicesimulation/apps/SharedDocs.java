package com.mobiledevicesimulation.apps;

import com.mobiledevicesimulation.Process;

public class SharedDocs extends Process {

    private final double POWER_FOREGROUND = 5;
    private final double POWER_BACKGROUND = 3;
    private final double POWER_FOREGROUND_POWERSAVEMODE = 2.5;
    private final double POWER_BACKGROUND_POWERSAVEMODE = 2;

    public SharedDocs() {

        super();
        setPowerForeground(POWER_FOREGROUND);
        setPowerBackground(POWER_BACKGROUND);
        setPowerForegroundPowerSaveMode(POWER_FOREGROUND_POWERSAVEMODE);
        setPowerBackgroundPowerSaveMode(POWER_BACKGROUND_POWERSAVEMODE);
        setName("Google Docs");
        setStatus("Background");
        System.out.println("Running Google Docs in " + getStatus());
    }

    @Override
    public void executeinForeground(boolean isPowerSaveModeOn, double cpuPower) {

        if(hasPowerToExecute(cpuPower, POWER_FOREGROUND_POWERSAVEMODE)) {
            System.out.print("\nGoogle Docs: Blocked for user input...");
            consumePower(isPowerSaveModeOn);
            displayPower(cpuPower);
        }
    }

    @Override
    public void executeinBackground(boolean isPowerSaveModeOn, double cpuPower) {

        if(hasPowerToExecute(cpuPower, POWER_BACKGROUND_POWERSAVEMODE)) {
            if(cpuPower > 5) {
                System.out.print("\nGoogle Docs: Scanning for network connectivity...");
                System.out.print("\nGoogle Docs: Saving changes....");
                consumePower(isPowerSaveModeOn);
                displayPower(cpuPower);
            } else {
                System.out.println("\nGoogle Docs: Low Battery. Automatic Sync Service has been disabled.");
                sleep();
            }
        }
    }

    @Override
    public void setStatus(String status) {
        if (status.equals("Terminated")) {
//            System.out.println("\nGoogle Docs process has been stopped due to extremely low power!");
        } else {
            super.setStatus(status);
        }
    }
}
