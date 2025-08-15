package com.cbm.android.cbmcalculatorandroid.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import com.cbm.android.cbmcalculatorandroid.R
import com.cbm.android.cbmcalculatorandroid.adapter.HistoryAdapter
import com.cbm.android.cbmcalculatorandroid.adapter.TabAdapter
import com.cbm.android.cbmcalculatorandroid.databinding.LayoutToolsBinding
import com.cbm.android.cbmcalculatorandroid.ui.MainActivity
import com.cbm.android.cbmcalculatorandroid.ui.fragment.HistoryFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.time.times

class ToolPanel: LinearLayout {
    lateinit var binding:LayoutToolsBinding
    private var dX=0f
    private var dY=0f
    enum class ViewType {HISTORY, TOOLS}
    lateinit var callback:CallBack
    constructor(context: Context?) : super(context) {
        custom(null)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        custom(attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        custom(attrs)
    }

    fun custom(attrs: AttributeSet?) {
        binding = LayoutToolsBinding.inflate(LayoutInflater.from(context), this, true)

//        binding.vpT.setAdapter(TabAdapter((context as MainActivity)))
//        TabLayoutMediator(binding.tabTools, binding.vpT, {tab, position ->
//            when(position) {
//                0 -> {tab.text="History"}
//                1 -> {tab.text="Other"}
//            }
//        }).attach()
//        View.inflate(context, R.layout.layout_tools, this)
//        val ta = TypedArray(context.resources., attrs)
        binding.btnTMoveTop.setOnTouchListener(move())
        binding.btnTMoveBottom.setOnTouchListener(move())

//        binding.btnTClose.setOnClickListener { this.visibility= GONE;
//            if(callback!=null){callback.onCall()}}
    }

    private fun move(): (View, MotionEvent) -> Boolean {
        return { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = event.rawX - this.x
                    dY = event.rawY - this.y
                }

                MotionEvent.ACTION_MOVE -> {
                    var fX = event.rawX - dX
                    var fY = event.rawY - dY
                    if(view.equals(binding.btnTMoveTop)&&event.rawX>((context as AppCompatActivity).window.decorView.width.times(0.9f))
                        ||view.equals(binding.btnTMoveTop)&&event.rawY>((context as AppCompatActivity).window.decorView.height.times(0.935f))) {
                        fX=0f
                        fY=0f
                    }
                    this.animate()
                        .x(fX)
                        .y(fY)
                        .setDuration(0)
                        .start()
                }
            }
            true
        }
    }

    fun addToPanel(view: View) {
        binding.llTPanel.removeAllViews()
        binding.llTPanel.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//context.resources.getDimensionPixelSize(R.dimen.s150dp))

//        (c as AppCompatActivity).supportFragmentManager.beginTransaction()
//            .add(R.id.llTPanel, fragment)
////            .attach(fragment)
//            .commit()
    }

    fun setLeftButton(onClick:View.OnClickListener) {
        if(binding!=null) {
            binding.btnTLeft.setOnClickListener(onClick)
        }
    }

    fun setRightButton(onClick:View.OnClickListener) {
        if(binding!=null) {
            binding.btnTRight.setOnClickListener(onClick)
        }
    }

    fun getView():View? {
       return binding.llTPanel.getChildAt(0)
    }

//    fun setOptionsButton(res:Int, onClick:View.OnClickListener) {
//        binding.btnTOptions.setImageResource(res)
//        binding.btnTOptions.scaleType = ImageView.ScaleType.FIT_CENTER
//        binding.btnTOptions.setPadding(context.resources.getDimensionPixelSize(R.dimen.s4dp))
//        binding.btnTOptions.setOnClickListener(onClick)
//    }

    interface CallBack {
        fun onCall()
    }
}