package com.cbm.android.cbmcalculatorandroid.data.model

import android.content.Context
import android.content.SharedPreferences

class HomeDatabase {
    lateinit var sp:SharedPreferences

    companion object {
    }

    constructor(c: Context) {
        sp = c.getSharedPreferences("CBMCalcStore", Context.MODE_PRIVATE);
    }
    fun saveExpression(expression:String, answer:String) {
        sp.edit().putString(expression, answer).apply();
    }

    fun getSavedAnswer(expression: String):String? {
        return sp.getString(expression, "")
    }

    fun getKeys(): ArrayList<String> {
        val map:Map<String, *> = sp.all
        val arr = ArrayList<String>()
        for (entry in map.keys) {
            arr.add(entry)
        }

        return arr
    }
}