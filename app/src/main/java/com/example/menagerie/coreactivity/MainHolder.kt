package com.example.menagerie.coreactivity

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.menagerie.R

class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @JvmField
    var namaPerusahaan: TextView

    @JvmField
    var emailPerusahaan: TextView

    @JvmField
    var alamatPerusahaan: TextView

    @JvmField
    var view: CardView

    init {
        namaPerusahaan = itemView.findViewById(R.id.nama_perusahaan);
        emailPerusahaan = itemView.findViewById(R.id.email_vendor);
        alamatPerusahaan = itemView.findViewById(R.id.alamat);
        view = itemView.findViewById(R.id.CardViewz);
    }

}
