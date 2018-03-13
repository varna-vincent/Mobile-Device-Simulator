package com.mobiledevicesimulation;

import com.mobiledevicesimulation.utils.Utils;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public abstract class Process implements Activity {

    private long pid;
    private double runningTime;
    private String status;
    private String name;
    private double powerConsumed;
    private java.lang.Process process;
    private int lastForegroundTime;

    private double POWER_FOREGROUND;
    private double POWER_BACKGROUND;
    private double POWER_FOREGROUND_POWERSAVEMODE;
    private double POWER_BACKGROUND_POWERSAVEMODE;

    public Process() {

        try {
//            Runtime r = Runtime.getRuntime();
//            process = r.exec("ping -c 3 google.com");
            process = new ProcessBuilder("/Users/get2binoy/MobileDeviceSimulation/launch.sh").start();
            dumpProcessInfo(process.toHandle());
            process.waitFor(10, TimeUnit.SECONDS);
            setPid(process.pid());
            System.out.println("\nPid - " + getPid());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
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

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public double getPowerConsumed() {
        return powerConsumed;
    }

    public void setPowerConsumed(double powerConsumed) {
        this.powerConsumed = powerConsumed;
    }

    public int getLastForegroundTime() {  return lastForegroundTime; }

    public void setLastForegroundTime(int lastForegroundTime) { this.lastForegroundTime = lastForegroundTime; }

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
            case "Foreground": setPowerConsumed(isPowerSaveModeOn ? POWER_FOREGROUND_POWERSAVEMODE : POWER_FOREGROUND); break;
            case "Background": setPowerConsumed(isPowerSaveModeOn ? POWER_BACKGROUND_POWERSAVEMODE : POWER_BACKGROUND); break;
        }
    }

    public void displayPower(double cpuPower) {

        System.out.print("\n" + getName() + " (PID - " + getPid() + ") consumed " + Utils.round(getPowerConsumed()) + "% power.");
        System.out.println("\nRemaining Battery - " + Utils.round(cpuPower - getPowerConsumed())+ "%");
    }

    public boolean hasPowerToExecute(double cpuPower, double powerConsumed) {
        return (cpuPower - powerConsumed) > 0;
    }

    public void sleep() {
        System.out.print(getName() + " (Pid - " + getPid() + ") will be put to sleep.");
        process.destroy();
    }

    void dumpProcessInfo(ProcessHandle ph)
    {
        System.out.println("\n\nPROCESS INFORMATION");
        System.out.println("===================");
        System.out.printf("Process id: %d%n", ph.pid());
        ProcessHandle.Info info = ph.info();
        System.out.printf("Command: %s%n", info.command().orElse(""));
        String[] args = info.arguments().orElse(new String[]{});
        System.out.println("Arguments:");
        for (String arg: args)
            System.out.printf("   %s%n", arg);
        System.out.printf("Command line: %s%n", info.commandLine().orElse(""));
        System.out.printf("Start time: %s%n",
                info.startInstant().orElse(Instant.now()).toString());
        System.out.printf("Run time duration: %sms%n",
                info.totalCpuDuration()
                        .orElse(Duration.ofMillis(0)).toMillis());
        System.out.printf("Owner: %s%n", info.user().orElse(""));
        System.out.println();
    }

    public void flipStatus() {
        System.out.print("\n" + getName() + " (Pid - " + getPid() + ") has been flipped from " + this.status);
        this.status = ("Foreground".equals(this.status) ? "Background" : "Foreground");
        System.out.println(" to " + this.status);
    }
}
