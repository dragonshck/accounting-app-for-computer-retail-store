package com.example.menagerie.expenseZ.paymentz

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.menagerie.R

class AdapterTransaksiBarang (private val context: Context, private val datatransaksiBarang: ArrayList<datatransaksibarang>?) : RecyclerView.Adapter<HolderDataTransaksiBarang>() {
    private val thrillerbark : firebaseListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderDataTransaksiBarang {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaksibarang, parent, false)
        return HolderDataTransaksiBarang(view)
    }

    override fun onBindViewHolder(holder: HolderDataTransaksiBarang, position: Int) {
        holder.namaBarangDibeli.text = (datatransaksiBarang?.get(position)?.namaBarangDibeli)
        holder.tanggalBeliBarang.text = datatransaksiBarang?.get(position)?.tanggalBeliBarang
        holder.hargaPembelianBarang.text = datatransaksiBarang?.get(position)?.hargaPembelianBarang
        holder.vendorBeliBarang.text = datatransaksiBarang?.get(position)?.vendorPembelian
        holder.quantityBarang.text = datatransaksiBarang?.get(position)?.jumlahBarangDibeli
        holder.viuw.setOnClickListener { thrillerbark.onDataClick(datatransaksiBarang?.get(position), position) }
    }

    override fun getItemCount(): Int {
        return datatransaksiBarang?.size!!
    }

    interface firebaseListener {
        fun onDataClick(datatransaksiBarang: datatransaksibarang?, position: Int)
    }

    init {
        thrillerbark = context as firebaseListener
    }
}