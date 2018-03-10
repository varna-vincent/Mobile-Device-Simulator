package com.mobiledevicesimulation.apps;

import com.mobiledevicesimulation.Process;

public class SharedDocs extends Process {

    private final double POWER_FOREGROUND = 5;
    private final double POWER_BACKGROUND = 3;
    private final double POWER_FOREGROUND_POWERSAVEMODE = 2.5;
    private final double POWER_BACKGROUND_POWERSAVEMODE = 2;

    public SharedDocs() {

        setPowerForeground(POWER_FOREGROUND);
        setPowerBackground(POWER_BACKGROUND);
        setPowerForegroundPowerSaveMode(POWER_FOREGROUND_POWERSAVEMODE);
        setPowerBackgroundPowerSaveMode(POWER_BACKGROUND_POWERSAVEMODE);
        setStatus("Background");
        System.out.println("Running Google Docs in " + getStatus());
    }

    @Override
    public void executeinForeground(boolean isPowerSaveModeOn, double power) {

        if(hasPowerToExecute(power, POWER_FOREGROUND_POWERSAVEMODE)) {
            System.out.print("\nGoogle Docs: Blocked for user input...");
            consumePower(isPowerSaveModeOn);
            displayPower(power);
        }
    }

    @Override
    public void executeinBackground(boolean isPowerSaveModeOn, double power) {

        if(hasPowerToExecute(power, POWER_BACKGROUND_POWERSAVEMODE)) {
            System.out.print("\nGoogle Docs: Scanning for network connectivity...");
            System.out.print("\nGoogle Docs: Saving changes....");
            consumePower(isPowerSaveModeOn);
            displayPower(power);
        }
    }
}
