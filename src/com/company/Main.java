package com.company;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Main {

    public static void main(String[] args) {

        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);

        Service service = new Service(20, 4);

        service.GO();

        System.out.println("Среднее время простоя = " + df.format(service.SrTimePr()) + "; вероятность отказа = " + service.VerOtk() + " %");
    }
}