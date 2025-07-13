package com.cbm.android.cbmcalculatorandroid.data.model

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cbm.android.cbmcalculatorandroid.MainActivity
import com.cbm.android.cbmcalculatorandroid.tool.Tools
import java.math.BigDecimal

class It: ViewModel() {
    var standBy = true
    var number = "0"
    var expression = ""
    var initValue = false

    public fun onPadClick(v: View, eV:View) {
        if (v is Button) {
            val btn = v as Button
            val text = btn.text.toString()
            var append = false
            if (Tools.isNumber(text)) {
                append = true
            } else if (Tools.symbols().contains(text)) {
                append = true
            }
            if (append) {
                try {
                    var ss = (eV as EditText).selectionStart
//                    var se = (eV as EditText).selectionEnd
                    if(ss>=(eV).length()){ss=(eV).length()}
                    if(ss<0){ss=0}
                    if (Tools.symbols().contains(text)) {
                        (eV).text.insert(ss, appendSymbol(eV, text))
                    } else {
                        if(getNumber(eV).contains(".")){return}
                        (eV).text.insert(ss, text)
                    }
                    (eV).setSelection(ss + 1)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }

        } else if (v is ImageButton) {

        }
    }

    fun appendSymbol(v:View, txt:String):String {
        (v as EditText)
        var prevChar=""
        var nextChar=""
        var ss = v.selectionStart-1
        prevChar =if(ss<0){""}else {v.text[ss]+""}
        nextChar = if(ss+1<v.length()){v.text[ss+1]+""}else{""}
        if(prevChar.isEmpty()) {
            when(txt) {
                "+" -> return "0+";
                "-" -> return "0-";
                "*", "×" -> return "1×";
                "/", "÷" -> return "1÷";
            }
        } else if (Tools.symbols().contains(prevChar)){return ""}
        else if((Tools.isNumber(prevChar))&&Tools.symbols().contains(nextChar)) {
            return ""
//            when(txt) {
//                "+" -> return "+0";
//                "-" -> return "-0";
//                "*", "×" -> return "×1";
//                "/", "÷" -> return "÷1";
//            }

            initValue=true;
        }
        return txt
    }

    public fun display(v:View, value:String) {
        (v as TextView).text = Tools.evaluation(value)
    }

    public fun getNumber(v:View):String {
        if(v is EditText) {
            val et = v as EditText
            var symA=0; var symB=0
            var etS = et.selectionStart-1
            var etE = et.selectionEnd
            if(etS<0){etS=0}
            if(etS>=et.text.length){etS=et.text.length-1}
            if(etE>=et.text.length){etE=et.text.length-1}
            number = ""
            try {
                for (a in etS downTo 0) {
//                number = et.text[a].toString() + number
                    if (Tools.symbols().contains(et.text[a].toString())) {
                        symA = a + 1;
                        break;
                    } else symA = a;
                }
                for (a in etE..et.text.length - 1) {
//                number = number + et.text[a].toString()
                    if (Tools.symbols().contains(et.text[a].toString())) {
                        symB = a;
                        break;
                    } else symB = a + 1;
                }

//            if(symB>=et.text.length){symB=et.text.length-1}
                number = et.text.subSequence(symA, symB).toString()
            } catch(ex:Exception){}
        }

        return number
    }

}