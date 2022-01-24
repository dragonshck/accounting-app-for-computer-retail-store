package com.example.menagerie.expenseZ.expandtrx

class DataTransaksi {
    var key: String? = null
    var tanggalBeliBarang: String? = null
    var vendorTransaksi: String? = null
    var pilihAkunDebit: String? = null
    var pilihAkunKredit: String? = null
    var namaBarangDibeli: String? = null
    var hargaPembelianBarang: String? = null

    constructor() {}
    constructor(
        tanggalBeliBarang: String?,
        vendorTransaksi: String?,
        pilihAkunDebit: String?,
        pilihAkunKredit: String?,
        namaBarangDibeli: String?,
        hargaPembelianBarang: String?
    ) {
        this.tanggalBeliBarang = tanggalBeliBarang
        this.vendorTransaksi = vendorTransaksi
        this.pilihAkunDebit = pilihAkunDebit
        this.pilihAkunKredit = pilihAkunKredit
        this.namaBarangDibeli = namaBarangDibeli
        this.hargaPembelianBarang = hargaPembelianBarang
    }


}