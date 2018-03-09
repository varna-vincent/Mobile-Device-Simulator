package com.mobiledevicesimulation.apps;

import com.mobiledevicesimulation.Process;

public class CameraApp extends Process implements Runnable {

//    private boolean enable;
    private boolean isPowerSaveModeOn;

    public CameraApp() {
        System.out.println("Started Camera App");
//        this.enable = false;
    }

    @Override
    public void executeinForeground(boolean isPowerSaveModeOn) {

        this.isPowerSaveModeOn = isPowerSaveModeOn;
        takePhoto();
        consumePower();

    }

    @Override
    public void executeinBackground(boolean isPowerSaveModeOn) {

        this.isPowerSaveModeOn = isPowerSaveModeOn;
        System.out.println("Executing bg tasks for camera - Fetch updates");
        // Code bg activity
        consumePower();
    }

    @Override
    public void consumePower() {

        switch (getStatus()) {
            case "Foreground": setPowerConsumed(isPowerSaveModeOn ? 5 : 10); break;
            case "Background": setPowerConsumed(isPowerSaveModeOn ? 1 : 4); break;
        }
    }

    private void takePhoto() {
        System.out.println("Camera is taking photos");
    }

    @Override
    public void run() {
//        while (true) {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if (this.enable) {
//                executeinForeground();
//                disableCamera();
//            }
//
//        }
    }

//    public void enableCamera() {
//        this.enable = true;
//    }
//
//    public void disableCamera() {
//        this.enable = false;
//    }
}
