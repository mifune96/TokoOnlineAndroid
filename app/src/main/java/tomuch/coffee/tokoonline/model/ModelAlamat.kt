package tomuch.coffee.tokoonline.model

class ModelAlamat {
    val id = 0
    val nama = ""

    val status = Status()
    val results = ArrayList<Provinsi>()

    class Status{
        val code = 0
        val description = ""
    }

    class Provinsi {
        val province_id = "0"
        val province = ""
        val city_id = "0"
        val city_name = ""
        val postal_code = ""
        val type = ""
    }

}