package com.example.menagerie

class dataNS {
    var key: String? = null
    var namaakun: String? = null
    var kodeakun: String? = null
    var hargaPembelianBarang: String? = null

    constructor(){}
    constructor(namaakun: String?, kodeakun: String?, hargaPembelianBarang: String?) {
        this.namaakun = namaakun
        this.kodeakun = kodeakun
        this.hargaPembelianBarang = hargaPembelianBarang
    }


}