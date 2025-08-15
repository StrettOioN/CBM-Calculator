package com.cbm.android.cbmcalculatorandroid.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.cbm.android.cbmcalculatorandroid.R
import com.cbm.android.cbmcalculatorandroid.databinding.LayoutToolsBinding
import com.cbm.android.cbmcalculatorandroid.databinding.TvMathSymbolsBinding

class TVMathSymbol: androidx.appcompat.widget.AppCompatTextView {
    lateinit var binding:TvMathSymbolsBinding
    constructor(context: Context) : super(context!!)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        binding = TvMathSymbolsBinding.inflate(LayoutInflater.from(context), null, true)

    }
}