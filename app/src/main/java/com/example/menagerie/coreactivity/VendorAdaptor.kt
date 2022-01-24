package com.example.menagerie.coreactivity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.menagerie.R

class VendorAdaptor (private val context: Context, private val infoVendor: ArrayList<InfoVendor?>?) : RecyclerView.Adapter<MainHolder>() {
    private val listener: FirebaseDataListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vendor, parent, false)
        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.namaPerusahaan.text = "Nama Perusahaan : " + (infoVendor?.get(position)?.namaperusahaan)
        holder.emailPerusahaan.text = "Email Perusahaan : " + infoVendor?.get(position)?.email
        holder.alamatPerusahaan.text = "Alamat : " + infoVendor?.get(position)?.alamat
        holder.view.setOnClickListener{listener.onDataClick(infoVendor?.get(position), position)}

    }

    override fun getItemCount(): Int {
        return infoVendor?.size!!
    }

    interface  FirebaseDataListener {
        fun onDataClick(vendor: InfoVendor?, position: Int)
    }

    init {
        listener = context as FirebaseDataListener
    }
}