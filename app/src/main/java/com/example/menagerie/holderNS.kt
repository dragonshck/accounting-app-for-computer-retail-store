package com.example.menagerie

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class holderNS(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @JvmField
    var kodeAkun: TextView

    @JvmField
    var namaAkun: TextView

    @JvmField
    var nominalDebit: TextView

    @JvmField
    var nominalKredit: TextView

    @JvmField
    var view: CardView

    init {
        kodeAkun = itemView.findViewById(R.id.kode_akun1)
        namaAkun = itemView.findViewById(R.id.nama_akun1)
        nominalDebit = itemView.findViewById(R.id.nominalDebit1)
        nominalKredit = itemView.findViewById(R.id.nominalKredit1)
        view = itemView.findViewById(R.id.cardViewNS)
    }
}