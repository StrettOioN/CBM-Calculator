package com.cbm.android.cbmcalculator.calcation;

public class OperatorNode extends ExprNode {
    private final String operator;
    private final ExprNode left, right;

    public OperatorNode(String operator, ExprNode left, ExprNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate() {
        double a = left.evaluate();
        double b = right.evaluate();

        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "×" -> a * b;
            case "*" -> a * b;
            case "÷" -> a / b;
            case "/" -> a / b;
            case "^" -> Math.pow(a, b);
            default -> throw new UnsupportedOperationException("Unknown operator: " + operator);
        };
    }
}