package com.cbm.android.cbmcalculator.calcation;

import com.cbm.android.cbmcalculator.tool.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Tokenizer {
    public static final Set<String> SYMBOLS = Set.of("π", "φ", "√", "∑", "∏", "∞", "≠", "≈", "≤", "≥", "!", "e^n");

    public static List<Token> tokenize(String expr) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        try {

            for (char c : expr.toCharArray()) {
                if (Character.isWhitespace(c)) continue;
                if (!Tools.isNumber(c + "")) {
                    buffer.setLength(0);
                }
                buffer.append(c);
                String current = buffer.toString();

                if (SYMBOLS.contains(current)) {
                    tokens.add(new Token(Token.Type.SYMBOL, current));
                    buffer.setLength(0);
                } else if ("()+-*/÷×^".indexOf(c) >= 0) {
                    tokens.add(new Token(Token.Type.OPERATOR, String.valueOf(c)));
                    buffer.setLength(0);
                } else if (Character.isDigit(c) || c == '.') {
                    // Continue building number

                    if ((buffer.length() > 0 && tokens.size()==0) || (tokens.size() > 0 && !Tools.isNumber(tokens.get(tokens.size() - 1).value))) {
                        tokens.add(new Token(Token.Type.NUMBER, buffer.toString()));
                    } else tokens.set(tokens.size() - 1, new Token(Token.Type.NUMBER, String.valueOf(buffer)));
                } else {
                    // Handle variables or functions
                    tokens.add(new Token(Token.Type.FUNCTION, String.valueOf(c)));
                }
            }

//            if (buffer.length() > 0) {
//                tokens.add(new Token(Token.Type.NUMBER, buffer.toString()));
//            }
        } catch(Exception ex){ex.printStackTrace();}

        return tokens;
    }
}