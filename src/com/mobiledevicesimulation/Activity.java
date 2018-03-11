package com.mobiledevicesimulation;

 public interface Activity {

    public void executeinForeground(boolean isPowerSaveModeOn, double power);

    public void executeinBackground(boolean isPowerSaveModeOn, double power);
}
