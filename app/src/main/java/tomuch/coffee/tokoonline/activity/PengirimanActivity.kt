package tomuch.coffee.tokoonline.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_pengiriman.*
import kotlinx.android.synthetic.main.activity_pengiriman.div_kosong
import kotlinx.android.synthetic.main.activity_tambah_alamat.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.adapter.AdapterKurir
import tomuch.coffee.tokoonline.app.ApiConfigAlamat
import tomuch.coffee.tokoonline.helper.Helper
import tomuch.coffee.tokoonline.model.Alamat
import tomuch.coffee.tokoonline.model.rajaongkir.Costs
import tomuch.coffee.tokoonline.model.rajaongkir.ResponOngkir
import tomuch.coffee.tokoonline.room.MyDatabase
import tomuch.coffee.tokoonline.util.ApiKey

class PengirimanActivity : AppCompatActivity() {

    lateinit var myDb: MyDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengiriman)
        Helper().setToolbar(this, toolbar, "Pengiriman")
        myDb = MyDatabase.getInstance(this)!!

        mainButton()
        setSpinner()
    }

    private fun setSpinner() {
        val arrString = ArrayList<String>()
        arrString.add("JNE")
        arrString.add("POS")
        arrString.add("TIKI")

        val adapter = ArrayAdapter<Any>(
            this,
            R.layout.item_spinner,
            arrString.toTypedArray()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_kurir.adapter = adapter

        spn_kurir.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                       getOngkir(spn_kurir.selectedItem.toString())
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
    }

    fun checkAlamat() {

        if (myDb.daoAlamat().getByStatus(true) != null) {
            div_alamat.visibility = View.VISIBLE
            div_kosong.visibility = View.GONE
            div_metodePengiriman.visibility = View.VISIBLE

            val alamat = myDb.daoAlamat().getByStatus(true)!!
            tv_nama.text = alamat.name
            tv_phone.text = alamat.phone
            tv_alamat.text =
                alamat.alamat + ", " + alamat.kota + ", " + alamat.kecamatan + ", " + alamat.kodepos + ", (" + alamat.type + ")"
            btn_tambahAlamat.text = "Ubah Alamat"

            getOngkir("JNE")
        } else {
            div_alamat.visibility = View.GONE
            div_kosong.visibility = View.VISIBLE
            btn_tambahAlamat.text = "Tambah Alamat"
        }
    }

    private fun mainButton() {
        btn_tambahAlamat.setOnClickListener {
            startActivity(Intent(this, ListAlamatActivity::class.java))
        }
    }

    private fun getOngkir(kurir: String) {
        val alamat = myDb.daoAlamat().getByStatus(true)
        val origin = "501"
        val destination = alamat!!.id_Kota.toString()
        val berat = 1000

        ApiConfigAlamat.instanceRetrofit.onkir(ApiKey.key, origin, destination, berat, kurir.toLowerCase())
            .enqueue(object : Callback<ResponOngkir> {
                override fun onResponse(call: Call<ResponOngkir>, response: Response<ResponOngkir>) {
                    if (response.isSuccessful) {
                        Log.d("Sukses", "berhasil memuat data")
                        val result = response.body()!!.rajaongkir.results
                        if (result.isNotEmpty()){
                            displayOngkir(result[0].code.toUpperCase(), result[0].costs)
                        }

                    } else {
                        Log.d("Error", "gagal memuat data" + response.message())
                    }
                }

                override fun onFailure(call: Call<ResponOngkir>, t: Throwable) {
                }

            })
    }

    private fun displayOngkir(kurir: String, arrayList: ArrayList<Costs>) {

        if (arrayList.isEmpty()) div_kosong.visibility = View.VISIBLE
        else div_kosong.visibility = View.GONE

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_metode.adapter = AdapterKurir(arrayList, kurir, object : AdapterKurir.Listeners {
            override fun onClicked(data: Alamat) {

            }
        })
        rv_metode.layoutManager = layoutManager
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        checkAlamat()
        super.onResume()
    }


}