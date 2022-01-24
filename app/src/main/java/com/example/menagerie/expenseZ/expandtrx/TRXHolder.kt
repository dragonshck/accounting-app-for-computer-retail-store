package com.example.menagerie.expenseZ.expandtrx

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.menagerie.R

class TRXHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    @JvmField
    var namaTRX: TextView

    @JvmField
    var tglTRX: TextView

    @JvmField
    var nominalTRX: TextView

    @JvmField
    var view: CardView

    init {
        namaTRX = itemView.findViewById(R.id.namaTransaksi1);
        tglTRX= itemView.findViewById(R.id.tanggalTransaksi1);
        nominalTRX = itemView.findViewById(R.id.hargaBarang1);
        view = itemView.findViewById(R.id.CardViewzTRX);
    }
}