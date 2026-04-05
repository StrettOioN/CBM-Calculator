package com.cbm.android.cbmcalculator.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.cbm.android.cbmcalculator.R;
import com.cbm.android.cbmcalculator.databinding.LayoutToolPanelBinding;

public class ToolPanel extends LinearLayout {
    LayoutToolPanelBinding binding;
    public ToolPanel(Context context) {
        super(context);
        init();
    }

    public ToolPanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ToolPanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        binding = LayoutToolPanelBinding.inflate(LayoutInflater.from(getContext()), this, true);

    }

}
