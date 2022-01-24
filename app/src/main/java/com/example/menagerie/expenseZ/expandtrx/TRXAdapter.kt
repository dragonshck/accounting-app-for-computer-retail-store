package com.example.menagerie.expenseZ.expandtrx

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.menagerie.R
import java.text.FieldPosition

class TRXAdapter (private val context: Context, private val dataTransaksi: ArrayList<DataTransaksi>?) : RecyclerView.Adapter<TRXHolder>() {
    private val listen: dbListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TRXHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaksi, parent, false)
        return TRXHolder(view)
    }

    override fun onBindViewHolder(holder: TRXHolder, position: Int) {
        holder.namaTRX.text = (dataTransaksi?.get(position)?.namaBarangDibeli)
        holder.tglTRX.text= dataTransaksi?.get(position)?.tanggalBeliBarang
        holder.nominalTRX.text = dataTransaksi?.get(position)?.hargaPembelianBarang
        holder.view.setOnClickListener { listen.onDataClick(dataTransaksi?.get(position), position) }
    }

    override fun getItemCount(): Int {
        return dataTransaksi?.size!!
    }

    interface  dbListener {
        fun onDataClick(dataTransaksi: DataTransaksi?, position: Int)
    }

    init {
        listen = context as dbListener
    }
}