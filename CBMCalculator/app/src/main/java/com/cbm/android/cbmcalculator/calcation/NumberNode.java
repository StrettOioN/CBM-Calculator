package com.cbm.android.cbmcalculator.calcation;

public class NumberNode extends ExprNode {
    private final double value;

    public NumberNode(double value) {
        this.value = value;
    }

    @Override
    public double evaluate() {
        return value;
    }
}