package com.mobiledevicesimulation;

import com.mobiledevicesimulation.utils.Utils;

public abstract class Process implements Activity {

    private int pid;
    private double runningTime;
    private String status;
    private double powerConsumed;
    private int lastForegroundTime;

    private double POWER_FOREGROUND;
    private double POWER_BACKGROUND;
    private double POWER_FOREGROUND_POWERSAVEMODE;
    private double POWER_BACKGROUND_POWERSAVEMODE;

    public Process() {
        runningTime = 0;
        lastForegroundTime = 0;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public double getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(double runningTime) {
        this.runningTime = runningTime;
    }

    public int getLastForegroundTime() {
        return lastForegroundTime;
    }

    public void setLastForegroundTime(int t) {
        this.lastForegroundTime = t;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void flipStatus() {
        System.out.print("Process is flipped from " + this.status);
        this.status = ("Foreground".equals(this.status) ? "Background" : "Foreground");
        System.out.println(" to " + this.status);
    }

    public double getPowerConsumed() {
        return powerConsumed;
    }

    public void setPowerConsumed(double powerConsumed) {
        this.powerConsumed = powerConsumed;
    }

    protected double getPowerForeground() {
        return POWER_FOREGROUND;
    }

    protected void setPowerForeground(double powerForeground) {
        this.POWER_FOREGROUND = powerForeground;
    }

    protected double getPowerBackground() {
        return POWER_BACKGROUND;
    }

    protected void setPowerBackground(double powerBackground) {
        this.POWER_BACKGROUND = powerBackground;
    }

    protected double getPowerForegroundPowerSaveMode() {
        return POWER_FOREGROUND_POWERSAVEMODE;
    }

    protected void setPowerForegroundPowerSaveMode(double powerForegroundPowerSaveMode) {
        this.POWER_FOREGROUND_POWERSAVEMODE = powerForegroundPowerSaveMode;
    }

    protected double getPowerBackgroundPowerSaveMode() {
        return POWER_BACKGROUND_POWERSAVEMODE;
    }

    protected void setPowerBackgroundPowerSaveMode(double powerBackgroundPowerSaveMode) {
        this.POWER_BACKGROUND_POWERSAVEMODE = powerBackgroundPowerSaveMode;
    }

    @Override
    public abstract void executeinForeground(boolean isPowerSaveModeOn, double power);

    @Override
    public abstract void executeinBackground(boolean isPowerSaveModeOn, double power);

    public void consumePower(boolean isPowerSaveModeOn) {

        switch (getStatus()) {
            case "Foreground":
                setPowerConsumed(isPowerSaveModeOn ? POWER_FOREGROUND_POWERSAVEMODE : POWER_FOREGROUND);
                break;
            case "Background":
                setPowerConsumed(isPowerSaveModeOn ? POWER_BACKGROUND_POWERSAVEMODE : POWER_BACKGROUND);
                break;
        }
    }

    public void displayPower(double cpuPower) {

        System.out.print("\nConsumed " + Utils.round(getPowerConsumed()) + "% power.");
        System.out.println("\nRemaining CPU Power - " + Utils.round(cpuPower - getPowerConsumed()) + "%");
    }

    public boolean hasPowerToExecute(double cpuPower, double powerConsumed) {
        return (cpuPower - powerConsumed) > 0;
    }
}
