package com.example.menagerie.jurnal

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.menagerie.R

class adeptJurnal(private val context: Context, private val daftarJurnal: ArrayList<dataJurnal?>?) :
    RecyclerView.Adapter<holderJurnal>() {
    private val mechJurnal: FDBListerner
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holderJurnal {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_jurnalumum, parent, false)
        return holderJurnal(view)
    }

    override fun onBindViewHolder(holder: holderJurnal, position: Int) {
        holder.tgltransaksi.text = daftarJurnal?.get(position)?.tanggalBeliBarang
        holder.namaBarangDituku.text = daftarJurnal?.get(position)?.namaBarangDibeli
        holder.namaAkunDebt.text = daftarJurnal?.get(position)?.pilihAkunDebit + " (D)"
        holder.namaAkunCredt.text = daftarJurnal?.get(position)?.pilihAkunKredit + " (C)"
        holder.nominalAccount.text = daftarJurnal?.get(position)?.hargaPembelianBarang
        holder.nominalAccount1.text = daftarJurnal?.get(position)?.hargaPembelianBarang
        holder.view.setOnClickListener { mechJurnal.onDataClick(daftarJurnal?.get(position), position) }
    }

    override fun getItemCount(): Int {
        return daftarJurnal?.size!!
    }

    // Interface Data Listener

    interface FDBListerner {
        fun onDataClick(daftarJurnal: dataJurnal?, position: Int)
    }

    init {
        mechJurnal = context as FDBListerner
    }
}