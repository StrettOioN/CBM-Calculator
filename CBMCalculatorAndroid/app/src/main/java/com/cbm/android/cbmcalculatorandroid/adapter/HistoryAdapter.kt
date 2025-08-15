package com.cbm.android.cbmcalculatorandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cbm.android.cbmcalculatorandroid.data.model.HomeDatabase
import java.util.Arrays
import java.util.Collections

class HistoryAdapter:RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    lateinit var hdb:HomeDatabase
    lateinit var onClick:OnClickListener
    constructor(c:Context) {
        hdb = HomeDatabase(c)
    }

    constructor(c:Context, onClick:OnClickListener) {
        hdb = HomeDatabase(c)
        this.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        return HistoryAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, null))
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        if(hdb.getKeys().size>0) {
            val expression = hdb.getKeys().get(position)//(db.getKeys().size-1)-position)
            holder.itemView.findViewById<TextView>(android.R.id.text1).text =
                expression + "\n = " + hdb.sp.getString(expression, "")+"\n"

            holder.itemView.setOnClickListener {
                if(onClick!=null)
                {onClick.onClick(expression, hdb.sp.getString(expression, "")!!)}
            }
        }
    }

    class ViewHolder:RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)
    }

    override fun getItemCount(): Int {
        return hdb.getKeys().size
    }

    fun setOnClickListener(onClick: OnClickListener)
    {this.onClick = onClick}

    public fun interface OnClickListener {
        fun onClick(expression:String, answer:String)
    }
}