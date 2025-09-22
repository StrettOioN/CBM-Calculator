package com.cbm.android.cbmcalculator.calcation;

import java.util.List;

public class ExpressionCompiler {
    public static double compile(String expression, boolean bodmas) {
        List<Token> tokens = Tokenizer.tokenize(expression);
        List<Token> rpn = ToGether.toRPN(tokens, bodmas);
        ExprNode root = ASTBuilder.build(rpn);
        return root.evaluate();
    }

//    public static void main(String[] args) {
//        System.out.println(compile("π + φ * 2"));       // 3.1415 + 1.618 * 2
//        System.out.println(compile("√(φ^2 + π)"));       // sqrt(φ² + π)
//        System.out.println(compile("e^n + 5"));          // e + 5
////        System.out.println(compile("5 + 5"));
//    }

}