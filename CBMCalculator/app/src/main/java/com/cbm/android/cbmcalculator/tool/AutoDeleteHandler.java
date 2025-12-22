package com.cbm.android.cbmcalculator.tool;

import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;

import com.cbm.android.cbmcalculator.calcation.Entry;

import java.math.BigDecimal;

import ui.cbmtext.CBMButton;
import ui.cbmtext.CBMText;
import ui.shapelayout.FlowLayout;

public class AutoDeleteHandler {
    FlowLayout expression;
    Entry entry;
    long intervalMs=100;
    public int cbmtIndex = 0;

    public AutoDeleteHandler(FlowLayout expression, Entry entry, long intervalMs) {
        this.expression = expression;
        this.entry = entry;
        this.intervalMs = intervalMs;
    }

    public AutoDeleteHandler(CBMText cbmText, long intervalMs) {
        this.entry = entry;
        this.intervalMs = intervalMs;
    }

    Handler handler = new Handler(Looper.getMainLooper());
    Runnable deleteRunnable = new Runnable() {
        @Override
        public void run() {
            backspaceAtCursor(/*entry*/);
            handler.postDelayed(this, intervalMs);
        }
    };

    public void start() {
        handler.post(deleteRunnable);
    }

    public void stop() {
        handler.removeCallbacks(deleteRunnable);
    }

    public void backspaceAtCursor(/*Entry entry*/) {
        try {
            EditText editText = entry.getCurrentEntry().getEditText();
            if(areSelections()) {
                int pos = editText.getSelectionStart();
                if (pos > 0) {
                    editText.getText().delete(pos - 1, pos);
                    editText.setSelection(pos - 1);
//                    cbmtIndex = cbmtIndex - 1 > -1 ? cbmtIndex - 1 : 0;
                    return;
                }
                int count = getExpression().getChildCount();
                if (editText.getText().toString().isEmpty()) {
                    if (getExpression() != null) {
                        if (cbmtIndex == 0) {
                            boolean hasMinus = false;
                            removeItemAt(cbmtIndex);
                            hasMinus = (((CBMButton) getExpression().getChildAt(cbmtIndex)).getText().toString().equalsIgnoreCase("-"));
                            removeItemAt(cbmtIndex);
                            if (hasMinus) {
                                String num = ((CBMButton) getExpression().getChildAt(cbmtIndex)).getText().toString();
                                if(Tools.isNumber(num)) {
                                    ((CBMButton) getExpression().getChildAt(cbmtIndex)).setText(new BigDecimal(num).negate().toString());
                                }
                            }
                            count = getExpression().getChildCount();
                            entry.onCTextClick(getExpression().getChildAt(count-1), getExpression(), entry.getCurrentEntry());
                        } else {
                            removeItemAt(cbmtIndex-1);
                            removeItemAt(cbmtIndex-1);
                            entry.reIndex(getExpression());
                            count = getExpression().getChildCount();
                            entry.onCTextClick(getExpression().getChildAt(count-1), getExpression(), entry.getCurrentEntry());
                        }
                    }

//                    count = getExpression().getChildCount();
//                    cbmtIndex=count-1;
                    entry.reIndex(getExpression());
//                    entry.setEntryIndex(cbmtIndex);
                }
            }
        } catch (Exception ex){ex.printStackTrace();}
    }

    private void removeItemAt(int index) {
        getExpression().removeViewAt(index);
        entry.getExpression().remove(index);
    }

//    public static FlowLayout getStaticExpression(AutoDeleteHandler a) {
//        return a!=null?a.getExpression():null;
//    }

    public FlowLayout getExpression() {
        return entry.getExpressionUI().get();
    }

    public void setExpression(FlowLayout expression) {
        this.expression = expression;
    }

    public boolean areSelections() {
        for (int i = 0; i < getExpression().getChildCount(); i++) {
            if(getExpression().getChildAt(i).isSelected()) {
                return true;
            }
        }
        return false;
    }

    public CBMText getEditText() {
        return entry.getCurrentEntry();
    }

//    public void setEditText(CBMText cbmText) {
//        this.entry.getCurrentEntry() = cbmText;
//    }

    public int getCbmtIndex() {
        return cbmtIndex;
    }

    public void setCbmtIndex(int cbmtIndex) {
        this.cbmtIndex = cbmtIndex;
    }
}
