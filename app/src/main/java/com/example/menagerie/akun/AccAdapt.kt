package com.example.menagerie.akun

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.menagerie.R
import com.example.menagerie.coreactivity.VendorAdaptor

class AccAdapt (private val context: Context, private val daftarAkun: ArrayList<ItemAkun>?) : RecyclerView.Adapter<AkunHolder>(){
    private val listener: dbFireListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AkunHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_akun, parent, false)
        return AkunHolder(view)
    }

    override fun onBindViewHolder(holder: AkunHolder, position: Int) {
        holder.NamaAkun.text = (daftarAkun?.get(position)?.namaakun)
        holder.KodeAkun.text = daftarAkun?.get(position)?.kodeakun
        holder.DeskAkun.text = daftarAkun?.get(position)?.deskripsi
        holder.view.setOnClickListener{listener.onDataClick(daftarAkun?.get(position), position)}
    }

    override fun getItemCount(): Int {
        return daftarAkun?.size!!
    }

    interface dbFireListener {
        fun onDataClick(akun: ItemAkun?, position: Int)
    }

    init {
        listener = context as dbFireListener
    }
}