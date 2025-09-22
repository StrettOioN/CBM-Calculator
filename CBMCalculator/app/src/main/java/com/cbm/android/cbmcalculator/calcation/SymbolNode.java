package com.cbm.android.cbmcalculator.calcation;

public class SymbolNode extends ExprNode {
    private final String symbol;

    public SymbolNode(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public double evaluate() {
        return switch (symbol) {
            case "π" -> Math.PI;
            case "φ" -> (1 + Math.sqrt(5)) / 2;
            case "∞" -> Double.POSITIVE_INFINITY;
            case "e^n" -> Math.E; // Placeholder
            default -> throw new UnsupportedOperationException("Unknown symbol: " + symbol);
        };
    }
}