package com.cbm.android.cbmcalculatorandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.cbm.android.cbmcalculatorandroid.data.model.It
import com.cbm.android.cbmcalculatorandroid.databinding.LayoutMainBinding
import com.cbm.android.cbmcalculatorandroid.tool.Tools

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName
    lateinit var binding:LayoutMainBinding
    lateinit var itvm:It

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itvm = ViewModelProvider(this).get(It::class.java)
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
//        binding.Expression.setOnClickListener {
//            Toast.makeText(it!!.context, "Current Number: "+itvm.getNumber(binding.Expression), Toast.LENGTH_SHORT).show()
//        }
    }

    public fun onBtnClick(v:View) {
        if(itvm.standBy) {
            clear()
            itvm.standBy=false
        }
        itvm.onPadClick(v, binding.Expression)
    }

    private fun clear() {
        binding.tvDisplay.text = ""
        binding.Expression.setText("")

        itvm.standBy=true
    }

    public fun onClear(v:View) {
        clear()
    }
    public fun onEqual(v:View) {
        var expression = binding.Expression.text.toString()
        if(expression.length>0) {
            if (!Tools.isNumber(expression[expression.length - 1] + "")) {
                val lastChar = expression[expression.length - 1]
                expression +=if(lastChar.equals("+")||lastChar.equals("-"))
                { "0" }
                else { "1" }
            }
            itvm.display(binding.tvDisplay, Tools.evaluation(binding.Expression.text.toString()))
            itvm.standBy = true
        }
    }


}