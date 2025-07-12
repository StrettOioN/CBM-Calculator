package com.cbm.android.cbmcalculator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cbm.android.cbmcalculator.extended.*;
import com.cbm.android.cbmcalculator.utility.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class ASelf {
    private static ASelf aSelf;
    //private static Context mContext;
    public String cTxt="", calcNm="#1 Calculation"; //Current Light And Sound Description
    public static String setting="#FF000000", stitle="ColorBackground";
    public static class Constants {
        public static String DEFAULT_JSONCOLOR_BLKTXT = "{'A':'1.0', 'R':'0.0', 'B':'0.0', 'G':'0.0'}";
        public static String DEFAULT_JSONCOLOR_WHTTXT = "{'A':'1.0', 'R':'1.0', 'B':'1.0', 'G':'1.0'}";
        public static String DEFAULT_JSONCOLOR_ID="ID";
        public static String DEFAULT_JSONCOLOR_BGCOLOR="BGCOLOR";
        public static String DEFAULT_JSONCOLOR_BGTYPE="BGTYPE";
        public static String DEFAULT_JSONCOLOR_TXTCOLOR="TXTCOLOR";
        public static String SOLID="SOLID";
        public static String BORDER="BORDER";
    }
    
    public boolean isDotd = false, standBy = false, hcb=false;
    private SharedPreferences sp;
    private SharedPreferences.Editor sped;
    
    public ArrayList<String> arr = new ArrayList<>();
    public ArrayList<JSONObject> arrS = new ArrayList<>();
    public String mDoneCalculation, carriedAnswer="";
    public int tvCInd=0, historyCount=0, sbEmpty = 0, expCount=0, expAltInd=0;
    public static boolean decimalAlways, bOpts, phOn, carryAnswer;
    public static DecimalFormat mDF;
    public KeypadModeOpt mKeypadMode = KeypadModeOpt.Numbers;
    
    public static String[] clrStates = new String[] {
        "ColorBackground",
        "ColorAnswer",
        "ColorCalculation_Details",
        "ColorNumbers",
        "ColorSymbols",
        "ColorDefault_Theme"
    };
    
    public static String[] settings;
    
    public ArrayList<View> getSettingViews(String setting) {
        ArrayList<View> arrV = new ArrayList<>();
        if(setting.equals(settings[0])) {
            
        }
        
        return arrV;
    }
    
    public enum KeypadModeOpt {
        Numbers,
        Selection
    };
    
    public static ArrayList<String> strSigns() {
        ArrayList<String> arrSigns = new ArrayList<>();
        //arrSigns.add(Arrays.asList(new String[]{"+", "-", "*", "/"});
        arrSigns.add("+");
        arrSigns.add("-");
        arrSigns.add("×");
        arrSigns.add("÷");
        arrSigns.add("e");
        arrSigns.add("√");
        //arrSigns.add("cos");
        //arrSigns.add("sin");
        //arrSigns.add("tan");
        
        return arrSigns;
    }
    
    public enum Calculation {
        Ix,
        LmtIx,
        Exp,
        ExpAns,
        DoneCalc,
        dType
    };
    
    private ASelf() {
        new ASelf(null);
    }
    
    private ASelf(Context c) {
        //mContext=c;
        isDotd = false;
        cTxt="";
        mDF = new DecimalFormat("#.#");
        //sp = getCalcSet(c);
        //sped = sp.edit();
    }
    
    /*public static ASelf get() {
        if(aSelf==null) {
            aSelf=new ASelf(null);
        }
        
        return aSelf;
    }*/
    
    public static ASelf get(Context c) {
        if(aSelf==null) {
            aSelf=new ASelf(c);
        }

        return aSelf;
    }
    
    public static SharedPreferences getCalcHistory(Context c) {
        return c.getSharedPreferences("CalcPrefsFile", Context.MODE_PRIVATE);
    }
    
    public static SharedPreferences getCalcSet(Context c) {
        return c.getSharedPreferences("CBMCalcSettings", Context.MODE_PRIVATE);
    }
  
    public static void refresh(Context c) {
        try {
        SharedPreferences sp = getCalcSet(c);
        SharedPreferences.Editor ed = sp.edit();
        for(String s: settings) {
            int a = s.substring(0, s.indexOf("_")).equals("Color")?1:0;
            if(!sp.contains(s)){
                if(a==1){ed.putString(s,DefaultThemeSet.defaultBGFormats().get(0).getString(ASelf.Constants.DEFAULT_JSONCOLOR_BGCOLOR));
                }else{ed.putString(s,"Off");}
            }else	if(sp.contains(s)&&(sp.getInt(s,-1)<=0)){
                ed.putString(s,DefaultThemeSet.defaultBGFormats().get(0).toString());
            }
        }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void setProperty(Context c, String s, String p) {
        SharedPreferences.Editor ed = getCalcSet(c).edit();
        ed.putString(s,p);
        ed.apply();
    }
    
    public String getProperty(Context c, String s) {
        return getCalcSet(c).getString(s,s);
    }
    
    public void addListItem(Object ol, Object o) {
        try {
            if(ol instanceof List) {
                ((List)ol).add(o);
            } else if(o instanceof JSONObject) {
                JSONObject jo = ((JSONObject)ol);
                ((List)ol).add(o);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void allTypeCalculations() {
        try {
        aSelf.arrS.add(new JSONObject().put("CalculationName","BODMAS")
            .put("Calculation",Ut.toCalculationType(arr, new String[]{"÷","×","+","-"})));
        aSelf.arrS.add(new JSONObject().put("CalculationName","BOMDAS")
            .put("Calculation",Ut.toCalculationType(arr, new String[]{"×","÷","+","-"})));
        aSelf.arrS.add(new JSONObject().put("CalculationName","BODMSA")
            .put("Calculation",Ut.toCalculationType(arr, new String[]{"÷","×","-","+"})));
        aSelf.arrS.add(new JSONObject().put("CalculationName","BOMDSA")
            .put("Calculation",Ut.toCalculationType(arr, new String[]{"×","÷","-","+"})));
        aSelf.arrS.add(new JSONObject().put("CalculationName","BOASMD")
            .put("Calculation",Ut.toCalculationType(arr, new String[]{"+","-","×","÷"})));
        aSelf.arrS.add(new JSONObject().put("CalculationName","BOASDM")
            .put("Calculation",Ut.toCalculationType(arr, new String[]{"+","-","÷","×"})));
        aSelf.arrS.add(new JSONObject().put("CalculationName","BOSAMD")
            .put("Calculation",Ut.toCalculationType(arr, new String[]{"-","+","×","÷"})));
        aSelf.arrS.add(new JSONObject().put("CalculationName","BOSADM")
            .put("Calculation",Ut.toCalculationType(arr, new String[]{"-","+","÷","×"})));
        aSelf.arrS.add(new JSONObject().put("CalculationName","BOMADS")
            .put("Calculation",Ut.toCalculationType(arr, new String[]{"×","+","÷","-"})));
        aSelf.arrS.add(new JSONObject().put("CalculationName","BOAMDS")
            .put("Calculation",Ut.toCalculationType(arr, new String[]{"+","×","÷","-"})));
        aSelf.arrS.add(new JSONObject().put("CalculationName","BOMASD")
            .put("Calculation",Ut.toCalculationType(arr, new String[]{"×","+","-","÷"})));
        aSelf.arrS.add(new JSONObject().put("CalculationName","BOAMSD")
            .put("Calculation",Ut.toCalculationType(arr, new String[]{"+","×","-","+"})));
        aSelf.arrS.add(new JSONObject().put("CalculationName","BODSMA")
            .put("Calculation",Ut.toCalculationType(arr, new String[]{"÷","-","×","+"})));
        aSelf.arrS.add(new JSONObject().put("CalculationName","BODSAM")
            .put("Calculation",Ut.toCalculationType(arr, new String[]{"÷","-","+","×"})));
        aSelf.arrS.add(new JSONObject().put("CalculationName","BOSDMA")
            .put("Calculation",Ut.toCalculationType(arr, new String[]{"-","÷","×","+"})));
        aSelf.arrS.add(new JSONObject().put("CalculationName","BOSDAM")
            .put("Calculation",Ut.toCalculationType(arr, new String[]{"-","÷","+","×"})));
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void clearEmpties(Context c) {
        SharedPreferences.Editor ed = ASelf.getCalcSet(c).edit();
            for(int b = 0; b<ASelf.getCalcSet(c).getAll().size(); b++) {
            JSONObject jo=null;
            try{
                String hl = ASelf.getCalcSet(c).getString("jsonCalc"+b, "");
                jo= new JSONObject(hl);
                //Toast.makeText(c,jo.toString(), Toast.LENGTH_SHORT).show();
                if(!hl.isEmpty()||jo==null||jo.names().length()<0) {
                    ed.remove("jsonCalc"+b);
                    ed.apply();
                }
            }
            catch(Exception ex){ex.printStackTrace();ed.remove("jsonCalc"+b);
            }
        }
    }
    
    public static boolean setCBM(Context c, String s, Object o) {
        SharedPreferences.Editor ed = getCalcSet(c).edit();
        boolean bln = true;
        if(o instanceof Boolean) {
            ed.putString(s, Boolean.valueOf(o.toString())?"On":"Off");
        } else if(o instanceof Integer) {
            ed.putInt(s, Integer.parseInt(o.toString()));
        } else if(o instanceof String) {
            ed.putString(s, o.toString());
        } else {
            bln=false;
        }
        
        ed.apply();
        
        return bln;
    }
    
    public static void setDecimalAlways(Context c, boolean b) {
        decimalAlways=b;
        //setCBM(mContext.getResources().getString(R.string.decimal_always_switch,"Switch_Decimal_Always"), "On");
    }
    
    public static boolean getDecimalAlways(Context c) {
        return getCalcSet(c).getString("Switch_Decimal_Always", "Switch_Decimal_Always").equals("On");
    }
    
    public String editNowValue(String value, boolean clear) {
        if(clear) {
            cTxt = ""; isDotd=false;
        }
        
        return editNowValue(value);
    }
    
    public String editNowValue(String value) {
        String str1 = "";
        if(cTxt.isEmpty()) {
            if(value.equals(".")) {
                value = "0.";
            } else {
                cTxt += value;
            }
        } else {
            if(cTxt.equals("0")) {
                cTxt = value.equals(".")?cTxt+value:value;
            } else {
                cTxt += value;
            }
        }
        
        if(!cTxt.isEmpty()&&cTxt.contains(".")) {
            //cTxt = Double.parseDouble(cTxt)+"";
            isDotd = true;
        } else {
            //ASelf.get().cTxt = cTxt;
            isDotd = false;
        }
        
        return cTxt;
    }
    
    public static double calculate(String s, double ans, double ans2) {
        switch(s) {
            case "+":
                ans = ans + ans2;
                break;
            case "-":
                ans = ans - ans2;
                break;
            case "×":
                ans = ans * ans2;
                break;
            case "÷":
                ans = ans / ans2;
                break;
            case "%":
                ans = ans * 0.01;
                break;
            case "√":
                ans = Math.pow(ans2, Double.parseDouble((1.0 / ans) + ""));
                ans = (ans + "").contains(".9999999999") ? Math.round(ans) : ans;
                break;
            case "e":
                ans = Math.pow(ans, ans2);
                break;
            default:
                ans = ans + ans2;
                break;
        }
                         
        return ans;
    }
    
    public static String[] calculation(List<String> l) {
        String answer[] = new String[]{"","0"};
        double ans = Double.parseDouble(l.get(0));
        String currentSign = "!+";
        for(int x = 0; x<l.size(); x++) {
            answer[0] += l.get(x);
            if(!ASelf.isNumber(l.get(x))) {
                currentSign = l.get(x);
                //ans = ans + Integer.parseInt(aSelf.arr.get(x+1));
            } else {
                double anso = 0;
                if(x>1)anso = Double.parseDouble(l.get(x));
                ans = ASelf.calculate(currentSign, ans, anso);
            }
        }
        answer[1] = String.valueOf(ans);
        answer[1] = answer[1].endsWith(".0")?String.valueOf(Math.round(ans)):answer[1];

        return answer;
    }
    
    public static boolean isNumber(String s) {
        try {Double.parseDouble(s); return true;}
        catch(Exception ex){return false;}
    }
    
    public void setStandBy(boolean b) {
        standBy=b;
        phOn=b;
        //((Button)findViewById(R.id.btnMPercentage)).setEnabled(false);
    }
    
    public boolean isStandBy() {
        return standBy;
    }
    
    public void setHCB(boolean z) {
        hcb=z;
        
    }
    
    public boolean getHCB() {
        return hcb;
    }
    
    public int getHC(Context c) {
        return getCalcHistory(c).getAll().size();
    }
    
}
