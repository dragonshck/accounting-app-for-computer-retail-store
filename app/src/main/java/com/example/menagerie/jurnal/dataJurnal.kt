package com.example.menagerie.jurnal

class dataJurnal {
    var key: String? = null
    var tanggalBeliBarang: String? = null
    var namaBarangDibeli: String? = null
    var pilihAkunDebit: String? = null
    var pilihAkunKredit: String? = null
    var hargaPembelianBarang: String? = null

    constructor(){}
    constructor(
        key: String?,
        tgltransaksi: String?,
        namaBarangDibeli: String?,
        namaAkunDebit: String?,
        namaAkunKredit: String?,
        nominalAkun: String?
    ) {
        this.key = key
        this.tanggalBeliBarang = tgltransaksi
        this.namaBarangDibeli = namaBarangDibeli
        this.pilihAkunDebit = namaAkunDebit
        this.pilihAkunKredit = namaAkunKredit
        this.hargaPembelianBarang = nominalAkun
    }


}