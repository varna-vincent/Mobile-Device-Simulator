package com.mobiledevicesimulation;

import java.util.Scanner;

public class MobileDeviceSimulator {

    public static void main(String[] args) {

        System.out.println("Initiating Mobile Operating System...");

        System.out.print("Enter power remaining in system: ");
        double power = new Scanner(System.in).nextInt();

        MobileOS test = new MobileOS(power);
        test.run();
    }

}
