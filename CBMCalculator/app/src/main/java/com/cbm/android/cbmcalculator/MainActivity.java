package com.cbm.android.cbmcalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.cbm.android.cbmcalculator.calcation.Entry;
import com.cbm.android.cbmcalculator.calcation.Token;
import com.cbm.android.cbmcalculator.tool.AutoDeleteHandler;
import com.cbm.android.cbmcalculator.tool.Tools;
import com.cbm.android.dbutton.DButton;
import com.cbm.android.cbmcalculator.calcation.Calcation;
import com.cbm.android.cbmcalculator.calcation.ExpressionCompiler;
import com.cbm.android.cbmcalculator.databinding.ActivityMainBinding;
import com.cbm.android.cbmcalculator.settings.AppSettings;
import com.cbm.android.cbmtext.CBMText;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppSettings sets;
    private CBMText cText;
    private Entry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sets = new AppSettings(this);
        entry = new Entry(this, (v)->onCTextClick(v));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
//        entry.onClick = (v)->onCTextClick(((CBMText)v) );
        entry.makeEditr("");
        binding.btnBODMAS.setSelected(sets.getBODMAS());

        for(int x=0; x<binding.slPad.getChildCount(); x++) {
            View vA = binding.slPad.getChildAt(x);
            vA.setOnClickListener(v-> {
            try {
                DButton sb = null;
                if (v instanceof DButton) sb = (DButton) v;
                String text = sb.getText().toString();
                if (binding != null) {
                    String s = binding.stExpressCurrNr.getText();
//                    switch(text) {
//                        check what type of token
//                    }
                    if(text.equalsIgnoreCase("=")) {
                        s="";
                        String ans = getAnswer();
                        binding.ctAnswer.setText(ans);
                        sets.saveStandBy(true);
                        return;
                    } else if(text.equalsIgnoreCase("C")) {
                        clear();
                        return;
                    }
                    try {
                        if((s!=null&&!s.isEmpty()&&Tools.isNumber(s)&&new BigDecimal(s).doubleValue()==0)||sets.getStandBy())
                        {clear();sets.saveStandBy(false);}
                    } catch(Exception ex){ex.printStackTrace();}
                    if(Tools.isNumber(text)||text.equals(".")/*&&cText!=null*/) {
                        if(binding.slExpression.getChildCount()<1) {
                            entry.add(binding.slExpression, entry.makeEditr(""));
                        }
                        if(text.equals(".")&&s.contains(".")) {
                            return;
                        }
                        EditText editText = binding.stExpressCurrNr.getEditText();
                        int start = editText.getSelectionStart();
                        int end = editText.getSelectionEnd();
                        editText.getText().replace(Math.min(start, end), Math.max(start, end), text);
                        editText.setSelection(start + text.length());
                        s = editText.getText().toString();
                        entry.getCbmText().setText(s);
                    } else {
                        entry.add(binding.slExpression, entry.makeEditr(text));
                        entry.add(binding.slExpression, entry.makeEditr(""));
                        binding.stExpressCurrNr.setText("");
//                        updateItems();

//                        cText.setOnClickListener(view-> {
//                            onCTextClick();
//                        });
                    }
                    binding.slExpression.invalidate();
//                    binding.stExpressCurrNr.append(text);
                }
            } catch(Exception ex){ex.printStackTrace(); }});
        }

//        updateItems();

        binding.stExpressCurrNr.getEditText().addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                entry.getCbmText().setText(binding.stExpressCurrNr.getText());
            }
        });

//        cText.setOnClickListener(v-> {
//            onCTextClick();
//        });

        binding.btnBackspace.setOnClickListener(v -> {
            AutoDeleteHandler.backspaceAtCursor(binding.stExpressCurrNr.getEditText());
        });
        AutoDeleteHandler autoDelete = new AutoDeleteHandler(binding.stExpressCurrNr.getEditText(), 100);

        binding.btnBackspace.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                autoDelete.start();
                return false;
            }
        });
        binding.btnBackspace.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL : autoDelete.stop(); break;
                    }
                return false;
            }
        });

        binding.btnBODMAS.setOnClickListener(v->{
            v.setSelected(!v.isSelected());
            sets.saveBODMAS(v.isSelected());
            if(!binding.ctAnswer.getEditText().getText().toString().isEmpty()) {
                binding.ctAnswer.setText(getAnswer());
            }
            Log.d("Calc", "bodmas="+sets.getBODMAS()+"\nview="+v.isSelected());
        });
    }

    public void updateItems() {
        for (int i = 0; i < binding.slExpression.getChildCount(); i++) {
            CBMText ct = ((CBMText) binding.slExpression.getChildAt(i));
            if(Tools.isNumber(ct.getText())) {
                ct.setOnClickListener(v->onCTextClick(v));
            }
        }
    }

    private void onCTextClick(View view) {
        if(Tools.isNumber(entry.getCbmText().getText().toString())) {
            entry.setCbmText((DButton) view);
            Log.d("DButton", "Btn: "+entry.getCbmText().getText()+" is pressed.");
            binding.stExpressCurrNr.setText(entry.getCbmText().getText());
            entry.deselectAll(binding.slExpression);
            entry.getCbmText().setSelected(!entry.getCbmText().isSelected());
            entry.getCbmText().setFillColor(getColor(R.color.colorAccent));
        }
    }

    private String getAnswer() {
        String s = "";
        for(int xe=0;xe<binding.slExpression.getChildCount(); xe++){
            s += ((DButton)binding.slExpression.getChildAt(xe)).getText();
        }

        String ans = ExpressionCompiler.compile(s, sets.getBODMAS())+"";
//        Log.d("MainAct", "Answer="+ans);
        return ans;
    }

    private void clear() {
        binding.ctAnswer.setText("");
        binding.stExpressCurrNr.setText("");
        binding.slExpression.removeAllViews();
        entry.add(binding.slExpression, entry.makeEditr(""));

        sets.saveStandBy(true);
    }


}