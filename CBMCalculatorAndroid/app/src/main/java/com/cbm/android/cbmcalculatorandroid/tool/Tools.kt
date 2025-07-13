package com.cbm.android.cbmcalculatorandroid.tool

import java.math.BigDecimal

class Tools {
    companion object {
        public fun isNumber(value:String): Boolean {
            try {
                val db = BigDecimal(value);
                return !db.toDouble().toString().isEmpty()
            } catch(ex:Exception){return false}
        }

        public fun symbols():ArrayList<String> {
            val arr = ArrayList<String>()
            arr.add("+")
            arr.add("-")
            arr.add("÷")
            arr.add("/")
            arr.add("×")
            arr.add("*")
            return arr
        }

        public fun calculate(sym:String, val1:BigDecimal, val2:BigDecimal):BigDecimal {
            var ans = BigDecimal(0)
            when(sym) {
                "+" -> ans = val1.add(val2);
                "-" -> ans = val1.minus(val2);
                "*", "×" -> ans = val1.multiply(val2);
                "/", "÷" -> ans = val1.divide(val2, BigDecimal.ROUND_UNNECESSARY);
            }
            return ans
        }

        public fun evaluation(expression:String):String {
            var ans = BigDecimal(0);
            var sym = "+";
            var number="";

            expression.forEachIndexed { index, c ->
                if(Tools.isNumber(c+"")) {
                    number+=c+""
                    if(index>=expression.length-1)
                    {ans = calculate(sym, ans, number.toBigDecimal())}
                } else {
                    ans = calculate(sym, ans, number.toBigDecimal())
                    sym = c+""
                    number=""
                }
            }

            return ans.toString()
        }
     }
}