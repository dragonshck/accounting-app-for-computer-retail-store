package com.example.menagerie.expenseZ.paymentz

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.menagerie.R

class HolderDataTransaksiBarang(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @JvmField
    var namaBarangDibeli: TextView

    @JvmField
    var hargaPembelianBarang: TextView

    @JvmField
    var vendorBeliBarang: TextView

    @JvmField
    var tanggalBeliBarang: TextView

    @JvmField
    var quantityBarang: TextView

    @JvmField
    var viuw: CardView

    init {
        namaBarangDibeli = itemView.findViewById(R.id.namaBarang);
        hargaPembelianBarang = itemView.findViewById(R.id.hargaBeliBarang);
        vendorBeliBarang = itemView.findViewById(R.id.vendorBeliBarang);
        tanggalBeliBarang = itemView.findViewById(R.id.tanggalTransaksiBarang);
        quantityBarang = itemView.findViewById(R.id.QuantityBarang);
        viuw = itemView.findViewById(R.id.CardViewzTRXBarang);
    }
}