package com.mobiledevicesimulation;

public interface Activity {

    public void executeinForeground(boolean isPowerSaveModeOn);

    public void executeinBackground(boolean isPowerSaveModeOn);
}
