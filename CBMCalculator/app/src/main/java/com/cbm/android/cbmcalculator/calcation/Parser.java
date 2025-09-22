package com.cbm.android.cbmcalculator.calcation;

import java.util.List;

public class Parser {
    public static ExprNode parse(List<Token> tokens) {
        // Minimal: parse single symbol or number
        Token token = tokens.get(0);
        return switch (token.type) {
            case NUMBER -> new NumberNode(Double.parseDouble(token.value));
            case SYMBOL -> new SymbolNode(token.value);
            default -> throw new IllegalArgumentException("Unsupported token: " + token.value);
        };
    }
}