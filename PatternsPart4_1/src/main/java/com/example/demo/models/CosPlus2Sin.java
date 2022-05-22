package com.example.demo.models;

public class CosPlus2Sin implements Function {

    private static volatile Function instance = null;

    @Override
    public double calculate(double x) {
        return Math.cos(x) + 2 * Math.sin(2 * x);
    }

    public static Function getInstance() {
        if (instance == null) {
            synchronized (CosPlus2Sin.class) {
                if (instance == null) {
                    instance = new CosPlus2Sin();
                }
            }
        }
        return instance;
    }
}
