package com.mobiledevicesimulation.apps;

import com.mobiledevicesimulation.Process;

public class CameraApp extends Process {

    private final double POWER_FOREGROUND = 10;
    private final double POWER_BACKGROUND = 4;
    private final double POWER_FOREGROUND_POWERSAVEMODE = 5;
    private final double POWER_BACKGROUND_POWERSAVEMODE = 1;

    public CameraApp() {
        super();
        setPowerForeground(POWER_FOREGROUND);
        setPowerBackground(POWER_BACKGROUND);
        setPowerForegroundPowerSaveMode(POWER_FOREGROUND_POWERSAVEMODE);
        setPowerBackgroundPowerSaveMode(POWER_BACKGROUND_POWERSAVEMODE);
        setName("Camera");
        setStatus("Foreground");
        System.out.println("Started Camera in " + getStatus());
    }

    @Override
    public void executeinForeground(boolean isPowerSaveModeOn, double cpuPower) {

        if(cpuPower > 5) {
            takePhoto();
            consumePower(isPowerSaveModeOn);
            displayPower(cpuPower);
        } else {
            System.out.println("\nCamera: Battery is critically low. Camera cannot be opened.");
        }

    }

    @Override
    public void executeinBackground(boolean isPowerSaveModeOn, double cpuPower) {

        if(hasPowerToExecute(cpuPower, POWER_BACKGROUND_POWERSAVEMODE)) {
            System.out.println("Camera: Executing bg tasks for camera - Fetch updates");
            // Code bg activity
            consumePower(isPowerSaveModeOn);
            displayPower(cpuPower);
        }
    }

    private void takePhoto() {
        System.out.print("\nCamera: Adjusting focus... Preparing to take photos");
    }
}
