package com.cbm.android.cbmcalculator.tool;

public class BooleanOperators {
    public static boolean notEqual(double a, double b) { // ≠
        return a != b;
    }

    public static boolean greaterThan(double a, double b) { // >
        return a > b;
    }

    public static boolean lessThan(double a, double b) { // <
        return a < b;
    }

    public static boolean approximatelyEqual(double a, double b, double tolerance) { // ≈
        return Math.abs(a - b) <= tolerance;
    }

    public static boolean lessThanOrEqual(double a, double b) { // ≤
        return a <= b;
    }

    public static boolean greaterThanOrEqual(double a, double b) { // ≥
        return a >= b;
    }

    public static boolean isInfinity(double value) { // ∞
        return Double.isInfinite(value);
    }
}