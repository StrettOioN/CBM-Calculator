package com.cbm.android.cbmcalculator.tool;

import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;

public class AutoDeleteHandler {
    EditText editText;
    long intervalMs=100;

    public AutoDeleteHandler(EditText editText, long intervalMs) {
        this.editText = editText;
        this.intervalMs = intervalMs;
    }

    Handler handler = new Handler(Looper.getMainLooper());
    Runnable deleteRunnable = new Runnable() {
        @Override
        public void run() {
            backspaceAtCursor(editText);
            handler.postDelayed(this, intervalMs);
        }
    };

    public void start() {
        handler.post(deleteRunnable);
    }

    public void stop() {
        handler.removeCallbacks(deleteRunnable);
    }

    public static void backspaceAtCursor(EditText editText) {
        int pos = editText.getSelectionStart();
        if (pos > 0) {
            editText.getText().delete(pos - 1, pos);
            editText.setSelection(pos - 1);
        }
    }
}
