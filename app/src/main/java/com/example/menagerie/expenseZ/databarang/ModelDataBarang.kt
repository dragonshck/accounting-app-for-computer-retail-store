package com.example.menagerie.expenseZ.databarang

class ModelDataBarang {
    var key: String? = null
    var namaBarang: String? = null
    var tipeAkunBarang: String? = null
    var deskripsiBarang: String? = null

    constructor() {}
    constructor(
        namaBarang: String?,
        tipeAkunBarang: String?,
        deskripsiBarang: String?
    ) {
        this.namaBarang = namaBarang
        this.tipeAkunBarang = tipeAkunBarang
        this.deskripsiBarang = deskripsiBarang
    }


}