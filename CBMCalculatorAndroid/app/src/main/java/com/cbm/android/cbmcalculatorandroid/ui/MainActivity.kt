package com.cbm.android.cbmcalculatorandroid.ui

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.text.set
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cbm.android.cbmcalculatorandroid.R
import com.cbm.android.cbmcalculatorandroid.adapter.HistoryAdapter
import com.cbm.android.cbmcalculatorandroid.data.model.HomeDatabase
import com.cbm.android.cbmcalculatorandroid.data.model.It
import com.cbm.android.cbmcalculatorandroid.databinding.LayoutMainBinding
import com.cbm.android.cbmcalculatorandroid.databinding.LayoutMathToolsBinding
import com.cbm.android.cbmcalculatorandroid.databinding.TvMathSymbolsBinding
import com.cbm.android.cbmcalculatorandroid.tool.Tools
import com.cbm.android.cbmcalculatorandroid.tool.Tools.Companion.mathSymbols
import com.cbm.android.cbmcalculatorandroid.ui.custom.CButton
import com.cbm.android.cbmcalculatorandroid.ui.custom.ToolPanel
import com.cbm.android.cbmcalculatorandroid.ui.fragment.HistoryFragment
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName
    lateinit var binding:LayoutMainBinding
    lateinit var itvm:It
    lateinit var hdb:HomeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itvm = ViewModelProvider(this).get(It::class.java)
        hdb = HomeDatabase(this)
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)

        binding.Expression.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val styledText = SpannableStringBuilder(it)

                    // Reset spans
                    val spans = styledText.getSpans(0, styledText.length, Any::class.java)
                    for (span in spans) styledText.removeSpan(span)

                    // Example: make digits blue, operators bold
                    for (i in it.indices) {
                        when (it[i]) {
                            in '0'..'9' -> styledText.setSpan(
                                ForegroundColorSpan(Color.BLACK),
                                i, i + 1,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                            '+', '-', '×', '÷' -> {
                                styledText.setSpan(
                                    ForegroundColorSpan(Color.BLUE),
                                    i, i + 1,
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                                styledText.setSpan(
                                    StyleSpan(Typeface.BOLD),
                                    i, i + 1,
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                            }
                        }
                    }

                    binding.Expression.removeTextChangedListener(this)
                    binding.Expression.text = styledText
                    binding.Expression.setSelection(styledText.length) // Keep cursor at end
                    binding.Expression.addTextChangedListener(this)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.llTools.setLeftButton(View.OnClickListener {
            showHistory(it.context);
        })

        binding.llTools.setRightButton{
            var rv = LayoutMathToolsBinding.inflate(layoutInflater)
            for (txt in Tools.mathSymbols()) {
                val tv = TvMathSymbolsBinding.inflate(layoutInflater)
                tv.root.text = txt
                tv.root.setOnClickListener {
                    onBtnClick(it)
                }
                rv.glMathTools.addView(
                    tv.root,
                    resources.getDimensionPixelSize(R.dimen.s50dp),
                    resources.getDimensionPixelSize(R.dimen.s50dp)
                )
            }
            rv.root.tag = ToolPanel.ViewType.TOOLS
            binding.llTools.addToPanel(rv.root);
        }

        binding.btnTools.setOnClickListener {
            binding.llTools.visibility=if(binding.llTools.visibility==View.VISIBLE)
                {View.GONE} else {View.VISIBLE}
            showHistory(it.context)

            toggleTools()
        }

        binding.llTools.callback=(object:ToolPanel.CallBack{
            override fun onCall() {
                toggleTools()
            }
        })
    }

    private fun showHistory(c: Context) {
        var rv = View.inflate(c, R.layout.layout_history, null) as RecyclerView
        rv.adapter = HistoryAdapter(this, HistoryAdapter.OnClickListener()
        { expr, a ->
            binding.Expression.setText(expr)
            quickAnswer()
            binding.Expression.setSelection(expr.length)
        })
        rv.tag = ToolPanel.ViewType.HISTORY
        binding.llTools.addToPanel(rv)
    }

    private fun quickAnswer() {
        try {
            val valid = validateExpression(binding.Expression.text.toString())
            if(binding.Expression.text.toString().contains("÷0")){
                binding.tvDisplay.setText("Infinity")
                return
            }
            val ans = Tools.evaluation(valid) as String
            itvm.display(binding.tvDisplay, ans)
            binding.tvDisplay.setTextColor(Color.parseColor("#80FFFFFF"))
            itvm.standBy = false
        } catch(ex:Exception){ex.printStackTrace()
            if(binding.Expression.text.toString().contains("÷0")) {
                itvm.display(binding.tvDisplay, "Infinity")
            }
        }
    }

    public fun onBtnClick(v:View) {
        if(itvm.standBy) {
            clear()
            itvm.standBy=false
        }

        itvm.onPadClick(v, binding.Expression)
        quickAnswer()
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
            expression = validateExpression(expression)
            val ans = Tools.evaluation(expression)
            itvm.display(binding.tvDisplay, ans)
            binding.tvDisplay.setTextColor(Color.parseColor("#FFFFFFFF"))
            hdb.saveExpression(expression, ans)
            itvm.standBy = true
        }
    }

    private fun validateExpression(expression: String): String {
        var expression1 = expression
        if (expression.contains("÷0")) {
            return "Infinity"
        }
        if (Tools.arithmaticSymbols().contains(expression1[expression1.length - 1] + "")) {
            val lastChar = expression1[expression1.length - 1]+""
            expression1 += if (lastChar.equals("+") || lastChar.equals("-")) {
                "0"
            } else {
                "1"
            }
        }

        return expression1

        /*
        * validation inclusion
        * #( eg. 2( =
        * */
    }

    public fun toggleTools() {
        if(binding.llTools.visibility== View.VISIBLE) {
            binding.btnTools.setSelected(binding.llTools.getView()!!.tag.equals(ToolPanel.ViewType.HISTORY))
            binding.btnTools.isSelected = binding.llTools.getView()!!.tag.equals(ToolPanel.ViewType.TOOLS)
        } else {
            binding.btnTools.setSelected(false)
            binding.btnTools.isSelected = false
        }
    }
}