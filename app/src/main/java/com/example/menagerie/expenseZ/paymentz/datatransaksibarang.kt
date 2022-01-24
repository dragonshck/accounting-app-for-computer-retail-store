package com.example.menagerie.expenseZ.paymentz

class datatransaksibarang {
    var key: String? = null
    var tanggalBeliBarang: String? = null
    var namaBarangDibeli: String? = null
    var vendorPembelian: String? = null
    var pilihAkunDebit: String? = null
    var pilihAkunKredit: String? = null
    var jumlahBarangDibeli: String? = null
    var hargaPembelianBarang: String? = null

    constructor(){}

    constructor(
        tanggalBeliBarang: String?,
        namaBarangDibeli: String?,
        vendorPembelian: String?,
        pilihAkunDebit: String?,
        pilihAkunKredit: String?,
        jumlahBarangDibeli: String?,
        hargaPembelianBarang: String?
    ) {
        this.tanggalBeliBarang = tanggalBeliBarang
        this.namaBarangDibeli = namaBarangDibeli
        this.vendorPembelian = vendorPembelian
        this.pilihAkunDebit = pilihAkunDebit
        this.pilihAkunKredit = pilihAkunKredit
        this.jumlahBarangDibeli = jumlahBarangDibeli
        this.hargaPembelianBarang = hargaPembelianBarang
    }


}

