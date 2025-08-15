package com.cbm.android.cbmcalculatorandroid.ui.custom

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setMargins
import com.cbm.android.cbmcalculatorandroid.R

class CButton: AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
    
    attrs,
        defStyleAttr
    )

    init {
        /*
        * android:android:fontFamily = @string/m3_ref_typeface_plain_medium => sans-serif-medium 
    android:android:letterSpacing = 0.009375 
    android:android:lineHeight = 24sp 
    android:android:textAllCaps = false 
    android:android:textSize = 16sp fontFamily = @string/m3_ref_typeface_plain_medium => sans-serif-medium     lineHeight = 24s*/
        setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL)
//        setLineSpacing(1f, 0.009375f)
        setLineHeight(TypedValue.COMPLEX_UNIT_SP, resources.getDimension(R.dimen.s24dp))
        setAllCaps(true)
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        setMinHeight(resources.getDimensionPixelSize(R.dimen.s50dp))
        setPadding(resources.getDimensionPixelSize(R.dimen.s16dp),0,
        resources.getDimensionPixelSize(R.dimen.s16dp),0)
        setBackground(ResourcesCompat.getDrawable(resources, R.drawable.btnbg_normal, null))
        setGravity(Gravity.CENTER)
        setTextColor(Color.BLACK)
    }
}