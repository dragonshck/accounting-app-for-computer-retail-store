package com.example.menagerie.jurnal

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.menagerie.R
import org.w3c.dom.Text

class holderJurnal(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @JvmField
    var tgltransaksi: TextView

    @JvmField
    var namaBarangDituku: TextView

    @JvmField
    var namaAkunDebt: TextView

    @JvmField
    var namaAkunCredt: TextView

    @JvmField
    var nominalAccount: TextView

    @JvmField
    var nominalAccount1: TextView

    @JvmField
    var view: CardView

    init {
        tgltransaksi = itemView.findViewById(R.id.tglTRX)
        namaBarangDituku = itemView.findViewById(R.id.jenengBarang)
        namaAkunDebt = itemView.findViewById(R.id.jenengDebit)
        namaAkunCredt = itemView.findViewById(R.id.jenengKredit)
        nominalAccount = itemView.findViewById(R.id.nominalKredit)
        nominalAccount1 = itemView.findViewById(R.id.nominalDebit)
        view = itemView.findViewById(R.id.cardViewJurnal)
    }
}