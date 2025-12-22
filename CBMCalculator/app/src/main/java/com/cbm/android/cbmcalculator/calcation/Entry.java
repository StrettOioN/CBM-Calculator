package com.cbm.android.cbmcalculator.calcation;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.cbm.android.cbmcalculator.R;
import com.cbm.android.cbmcalculator.tool.Tools;

import ui.cbmtext.CBMButton;
import ui.cbmtext.CBMText;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.ArrayList;

import ui.shapelayout.FlowLayout;

public class Entry {
    private WeakReference<Context> wrc;
    private WeakReference<FlowLayout> expressionUI;
    private ArrayList<String> expression = new ArrayList<>();
    private CBMButton cbmButton;
    private WeakReference<CBMText> currentEntry;
    private View.OnClickListener onClick;
    private int entryIndex = 0;

    public Entry(Context c, View.OnClickListener onClick) {
        wrc = new WeakReference<>(c);
        this.onClick = onClick;
    }

    public Entry(Context c, View.OnClickListener onClick, FlowLayout expression, CBMText currentEntry) {
        wrc = new WeakReference<>(c);
        this.onClick = onClick;
        this.expressionUI = new WeakReference<>(expression);
        this.expression = new ArrayList<>();
        this.currentEntry = new WeakReference<>(currentEntry);
    }

    public CBMButton makeEditr( String text) {
        return makeEditr(text, null);
    }

    public CBMButton makeEditr(String text, View.OnClickListener listener) {
//        cbmText = new CBMButton(wrc.get());
        cbmButton = (CBMButton)View.inflate(wrc.get(), R.layout.layout_entry, null);

//        cbmButton.
        cbmButton.setText(text);
        cbmButton.setOnClickListener(getOnClick());
//        cbmText.getEditText().setOnClickListener(getOnClick());
        
        return cbmButton;
    }
    
    public static void deselectAll(FlowLayout layout) {
        try {
            for (int i = 0; i < layout.getChildCount(); i++) {
                View v = layout.getChildAt(i);
                if (layout != null) {
                    v.setSelected(false);
                    (((CBMButton) v)).setFillColor(Color.WHITE);
                    (((CBMButton) v)).setTextColor(Color.BLACK);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void add(FlowLayout layout, String text) {
        add(expressionUI.get(), makeEditr(text));
    }

    public void add(FlowLayout layout, CBMButton v) {
        add(expressionUI.get(), v, -1);
    }

    public void add(FlowLayout layout, CBMButton v, int index) {
        deselectAll(expressionUI.get());
        entryIndex = index<0? expressionUI.get().getChildCount():index;
        int indexRefine = entryIndex>expressionUI.get().getChildCount()?entryIndex-1:entryIndex;
        cbmButton = v;
        cbmButton.setTag(indexRefine+"");
        expression.add(indexRefine, v!=null?v.getText().toString():"");
        expressionUI.get().addView(v, indexRefine, new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT));
        cbmButton.setFillColor(v.getContext().getColor(R.color.colorAccent));
        cbmButton.setTextColor(Color.WHITE);
        onCTextClick(v, expressionUI.get(), currentEntry.get());
        reIndex(expressionUI.get());
    }

    public CBMButton getCbmButton() {
        return cbmButton;
    }

    public void setCbmButton(CBMButton cbmButton) {
        this.cbmButton = cbmButton;
        try{this.entryIndex = Tools.isNumber(cbmButton.getTag().toString())?new BigDecimal(cbmButton.getTag().toString()).intValueExact(): getExpression().size();}
        catch(Exception ex){ex.printStackTrace();this.entryIndex=getExpression().size();}
   }

    public int getEntryIndex() {
        try {this.entryIndex = Tools.isNumber(cbmButton.getTag().toString())?new BigDecimal(cbmButton.getTag().toString()).intValueExact(): getExpression().size();}
        catch (Exception ex) {ex.printStackTrace();}//this.entryIndex=getExpression().size();}
        return entryIndex;
    }

    public void setEntryIndex(int entryIndex) {
        this.entryIndex = entryIndex;
    }

    public View.OnClickListener getOnClick() {
        return onClick;
    }

    public void setOnClick(View.OnClickListener onClick) {
        this.onClick = onClick;
    }

    public void onCTextClick(View view, FlowLayout layout, CBMText cbmt) {
        reIndex(getExpressionUI().get());
        setCbmButton((CBMButton) view);
        String ct = cbmButton.getText().toString();
        if(Tools.isNumber(ct)||ct.isEmpty()) {
            Log.d("CBMButton", "Btn: "+ cbmButton.getText()+" is pressed.");
            cbmt.setText(cbmButton.getText());
            deselectAll(expressionUI.get());
            cbmButton.setSelected(!cbmButton.isSelected());
            cbmButton.setFillColor(view.getContext().getColor(R.color.colorAccent));
            cbmButton.setTextColor(Color.WHITE);
            cbmt.getEditText().requestFocus();
            cbmt.getEditText().selectAll();
            cbmt.getEditText().setSelection(cbmt.getEditText().getSelectionEnd());
        }
    }

    public void reIndex(FlowLayout layout) {
        for (int i = 0; i < expressionUI.get().getChildCount(); i++) {
            expressionUI.get().getChildAt(i).setTag(i+"");
        }
    }

    public WeakReference<FlowLayout> getExpressionUI() {
        return expressionUI;
    }

    public ArrayList<String> getExpression() {
        return expression;
    }

    public CBMText getCurrentEntry() {
        return currentEntry.get();
    }
}
