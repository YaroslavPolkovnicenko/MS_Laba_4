package com.company;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Service {

    private double MO_exponential;
    private double MO_uniform;
    private double SKO_uniform;

    private double a;
    private double b;
    private double ksi;

    private double time_obs;
    private double time_pr;
    private double time_receipt;
    private double current_time;

    private int kol_otk;
    private int kol_request;
    private int kol_channel;
    private int per_otk;

    private ArrayList<Double> time_osv;

    public double getMO_exponential() {
        return MO_exponential;
    }

    public void setMO_exponential(double MO_exponential) {
        this.MO_exponential = MO_exponential;
    }

    public double getMO_uniform() {
        return MO_uniform;
    }

    public void setMO_uniform(double MO_uniform) {
        this.MO_uniform = MO_uniform;
    }

    public double getSKO_uniform() {
        return SKO_uniform;
    }

    public void setSKO_uniform(double SKO_uniform) {
        this.SKO_uniform = SKO_uniform;
    }

    public double getKsi() {
        return ksi;
    }

    public void setKsi(double ksi) {
        this.ksi = ksi;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getTime_obs() {
        return time_obs;
    }

    public void setTime_obs(double time_obs) {
        this.time_obs = time_obs;
    }

    public double getTime_pr() {
        return time_pr;
    }

    public void setTime_pr(double time_pr) {
        this.time_pr = time_pr;
    }

    public double getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(double current_time) {
        this.current_time = current_time;
    }

    public double getTime_receipt() {
        return time_receipt;
    }

    public void setTime_receipt(double time_receipt) {
        this.time_receipt = time_receipt;
    }

    public int getKol_otk() {
        return kol_otk;
    }

    public void setKol_otk(int kol_otk) {
        this.kol_otk = kol_otk;
    }

    public ArrayList<Double> getTime_osv() {
        return time_osv;
    }

    public void setTime_osv(ArrayList<Double> time_osv) {
        this.time_osv = time_osv;
    }

    public int getKol_request() {
        return kol_request;
    }

    public void setKol_request(int kol_request) {
        this.kol_request = kol_request;
    }

    public int getKol_channel() {
        return kol_channel;
    }

    public void setKol_channel(int kol_channel) {
        this.kol_channel = kol_channel;
    }

    public int getPer_otk() {
        return per_otk;
    }

    public void setPer_otk(int per_otk) {
        this.per_otk = per_otk;
    }

    public Service(int kol_request, int kol_channel) {
        this.MO_exponential = 25.0;
        this.MO_uniform = 5.0;
        this.SKO_uniform = 1.0;

        this.a = MO_uniform - SKO_uniform;
        this.b = MO_uniform + SKO_uniform;
        this.ksi = 0.0;
        this.current_time = 0.0;

        this.time_osv = new ArrayList<>();
        this.time_pr = 0.0;
        this.kol_otk = 0;
        this.kol_request = kol_request;
        this.kol_channel = kol_channel;
        this.per_otk = 0;

        time_osv.add(0.0);
        time_osv.add(0.0);
        time_osv.add(0.0);
        time_osv.add(0.0);
        time_osv.add(0.0);

    }

    public double GetKsi() {
        Random r = new Random();

        ksi = r.nextDouble();

        return ksi;
    }

    public double GetUniformY(){
        return a + ((b - a) * GetKsi());
    }

    public double GetExponentialY(){
        return -MO_exponential * Math.log10(GetKsi());
    }

    public void GO(){

        int k = 0;
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);

        for(int i = 0; i < kol_request; i++) {

            //k=0;
            time_receipt = GetUniformY();
            current_time += time_receipt;

            for(k = 0; k < 5; k++) {

                if (time_osv.get(k) <= current_time) {
                    time_obs = GetExponentialY();
                    time_pr += (current_time - time_osv.get(k));
                    time_osv.set(k, (current_time + time_obs));
                    System.out.println("ЗАЯВКУ ОБСЛУЖИВАЕТ КАНАЛ НОМЕР " + k + "; время поступления заявки = "
                            + df.format(current_time) + "; время обслуживания = " + df.format(time_obs) +
                            "; время освобождения канала = " + df.format(time_osv.get(k)));
                    //k = 0;
                    //continue;
                    break;
                }

                else if (k == kol_channel) {
                    kol_otk++;
                    System.out.println("Канал номер " + k + " занят. Заявка была отклонена. Время поступления = " + df.format(current_time) +
                            "; время освобождения канала номер " + k + " = " + df.format(time_osv.get(k)));
                    break;
                }

                else {

                    System.out.println("Канал номер " + k + " занят. Переходим к каналу номер " + (k + 1));
                }
            }
        }

        System.out.println("\n Время простоя = " + df.format(time_pr) + "; отказано заявок = " + kol_otk);
        }

        public double VerOtk(){
        double k = (double) kol_otk;
            return (k / kol_request) * 100;
        }

        public double SrTimePr(){
            return time_pr / kol_channel;
        }
    }