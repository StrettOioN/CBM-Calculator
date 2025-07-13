package com.cbm.android.cbmcalculatorandroid

import java.math.BigDecimal

class Tools {
    companion object {
        public fun isNumber(value:String): Boolean {
            try {
                val db = BigDecimal(value);
                return !db.toDouble().toString().isEmpty()
            } catch(ex:Exception){return false}
        }

        public fun expression():ArrayList<String> {
            val arr = ArrayList<String>()
            arr.add("+")
            arr.add("-")
            arr.add("")
            arr.add("")
            return arr
        }
     }
}