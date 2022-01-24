package com.example.menagerie.expenseZ.databarang

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.menagerie.R

class HolderDataBarang(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @JvmField
    var namaBarang: TextView

    @JvmField
    var deskripsiBarang: TextView

    @JvmField
    var tipeBarang: TextView

    @JvmField
    var viewz: CardView

    init {
        namaBarang = itemView.findViewById(R.id.nama_item)
        deskripsiBarang = itemView.findViewById(R.id.descItem)
        tipeBarang = itemView.findViewById(R.id.hargaItem)
        viewz = itemView.findViewById(R.id.CardViewItem)
    }
}