package com.cbm.android.cbmcalculatorandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cbm.android.cbmcalculatorandroid.adapter.HistoryAdapter
import com.cbm.android.cbmcalculatorandroid.databinding.LayoutHistoryBinding

class HistoryFragment:Fragment() {
    lateinit var binding:LayoutHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutHistoryBinding.inflate(layoutInflater)
        binding.rvHistory.adapter = HistoryAdapter(requireActivity())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}