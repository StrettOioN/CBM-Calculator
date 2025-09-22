package com.cbm.android.cbmcalculator.calcation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cbm.android.cbmcalculator.R;
import com.cbm.android.cbmcalculator.tool.Tools;
import com.cbm.android.cbmtext.CBMText;
import com.cbm.android.dbutton.DButton;

import java.lang.ref.WeakReference;

import ui.shapelayout.FlowLayout;
import ui.shapelayout.ShapeLayout;

public class Entry {
    private WeakReference<Context> wrc;
    private DButton cbmText;
    private View.OnClickListener onClick;
    public Entry(Context c, View.OnClickListener onClick) {
        wrc = new WeakReference<>(c);
        this.onClick = onClick;
    }

    public DButton makeEditr( String text) {
        return makeEditr(text, null);
    }

    public DButton makeEditr(String text, View.OnClickListener listener) {
//        cbmText = new DButton(wrc.get());
        cbmText = (DButton)View.inflate(wrc.get(), R.layout.layout_entry, null);
//        ((ViewGroup.LayoutParams)cbmText.getLayoutParams()).setMargins(
//                c.getResources().getDimensionPixelSize(R.dimen.dim4dp),
//                c.getResources().getDimensionPixelSize(R.dimen.dim4dp),
//                c.getResources().getDimensionPixelSize(R.dimen.dim4dp),
//                c.getResources().getDimensionPixelSize(R.dimen.dim4dp));
//        cbmText.setPadding(
//                wrc.get().getResources().getDimensionPixelSize(R.dimen.dim4dp),
//                0,
//                wrc.get().getResources().getDimensionPixelSize(R.dimen.dim4dp),
//                0);

//        cbmText.setGravity(Gravity.CENTER);
//        cbmText.setEditable(false);
//        cbmText.setClickable(true);
//        cbmText.setFocusable(true);
//        cbmText.getEditText().setClickable(true);
//        cbmText.getEditText().setFocusable(true);

//        cbmText.setTextColor(Color.WHITE);
//        cbmText.setFillColor(Color.BLACK);
//        cbmText.setStroke(Color.parseColor("#88FFFFFF"), 4);
//        cbmText.setRadius(25F);
//        cbmText.setTextSize(20, TypedValue.COMPLEX_UNIT_SP);
        cbmText.setText(text);
        cbmText.setOnClickListener(getOnClick());
//        cbmText.getEditText().setOnClickListener(getOnClick());
        
        return cbmText;
    }
    
    public static void deselectAll(FlowLayout layout) {
        try {
            for (int i = 0; i < layout.getChildCount(); i++) {
                View v = layout.getChildAt(i);
                if (layout != null) {
                    v.setSelected(false);
                    ((DButton)v).setFillColor(Color.BLACK);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void add(FlowLayout layout, String text) {
        add(layout, makeEditr(text));
    }

    public void add(FlowLayout layout, DButton v) {
        layout.addView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    public DButton getCbmText() {
        return cbmText;
    }

    public void setCbmText(DButton cbmText) {
        this.cbmText = cbmText;
    }

    public View.OnClickListener getOnClick() {
        return onClick;
    }

    public void setOnClick(View.OnClickListener onClick) {
        this.onClick = onClick;
    }
}
