package com.cbm.android.cbmcalculatorandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cbm.android.cbmcalculatorandroid.R
import com.cbm.android.cbmcalculatorandroid.ui.fragment.HistoryFragment

class TabAdapter:FragmentStateAdapter {
    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity)
    constructor(fragment: Fragment) : super(fragment)

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0->{return HistoryFragment()}
            1->{}
        }

        return Fragment(R.layout.layout_history)
    }
}