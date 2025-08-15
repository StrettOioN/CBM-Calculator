package com.cbm.android.cbmcalculatorandroid.tool

import android.content.Context
import android.view.View
import android.widget.TextView
import java.math.BigDecimal
import kotlin.math.exp

class Tools {
    companion object {
        public fun isNumber(value:String): Boolean {
            try {
                val db = BigDecimal(value);
                return !db.toDouble().toString().isEmpty()
            } catch(ex:Exception){return false}
        }

        public fun arithmaticSymbols():ArrayList<String> {
            val arr = ArrayList<String>()
            arr.add("+")
            arr.add("-")
            arr.add(":")
            arr.add("÷")
            arr.add("/")
            arr.add("×")
//            arr.add(".^")
            arr.add("*")
            return arr
        }

        public fun mathSymbols():ArrayList<String> {
            val arr = ArrayList<String>()
            arr.add("π") //PI
            arr.add("φ") //Golden Ratio: (1+√5)÷2=1.6180339887
            //expression symbol collection
//            arr.add("±")
            arr.add("√")
            arr.add("(");
            arr.add(")");
            arr.add("e^n")
            //boolean collection
            arr.add("∞")//SETTING for =zero(but still be infinity in boolean(true/false) situations) or =infinity(making most if not all equations equal to infinity)
            arr.add("≠")//For true false statement (give warning with check for (appearing again))
            arr.add(">")
            arr.add("<")
            arr.add("≈")
            arr.add("≤")
            arr.add("≥")
            arr.add("!")
            //series creation collection
            arr.add("∓")
            arr.add("∑")
            arr.add("∏")
            return arr
        }

        public fun calculate(sym:String, val1:BigDecimal, val2:BigDecimal):BigDecimal {
            var ans = BigDecimal(0)
            when(sym) {
                "+" -> ans = val1.add(val2);
                "-" -> ans = val1.minus(val2);
                "*", "×" -> ans = val1.multiply(val2);
                "/", "÷", ":" -> ans = BigDecimal(val1.toDouble()/val2.toDouble());
            }
            return ans
        }

//        public fun ownCalculate(sym:String, val1:BigDecimal, val2:BigDecimal):BigDecimal {
//            var ans = BigDecimal(0)
//            /*
//            "2.200-2.2000=-0.8200"
//            -0.9200+2.2000=2.200
//            "3.200-2.1000=0.9200"
//            "0.9200+2.1000="
//            */
//
//            when(sym) {
//                "+" -> ans = val1.add(val2, MathContext.DECIMAL128);
//                "-" -> ans = val1.minus(val2);
//                "*", "×" -> ans = val1.multiply(val2);
//                "/", "÷" -> ans = val1.divide(val2, BigDecimal.ROUND_UNNECESSARY);
//            }
//            return ans
//        }

        fun makeTV(c: Context, text:String): TextView {
            val v = View.inflate(c, android.R.layout.simple_expandable_list_item_1, null) as TextView
            v.text = text
            return v
        }

        public fun evaluation(expression:String):String {
            var ans = BigDecimal(0);
            var sym = "+";
            var arrSym = arrayListOf<String>()
            var number="";
            var openBracket = false
            var bCount = 0
            var bracketExpression = ""
            val sqrt = false
            if (expression.contains("÷0")) {
                return "Infinity"
            }
            for(index in 0..expression.length-1) {
                var c = expression[index]+""
                if(openBracket&&bCount>0) {
                    if((c+"").equals("(")) {
                        bCount+=1
                    } else if((c+"").equals(")")) {
                        bCount-=1
                    }
                    if(bCount==0) {
                        openBracket=false

                        if(bracketExpression.startsWith("(")
                            &&bracketExpression.endsWith(")")
                            &&isNumber(bracketExpression.substring(1, bracketExpression.length-2)))
                        {number=bracketExpression;}
                        else{number = evaluateBracket(bracketExpression)}
//                        ans = calculate(sym, ans, number.toBigDecimal())
                    } else {bracketExpression+=c+"";}
                } else if(Tools.isNumber(c+"")||(c+"").equals(".")) {
                    number+=c+""
                    if(number.equals(".")){number="0."}
                } else {
                    if ((sym.equals("/") || sym.equals("÷")) && number.equals("0")) {
                        return "Infinity"
                    }
                    if((c+"").equals("(")) {
                        openBracket=true
                        bCount+=1
                    } else {
                        if((c + "").equals("√")) { arrSym.add("√");number = "";continue;}
//                        if(number.contains("√"))
//                        {number=Math.sqrt(BigDecimal(number.substring(1)).toDouble()).toString()}
                        if (!isNumber(number)) {
                            number = "0"
                        }
                        if((c+"").equals("(")||(c+"").equals(")")){continue}
                        if(Tools.arithmaticSymbols().contains(sym)||Tools.mathSymbols().contains(sym)){
                            val pair = calculateAns(arrSym, number, ans, sym)
                            ans = pair.first
                            number = pair.second
                        }
                        number = ""
                        sym = c + ""
//                        arrSym.add(c+"")
                    }
                }
                if(index>=expression.length-1) {
//                    if (number.contains("√")) {
//                        number = Math.sqrt(BigDecimal(number.substring(1)).toDouble()).toString()
//                    }

                    if (Tools.arithmaticSymbols().contains(sym)||Tools.mathSymbols().contains(sym)) {
                        val pair = calculateAns(arrSym, number, ans, sym)
                        ans = pair.first
                    } else { ans=BigDecimal(number) }
                }
            }

            return ans.toString()
        }

        private fun calculateAns(
            arrSym: ArrayList<String>?,
            number: String,
            ans: BigDecimal,
            sym: String
        ): Pair<BigDecimal, String> {
            var number1 = number
            var ans1 = ans
            if (arrSym!!.size > 0 && isNumber(number1)) {
                for (i in arrSym.size-1 downTo 0) {
                    var symB = arrSym[i]
                    when (symB) {
                        "√" -> {
                            number1 =
                                Math.sqrt(BigDecimal(number1).toDouble()).toString()

                        } else -> {
                            ans1 = calculate(symB, ans1, number1.toBigDecimal())
                        }
                    }

                }
                arrSym.clear()
            }
            ans1 = calculate(sym, ans1, number1.toBigDecimal())
            return Pair(ans1, number1)
        }

        fun evaluateBracket(expre:String):String {
            var expressions = arrayListOf<String>()
//            var expre = expr.replace("2(", "2*").replace(")2", "*2")
            if(expre.contains("(")) {
                var cExp = ""
                var result = ""
                expre.forEachIndexed { index, c ->
                    if(((c+"").equals("(")||((c+"").equals(")"))&&!cExp.isEmpty())) {
                        appendBraInnerExpress(cExp, expressions);cExp="";} else {
                        cExp += c + "";
                    }
                }
                appendBraInnerExpress(cExp, expressions);
                for (a in 0..expressions.size-1 ) {
                    var exp = expressions[a]
                    val lastChar = exp[exp.length-1]+""
//                    if(a==expressions.size-1) {
//                        if(!isNumber(lastChar)&&exp.endsWith("*")) {
//                            exp = "*"+exp.substring(0,exp.length-1)
//                        }
//                    }
                    if(Tools.isNumber(exp)&&!exp.contains("-")){exp="+"+exp}
                    result += exp//evaluation(exp+(if(isNumber(lastChar)){"+"}else{""})+result)
                }
                return evaluation(result)
            } else {
                return evaluation(expre)
            }
        }

        private fun appendBraInnerExpress(
            cExp: String,
            expressions: ArrayList<String>
        ) {
            var cExp1 = cExp
            if (!cExp1.isEmpty()) {
                if (isNumber(cExp1)&&!cExp1.startsWith("+") /*&&isNumber(cExp1[cExp1.length - 1] + "")*/) {
                    cExp1 += "*"
                }
                expressions.add(cExp1)
                cExp1 = ""
            }
        }
    }
}