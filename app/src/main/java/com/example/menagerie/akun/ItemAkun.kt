package com.example.menagerie.akun

class ItemAkun {
    var key: String? = null
    var kodeakun: String? = null
    var namaakun: String? = null
    var deskripsi: String? = null

    constructor() {}

    constructor(kodeAkun: String?, namaAkun: String?, deskAkun: String?) {
        kodeakun = kodeAkun
        namaakun = namaAkun
        deskripsi = deskAkun
    }
}