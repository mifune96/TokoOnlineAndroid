package tomuch.coffee.tokoonline.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_tambah_alamat.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.app.ApiConfigAlamat
import tomuch.coffee.tokoonline.helper.Helper
import tomuch.coffee.tokoonline.model.Alamat
import tomuch.coffee.tokoonline.model.ModelAlamat
import tomuch.coffee.tokoonline.model.ResponModel
import tomuch.coffee.tokoonline.room.MyDatabase
import tomuch.coffee.tokoonline.util.ApiKey

class TambahAlamatActivity : AppCompatActivity() {

    var provinsi = ModelAlamat.Provinsi()
    var kota = ModelAlamat.Provinsi()
    var kecamatan = ModelAlamat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_alamat)
        Helper().setToolbar(this, toolbar, "Tambah Alamat")

        mainButton()
        getProvinsi()

    }

    private fun mainButton() {
        btn_simpan.setOnClickListener {
            simpan()
        }
    }

    private fun simpan() {
        when {
            edt_nama.text.isEmpty() -> {
                error(edt_nama)
                return

            }
            edt_type.text.isEmpty() -> {
                error(edt_type)
                return

            }
            edt_phone.text.isEmpty() -> {
                error(edt_phone)
                return

            }
            edt_alamat.text.isEmpty() -> {
                error(edt_alamat)
                return

            }
            edt_kodePos.text.isEmpty() -> {
                error(edt_kodePos)
                return
            }
        }
        if (provinsi.province_id == "0") {
            toast("Silahkan pilih provinsi")
            return
        }

        if (kota.city_id == "0") {
            toast("Silahkan pilih kota")
            return
        }

//        if (kecamatan.id == 0) {
//            toast("Silahkan pilih kecamatan")
//            return
//        }

        val alamat = Alamat()
        alamat.name = edt_nama.text.toString()
        alamat.type = edt_type.text.toString()
        alamat.phone = edt_phone.text.toString()
        alamat.alamat = edt_alamat.text.toString()
        alamat.kodepos = edt_kodePos.text.toString()

        alamat.id_provinsi = Integer.valueOf(provinsi.province_id)
        alamat.provinsi = provinsi.province
        alamat.id_Kota = Integer.valueOf(kota.city_id)
        alamat.kota = kota.city_name
//        alamat.id_kecamatan = kecamatan.id
//        alamat.kecamatan = kecamatan.nama

        insert(alamat)
    }

    private fun toast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    private fun error(editText: EditText) {
        editText.error = "Kolom Ini tidak boleh kosong !!"
        editText.requestFocus()
    }

    private fun getProvinsi() {
        ApiConfigAlamat.instanceRetrofit.getProvinsi(ApiKey.key)
            .enqueue(object : Callback<ResponModel> {
                override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {

                    if (response.isSuccessful) {

                        pb_tambahalamat.visibility = View.GONE
                        div_provinsi.visibility = View.VISIBLE

                        val res = response.body()!!
                        val arrString = ArrayList<String>()
                        arrString.add("Pilih Provinsi")
                        val listProvinsi = res.rajaongkir.results
                        for (prov in listProvinsi) {
                            arrString.add(prov.province)
                        }

                        val adapter = ArrayAdapter<Any>(
                            this@TambahAlamatActivity,
                            R.layout.item_spinner,
                            arrString.toTypedArray()
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spn_provinsi.adapter = adapter

                        spn_provinsi.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    if (position != 0) {
                                        provinsi = listProvinsi[position - 1]
                                        val idProv =
                                            provinsi.province_id //kenapa -1 larna array 0 udah di add tulisan pilih provinsi
                                        getKota(idProv)
                                    }
                                }

                                override fun onNothingSelected(p0: AdapterView<*>?) {
                                }

                            }

                    } else {
                        Log.d("Error", "gagal get provinsi" + response.message())
                    }
                }

                override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                }

            })
    }

    private fun getKota(id: String) {
        pb_tambahalamat.visibility = View.VISIBLE
        ApiConfigAlamat.instanceRetrofit.getKota(ApiKey.key, id)
            .enqueue(object : Callback<ResponModel> {
                override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {

                    if (response.isSuccessful) {

                        pb_tambahalamat.visibility = View.GONE
                        div_kota.visibility = View.VISIBLE

                        val res = response.body()!!
                        val listKota = res.rajaongkir.results
                        val arrString = ArrayList<String>()
                        arrString.add("Pilih Kota/Kabupaten")
                        for (kota in listKota) {
                            arrString.add(kota.type + " " + kota.city_name)
                        }

                        val adapter = ArrayAdapter<Any>(
                            this@TambahAlamatActivity,
                            R.layout.item_spinner,
                            arrString.toTypedArray()
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spn_kota.adapter = adapter

                        spn_kota.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    if (position != 0) {
                                        kota = listKota[position - 1]
                                        val kodepos = kota.postal_code
                                        edt_kodePos.setText(kodepos)
//                                getKecamatan(idKota)
                                    }
                                }

                                override fun onNothingSelected(p0: AdapterView<*>?) {
                                }

                            }

                    } else {
                        Log.d("Error", "gagal get kota/kabupaten" + response.message())
                    }
                }

                override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                }

            })
    }

    private fun getKecamatan(id: Int) {
        pb_tambahalamat.visibility = View.VISIBLE
        ApiConfigAlamat.instanceRetrofit.getKecamatan(id).enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {

                if (response.isSuccessful) {

                    pb_tambahalamat.visibility = View.GONE
                    div_kecamatan.visibility = View.VISIBLE

                    val res = response.body()!!
                    val listkecmatan = res.kecamatan
                    val arrString = ArrayList<String>()
                    arrString.add("Pilih Kecamatan")
                    for (kec in listkecmatan) {
                        arrString.add(kec.nama)
                    }

                    val adapter = ArrayAdapter<Any>(
                        this@TambahAlamatActivity,
                        R.layout.item_spinner,
                        arrString.toTypedArray()
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_kecamatan.adapter = adapter
                    spn_kecamatan.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                if (position != 0) {
                                    kecamatan = listkecmatan[position - 1]
                                }
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }

                        }
                } else {
                    Log.d("Error", "gagal get Kecamatan" + response.message())
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
            }

        })
    }

    private fun insert(data: Alamat) {
        val myDb = MyDatabase.getInstance(this)!!
        if (myDb.daoAlamat().getByStatus(true) == null) {
            data.isSelected = true
        }
        CompositeDisposable().add(Observable.fromCallable { myDb.daoAlamat().insert(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                toast("Berhasil masukin data alamat cuy")
                onBackPressed()
            })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}