package com.cbm.android.cbmcalculator.calcation;

import java.util.Map;

public class OperatorInfo {
    public final int precedence;
    public final boolean rightAssociative;

    public OperatorInfo(int precedence, boolean rightAssociative) {
        this.precedence = precedence;
        this.rightAssociative = rightAssociative;
    }

    public static final Map<String, OperatorInfo> OPERATORS = Map.of(
            "+", new OperatorInfo(1, false),
            "-", new OperatorInfo(1, false),
            "*", new OperatorInfo(2, false),
            "/", new OperatorInfo(2, false),
            "×", new OperatorInfo(2, false),
            "÷", new OperatorInfo(2, false),
            "^", new OperatorInfo(3, true)
    );
}