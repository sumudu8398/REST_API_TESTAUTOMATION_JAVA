package com.model;
/**
 * @author Sumudu on 13/05/2023
 * @project REST_API_TESTAUTOMATION_JAVA
 */
public class Data {
    private int year;
    private double price;
    private String cpuModel;
    private String hardDiskSize;

    public Data() {

    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public String getHardDiskSize() {
        return hardDiskSize;
    }

    public void setHardDiskSize(String hardDiskSize) {
        this.hardDiskSize = hardDiskSize;
    }
}
