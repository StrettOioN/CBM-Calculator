package com.cbm.android.cbmcalculator.extended;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cbm.android.cbmcalculator.ASelf;
import com.cbm.android.cbmcalculator.utility.Ut;

import java.util.ArrayList;

import org.json.JSONObject;

public class CBMOptions {
    public static void funHistory(final Context c, final Object[] os) {
                try{
                    final ASelf aSelf = ASelf.get(c);
                    if(ASelf.getCalcHistory(c).getAll().size()>0) {
                        aSelf.setHCB(true);
                        if(aSelf.isStandBy()) {
                            if(((Activity)c).equals((MainActivity)c))((MainActivity)c).clearC();
                            aSelf.onSaved(c, -1, (View)os[0], (View)os[1]);
                        }else {
                            aSelf.onSave(c, ASelf.getCalcHistory(c).getAll().size()/*getHC()*/);
                            //Toast.makeText(c, "There sHe is", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(c, "No History", Toast.LENGTH_SHORT).show();
                    }

                } catch(Exception ex) {
                    Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }


    }
    
    public static void funMADS(final Context c, final Object[] os) {
        try {
                        final ASelf aSelf = ASelf.get(c);
            AlertDialog ad = new AlertDialog.Builder(c).create();
                        if(aSelf.arr.size()>2&&Ut.containsMads(aSelf.arr.toString())) {
                            AlertDialog.Builder adb = new AlertDialog.Builder(c);
                                //boolean[] blns = new boolean[aSelf.arrS.size()];
                                String[] cnms = new String[aSelf.arrS.size()];
                                
                                for(int x=0; x<aSelf.arrS.size(); x++) {
                                    
                                   //blns[x]=false;
                                    cnms[x]=aSelf.arrS.get(x).getString("CalculationName");
                                }
                                
                            adb.setItems(cnms, 
                        new OnClickListener() {
                            @Override
                                  public void onClick(DialogInterface d, int i) {
                                        try {
                                            aSelf.expAltInd=i;
                                    ((LinearLayout)os[0]).removeAllViews();
                                    //llNC.removeAllViews();
                                            //CurrentComponents.addCurrentTV("");
                            JSONObject jo = ((JSONObject)aSelf.arrS.get(aSelf.expAltInd));
                            aSelf.calcNm=jo.getString("CalculationName");
                                            
                                            aSelf.setStandBy(false);
                            aSelf.arr = (ArrayList<String>)jo.get("Calculation");//.toArray(new String[aSelf.arrS.size()]);
                            for(int s=0;s<aSelf.arr.size(); s++) {
                                ((TextView)((LinearLayout)os[1]).getChildAt(s)).setText(aSelf.arr.get(s));
                                        //entry(aSelf.arr.get(s));
                                            }
                            
                            ((Button)os[2]).callOnClick();
                            ((AlertDialog)os[4]).cancel();
                                    
                                        } catch(Exception ex){Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                    
                                ex.printStackTrace();}
                        }});
                            os[4] = adb.create();
                            ((AlertDialog)os[4]).show();
                        } else {
                            Toast.makeText(c, "Needs more than one number", Toast.LENGTH_SHORT).show();
                                
                        }
                            } catch(Exception ex) {
                                Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                    
                                ex.printStackTrace();}
                    
                                
                        
    }
    
    public static void funPie(final Context c, Object[] os) {
                    ASelf aSelf = ASelf.get(c);
            aSelf.editNowValue("", true);
                            //int ci=0;try{ci=Integer.parseInt(((TextView)os[0]).getTag().toString());}catch(Exception ex){ex.printStackTrace();}
                            //if(!aSelf.isStandBy()&&!ASelf.isNumber(ci-1>=0?aSelf.arr.get(ci-1):"a"))
            aSelf.entry(c, Math.PI+"");
    }
    
    public static void funSignChange(final Context c, Object[] os) {
        ASelf aSelf = ASelf.get(c);
        if(((TextView)os[0])==null)return;
                    String str=((TextView)os[0]).getText().toString();
                    try {
                        String oStr = "";
                        if(!aSelf.isStandBy()) {
                            if(ASelf.isNumber(str)) {
                                aSelf.cTxt = str.contains(".")?((Double.parseDouble(str)*-1)+""):((Integer.parseInt(str)*-1)+"");
                                if(aSelf.arr.size()>=Integer.parseInt(((TextView)os[0]).getTag().toString())) {
                                     aSelf.arr.set(Integer.parseInt(((TextView)os[0]).getTag().toString()), aSelf.cTxt);
                                }
                                oStr=aSelf.cTxt;
                                    
                            } else {
                                for(int x=0; x<ASelf.strSigns().size(); x++) {
                                    if(str.equals(ASelf.strSigns().get(x))) {
                                        oStr = ASelf.strSigns().get((x+1>=ASelf.strSigns().size())?0:x+1);
                                        break;
                                    }
                                }
                                
                                aSelf.arr.set(Integer.parseInt(((TextView)os[0]).getTag().toString()), oStr);
                                oStr = (aSelf.arr.get(Integer.parseInt(((TextView)os[0]).getTag().toString())));
                                    
                            }
                            ((TextView)os[0]).setText(oStr);
                        }
                        //Toast.makeText(c, oStr+" tvCurrent="+((TextView)os[0]).getText().toString(), Toast.LENGTH_SHORT).show();
                    
                    //Toast.makeText(getApplicationContext(), "((TextView)os[0])="+.getText().toString(), Toast.LENGTH_SHORT).show();
                    } catch(Exception ex) {
                        Toast.makeText(c, ex.getMessage(), Toast.LENGTH_LONG).show();
            
                    }
    }
    
}
