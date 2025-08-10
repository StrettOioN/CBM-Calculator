package com.cbm.android.cbmcalculatorandroid.data.model

import android.graphics.Point
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.cbm.android.cbmcalculatorandroid.tool.Tools
import com.cbm.android.cbmcalculatorandroid.ui.custom.CButton
import java.math.BigDecimal

class It: ViewModel() {
    var standBy = true
    var number = "0"
    var numberIndex = Point(0,0)
    var expression = ""
    var initValue = false

    public fun onPadClick(v: View, eV:View) {
        if (v is CButton||v is TextView) {
            val btn = if(v is CButton){v as CButton}else{v as TextView}
            val text = btn.text.toString()
            onPadClick(text, eV)
        }
    }

    public fun onPadClick(text: String, eV:View) {
        var append = false
        if (Tools.isNumber(text)||text.equals(".")){
            append = true
        } else if (Tools.arithmaticSymbols().contains(text)||Tools.mathSymbols().contains(text)) {
            append = true
        }// else if(btn.equals("<-")){backspace(eV); return}
        if (append) {
            try { appendTo(eV, text) }
            catch (ex: Exception) { ex.printStackTrace() }
        } else backspace(eV)
    }

    private fun backspace(eV: View) {
        var ss = (eV as EditText).selectionStart
        if (ss - 1 < 0) {
            ""
        } else eV.text.delete(ss - 1, ss)
        (eV).setSelection(ss - 1)
    }

    private fun appendTo(eV: View, text: String) {
        var ss = (eV as EditText).selectionStart
        //                    var se = (eV as EditText).selectionEnd
        if (ss >= (eV).length()) {
            ss = (eV).length()
        }
        if (ss < 0) {
            ss = 0
        }
        if (Tools.arithmaticSymbols().contains(text)) {
            (eV).text.insert(ss, appendSymbol(eV, text))
            (eV).setSelection(ss + 2); return
        } else {
            if (text.equals(".")) {
                if(getNumber(eV).contains(".")) {
                return}
            } else {
//                var prevChar =if(ss-1<0){""}else {eV.text[ss]+""}
                if(getNumber(eV).equals("0"))backspace(eV)
                ss = eV.selectionStart
            }
            (eV).text.insert(ss, text)
        }
        (eV).setSelection(ss + 1)
    }

    fun appendSymbol(v:View, txt:String):String {
        (v as EditText)
        var prevChar =""
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
//            initValue=true;
        } else if (Tools.arithmaticSymbols().contains(prevChar)
            &&Tools.arithmaticSymbols().contains(txt)&&!txt.equals("-")&&!prevChar.equals("-")){return ""}
        else if((Tools.isNumber(prevChar))&&Tools.arithmaticSymbols().contains(nextChar)) {
            return ""
        } else if(txt.equals("π")) {
            return (if((v).text.length>0){"+"}else{""})+Math.PI.toString()
//            (v).text.insert(ss, appendSymbol(v, entry))
//            (v).setSelection(ss + 1+entry.length); return
        } else if(txt.equals("φ")) {
            val entry = (if((v).text.length>0){"+"}else{""})+"1.6180339887"
            return entry
//            (v).setSelection(ss + 1+entry.length); return
        } else if(txt.equals("∞")) {
            return "∞"
//            (v).text.insert(ss, appendSymbol(v, entry))
//            (v).setSelection(ss + 1+entry.length); return
        } else if(txt.equals("±")) {
            var cN = BigDecimal(getNumber(v)).multiply(BigDecimal(-1))
            if(Tools.isNumber(cN.toString()))
            {number = cN.toString()}
            val et = v as EditText
            et.setText(et.text.removeRange(numberIndex.x, numberIndex.y))
            et.text.insert(numberIndex.x, number)

            return "±"
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
                    var sym = ""
                    if (Tools.arithmaticSymbols().contains(et.text[a].toString())) {
                        symA = a;
                        break;
                    } else symA = a;
                }
                for (a in etE..et.text.length - 1) {
//                number = number + et.text[a].toString()
                    if (Tools.arithmaticSymbols().contains(et.text[a].toString())) {
                        symB = a;
                        break;
                    } else symB = a + 1;
                }

//            if(symB>=et.text.length){symB=et.text.length-1}
                number = et.text.subSequence(symA, symB).toString()
                if(!number.startsWith("-")){
                    number = et.text.subSequence(symA+1, symB).toString()
                    numberIndex = Point(symA+1, symB)}
            } catch(ex:Exception){}
        }

        return number
    }

    fun getLastChar(et:EditText):String {
        var lc = ""
        lc = et.text[et.selectionStart-1]+""
        return lc
    }

}