package com.example.menagerie.coreactivity

class InfoVendor {
    var key: String? = null
    var namaperusahaan: String? = null
    var email: String? = null
    var alamat: String? = null

    constructor() {}

    constructor(namaperusahaan: String?, email: String?, alamat: String?) {
        this.namaperusahaan = namaperusahaan
        this.email = email
        this.alamat = alamat
    }



}