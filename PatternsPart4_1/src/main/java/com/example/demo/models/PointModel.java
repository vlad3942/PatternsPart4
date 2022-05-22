package com.example.demo.models;

public class PointModel {

    private static final Function FUNCTION = CosPlus2Sin.getInstance();

    private final double x;
    private final double y;

    public PointModel() {
        this.x = 0.0;
        this.y = FUNCTION.calculate(this.x);
    }

    public PointModel(double x) {
        this.x = x;
        this.y = FUNCTION.calculate(this.x);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
