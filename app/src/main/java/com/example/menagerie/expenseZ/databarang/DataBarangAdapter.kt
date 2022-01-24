package com.example.menagerie.expenseZ.databarang

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.menagerie.R

class DataBarangAdapter(private val context: Context, private val dataBarang: ArrayList<ModelDataBarang>?) : RecyclerView.Adapter<HolderDataBarang>() {
    private val die4u : bmthListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderDataBarang {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return HolderDataBarang(view)
    }

    override fun onBindViewHolder(holder: HolderDataBarang, position: Int) {
        holder.namaBarang.text = dataBarang?.get(position)?.namaBarang
        holder.deskripsiBarang.text = dataBarang?.get(position)?.deskripsiBarang
        holder.tipeBarang.text = dataBarang?.get(position)?.tipeAkunBarang
        holder.viewz.setOnClickListener { die4u.onDataClick(dataBarang?.get(position), position) }
    }

    override fun getItemCount(): Int {
        return dataBarang?.size!!
    }

    interface bmthListener {
        fun onDataClick(dataBarang: ModelDataBarang?, position: Int)
    }

    init {
        die4u = context as bmthListener
    }



}