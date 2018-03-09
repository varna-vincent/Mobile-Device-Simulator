package com.mobiledevicesimulation;

public abstract class Process implements Activity {

    private int pid;
    private double runningTime;
    private String status;
    private double powerConsumed;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPowerConsumed() {
        return powerConsumed;
    }

    public void setPowerConsumed(double powerConsumed) {
        this.powerConsumed = powerConsumed;
    }

    public abstract void consumePower();

    @Override
    public abstract void executeinForeground(boolean isPowerSaveModeOn);

    @Override
    public abstract void executeinBackground(boolean isPowerSaveModeOn);
}
