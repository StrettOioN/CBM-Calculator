package com.cbm.android.cbmcalculatorandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.cbm.android.cbmcalculatorandroid.databinding.LayoutMainBinding
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    lateinit var binding:LayoutMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    public fun onBtnClick(v:View) {
        if(v is Button) {
            val btn = v as Button
            val text = btn.text.toString()
            var append = false
            if(Tools.isNumber(text)) {
                append = true
            } else if(Tools.expression().contains(text)) {
                append = true
            } else if(text.equals("=")) {
                binding.tvDisplay.text = evaluation(binding.tvExpression.text.toString())
            }
            if(append){binding.tvExpression.append(text)}

        } else if(v is ImageButton) {

        }
    }

    public fun calculate(sym:String, val1:BigDecimal, val2:BigDecimal):BigDecimal {
        var ans = BigDecimal(0)
        when(sym) {
            "+" -> ans = val1.add(val2);
            "-" -> ans = val1.minus(val2);
            "*", "+" -> ans = val1.multiply(val2);
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