package com.example.menagerie.akun

import android.view.View
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.menagerie.R

class AkunHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    @JvmField
    var NamaAkun: TextView

    @JvmField
    var KodeAkun: TextView

    @JvmField
    var DeskAkun: TextView

    @JvmField
    var view: CardView

    init {
        NamaAkun = itemView.findViewById(R.id.nama_akun);
        KodeAkun = itemView.findViewById(R.id.kode_akun);
        DeskAkun = itemView.findViewById(R.id.deskripsiakun);
        view = itemView.findViewById(R.id.CardViewx);
    }

}
