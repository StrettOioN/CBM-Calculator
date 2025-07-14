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

//            var ans = BigDecimal(0)
//            /*
//            "2.200-2.2000=-0.8200"
//            -0.9200+2.2000=2.200
//            "3.200-2.1000=0.9200"
//            "0.9200+2.1000="
//            */
//
//                "+" -> ans = val1.add(val2, MathContext.DECIMAL128);
//                "-" -> ans = val1.minus(val2);
//                "*", "×" -> ans = val1.multiply(val2);
//                "/", "÷" -> ans = val1.divide(val2, BigDecimal.ROUND_UNNECESSARY);
//            }
//            return ans
//        }

        public fun evaluation(expression:String):String {
            var ans = BigDecimal(0);
            var sym = "+";
            var number="";

            expression.forEachIndexed { index, c ->
                if(Tools.isNumber(c+"")||(c+"").equals(".")) {
                    number+=c+""
                    if(number.equals(".")){number="0."}
                    if(index>=expression.length-1)
                    {ans = calculate(sym, ans, number.toBigDecimal())}
                } else {
                    if(!isNumber(number)){number="0"}
                    if(((c+"").equals("/")||(c+"").equals("÷"))&&number.equals("0"))
                    {return "Infinity"}
                    ans = calculate(sym, ans, number.toBigDecimal())
                    sym = c+""
                    number=""
                }
            }

            return ans.toString()
        }
     }
}