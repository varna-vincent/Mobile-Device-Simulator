package com.mobiledevicesimulation;

import java.util.Scanner;

public class MobileDeviceSimulator {

    public static void main(String[] args) {

        System.out.println("Initiating Mobile Operating System...");

        System.out.println("Enter power remaining in system: ");
        double power = new Scanner(System.in).nextInt();

        new MobileOS(power);
    }
}
