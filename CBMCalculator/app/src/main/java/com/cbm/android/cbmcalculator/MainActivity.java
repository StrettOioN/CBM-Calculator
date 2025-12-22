package com.cbm.android.cbmcalculator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.cbm.android.cbmcalculator.calcation.Entry;
import com.cbm.android.cbmcalculator.tool.AutoDeleteHandler;
import com.cbm.android.cbmcalculator.tool.Tools;
import com.cbm.android.cbmcalculator.calcation.ExpressionCompiler;
import com.cbm.android.cbmcalculator.databinding.ActivityMainBinding;
import com.cbm.android.cbmcalculator.settings.AppSettings;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import ui.cbmtext.CBMButton;
import ui.cbmtext.CBMText;
import ui.shapelayout.FlowLayout;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppSettings sets;
//    private CBMButton cText;
    private Entry entry;
    private AutoDeleteHandler autoDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sets = new AppSettings(this);
        entry = new Entry(this, (v)-> {entry.onCTextClick(v, binding.slExpression, binding.stExpressCurrNr);
        autoDelete.cbmtIndex=entry.getEntryIndex();}, binding.slExpression, binding.stExpressCurrNr);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
//        entry.onClick = (v)->onCTextClick(((CBMText)v) );
        binding.btnBODMAS.setSelected(sets.getBODMAS());

//        if(cText==null) {
//            cText = new CBMButton(this);
//        }
        entry.makeEditr("");

        for(int x=0; x<binding.slPad.getChildCount(); x++) {
            View vA = binding.slPad.getChildAt(x);
            vA.setOnClickListener(v-> {
            try {
                CBMButton sb = null;
                if (v instanceof CBMButton) sb = (CBMButton) v;
                String text = sb.getText().toString();
                if (binding != null) {
                    String currNr = binding.stExpressCurrNr.getText();
//                    switch(text) {
//                        check what type of token
//                    }
                    if(text.equalsIgnoreCase("=")) {
                        currNr="";
                        String ans = getAnswer();
                        binding.ctAnswer.setText(ans);
                        sets.saveStandBy(true);
                        return;
                    } else if(text.equalsIgnoreCase("C")) {
                        clear();
                        return;
                    } else if(text.equalsIgnoreCase("-+")) {
                        binding.stExpressCurrNr.setText(invert(new BigDecimal(currNr)).toString());
                        return;
                    }
                    try {
                        if(sets.getStandBy())
                        {clear();sets.saveStandBy(false);}
                        if(currNr!=null&&!currNr.isEmpty()&&Tools.isNumber(text)&&new BigDecimal(currNr).doubleValue()==0) {
                            binding.stExpressCurrNr.setText("");
                        }
                    } catch(Exception ex){ex.printStackTrace();}
                    int expLength = binding.slExpression.getChildCount();
                    binding.slExpression.invalidate();
                    if(Tools.isNumber(text)||text.equals(".")/*&&cText!=null*/) {
                        if(binding.slExpression.getChildCount()<1) {
                            entry.add(binding.slExpression, entry.makeEditr(""));
                        }
                        if(text.equals(".")&&currNr.contains(".")) {return;}
                        EditText editText = binding.stExpressCurrNr.getEditText();
                        int start = editText.getSelectionStart();
                        int end = editText.getSelectionEnd();
//                        editText.getEditableText().delete(start, end);
                        editText.getText().replace(Math.min(start, end), Math.max(start, end), text);
                        editText.setSelection(start + text.length());
                        currNr = editText.getText().toString();
                        entry.getCbmButton().setText(currNr);
                        try{entry.getCbmButton().setText(currNr);}
                        catch(Exception ex){ex.printStackTrace();}
                    } else {
                        if(expLength<2) {
                            String beforeText = "0";
                            switch (text) {
                                case "×": case "*": case "÷":
                                case "/": beforeText="1"; break;
                            }
                            try {
                                if (expLength < 1) {
                                    entry.add(binding.slExpression, entry.makeEditr(beforeText), entry.getEntryIndex()+1);
                                } else {
                                    if (entry.getCbmButton().getText().toString().isEmpty())
                                    {entry.getCbmButton().setText(beforeText);}
                                }
                            } catch(Exception ex) {ex.printStackTrace();}
                        }
                        entry.add(binding.slExpression, entry.makeEditr(text), entry.getEntryIndex()+1);

                        String afterText = "0";
                        switch (text) {
                            case "×": case "*": case "÷":
                            case "/": afterText="1"; break;
                        }
                        entry.add(binding.slExpression, entry.makeEditr(afterText), entry.getEntryIndex()+1);
                        binding.stExpressCurrNr.setText(afterText);
                        binding.stExpressCurrNr.getEditText().selectAll();
                    }
                    binding.slExpression.invalidate();
//                    binding.stExpressCurrNr.append(text);
                }
            } catch(Exception ex){ex.printStackTrace(); }});
        }

        try {
            binding.stExpressCurrNr.getEditText().addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {}
                @Override public void onTextChanged(CharSequence cs, int i, int i1, int i2) {}
                @Override
                public void afterTextChanged(Editable e) {
                    String text = e.toString();
                    if(text.contains("-")&&text.indexOf("-")>0) {
                        try {
                            String ftext = text.substring(0, text.indexOf("-"));
                            text = "-" + ftext + text.substring(text.indexOf("-") + 1);
                            binding.stExpressCurrNr.setText(text);
                        } catch(Exception ex){ex.printStackTrace();}
                    }
                    entry.getCbmButton().setText(text);
                    entry.getExpression().set(entry.getEntryIndex(), text);
                    try {
                        if (autoDelete != null) {
                            autoDelete.cbmtIndex = entry.getEntryIndex();}
                    } catch(Exception ex) {ex.printStackTrace();}
                }
            });
        } catch(Exception ex) {ex.printStackTrace();}

//        try {
//            cText.setOnClickListener(v-> {
//                onCTextClick(v);
//            });
//        } catch(Exception ex) {ex.printStackTrace();}
        autoDelete = new AutoDeleteHandler(binding.slExpression, entry, 100);

        binding.btnBackspace.setOnClickListener(v -> {
            autoDelete.backspaceAtCursor(/*entry*/);
            entry.reIndex(binding.slExpression);
        });

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
                    case MotionEvent.ACTION_CANCEL : {autoDelete.stop();
                        entry.reIndex(binding.slExpression);} break;
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

//        clear();
    }

    public void updateItems() {
        for (int i = 0; i < binding.slExpression.getChildCount(); i++) {
            CBMText ct = ((CBMText) binding.slExpression.getChildAt(i));
            if(Tools.isNumber(ct.getText())) {
//                ct.setOnClickListener(v->onCTextClick(v));
            }
        }
    }

    private String getAnswer() {
        String s = "";
        for(int xe=0;xe<binding.slExpression.getChildCount(); xe++){
            s += ((CBMButton)binding.slExpression.getChildAt(xe)).getText();
        }

        String ans = ExpressionCompiler.compile(entry, sets.getBODMAS())+"";
        String regex = "\\d+\\.(0)+$";
        if(Pattern.compile(regex).matcher(ans).matches()) {
            ans = new BigDecimal(ans).toBigInteger()+"";
        }
        return ans;
    }

    private BigDecimal invert(BigDecimal value) {
        return value.multiply(new BigDecimal(-1));
    }

    private void clear() {
        binding.ctAnswer.setText("");
        binding.stExpressCurrNr.setText("");
        binding.slExpression.removeAllViews();
        entry.getExpression().clear();
        entry.add(binding.slExpression, entry.makeEditr(""));
        binding.slExpression.invalidate();
        entry.setEntryIndex(0);
        autoDelete.cbmtIndex=0;

        sets.saveStandBy(true);
    }

}