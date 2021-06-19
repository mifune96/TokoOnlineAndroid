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

class TambahAlamatActivity : AppCompatActivity() {

    var provinsi = ModelAlamat()
    var kota = ModelAlamat()
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
        if (provinsi.id == 0) {
            toast("Silahkan pilih provinsi")
            return
        }

        if (kota.id == 0) {
            toast("Silahkan pilih kota")
            return
        }

        if (kecamatan.id == 0) {
            toast("Silahkan pilih kecamatan")
            return
        }

        val alamat = Alamat()
        alamat.name = edt_nama.text.toString()
        alamat.type = edt_type.text.toString()
        alamat.phone = edt_phone.text.toString()
        alamat.alamat = edt_alamat.text.toString()
        alamat.kodepos = edt_kodePos.text.toString()

        alamat.id_provinsi = provinsi.id
        alamat.provinsi = provinsi.nama
        alamat.id_Kota = kota.id
        alamat.kota = kota.nama
        alamat.id_kecamatan = kecamatan.id
        alamat.kecamatan = kecamatan.nama

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
        ApiConfigAlamat.instanceRetrofit.getProvinsi().enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {

                if (response.isSuccessful) {

                    pb_tambahalamat.visibility = View.GONE
                    div_provinsi.visibility = View.VISIBLE

                    val res = response.body()!!
                    val arrString = ArrayList<String>()
                    arrString.add("Pilih Provinsi")
                    val listProvinsi = res.provinsi
                    for (prov in listProvinsi) {
                        arrString.add(prov.nama)
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
                                        listProvinsi[position - 1].id //kenapa -1 larna array 0 udah di add tulisan pilih provinsi
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

    private fun getKota(id: Int) {
        ApiConfigAlamat.instanceRetrofit.getKota(id).enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {

                if (response.isSuccessful) {

                    pb_tambahalamat.visibility = View.GONE
                    div_kota.visibility = View.VISIBLE

                    val res = response.body()!!
                    val listKota = res.kota_kabupaten
                    val arrString = ArrayList<String>()
                    arrString.add("Pilih Kota/Kabupaten")
                    for (kot in listKota) {
                        arrString.add(kot.nama)
                    }

                    val adapter = ArrayAdapter<Any>(
                        this@TambahAlamatActivity,
                        R.layout.item_spinner,
                        arrString.toTypedArray()
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_kota.adapter = adapter

                    spn_kota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            if (position != 0) {
                                kota = listKota[position - 1]
                                val idKota =
                                    listKota[position - 1].id //kenapa -1 larna array 0 udah di add tulisan pilih provinsi
                                getKecamatan(idKota)
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
                                    val idKecamatan =
                                        listkecmatan[position - 1].id //kenapa -1 larna array 0 udah di add tulisan pilih provinsi
                                    getKecamatan(idKecamatan)
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
        CompositeDisposable().add(Observable.fromCallable { myDb.daoAlamat().insert(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                toast("Berhasil masukin data alamat cuy")
            })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}