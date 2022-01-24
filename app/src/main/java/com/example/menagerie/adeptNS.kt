package com.example.menagerie

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class adeptNS(private val context: Context, private val daftarNS: ArrayList<dataNS>) : RecyclerView.Adapter<holderNS>() {
    private val mechNS : FDBXYZ
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holderNS {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_total, parent, false)
        return holderNS(view)
    }

    override fun onBindViewHolder(holder: holderNS, position: Int) {
        holder.kodeAkun.text = daftarNS?.get(position)?.namaakun
        holder.namaAkun.text = daftarNS?.get(position)?.kodeakun
        holder.nominalDebit.text = daftarNS?.get(position)?.hargaPembelianBarang
        holder.nominalKredit.text = daftarNS?.get(position)?.hargaPembelianBarang

    }

    override fun getItemCount(): Int {
        return daftarNS?.size!!
    }

    interface FDBXYZ {
        fun onDataClick(daftarNS: dataNS?, position: Int)
    }

    init {
        mechNS = context as FDBXYZ
    }

}