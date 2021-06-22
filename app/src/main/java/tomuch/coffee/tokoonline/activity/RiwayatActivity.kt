package tomuch.coffee.tokoonline.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_riwayat.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.adapter.AdapterRiwayat
import tomuch.coffee.tokoonline.app.ApiConfig
import tomuch.coffee.tokoonline.helper.Helper
import tomuch.coffee.tokoonline.helper.SharedPref
import tomuch.coffee.tokoonline.model.ResponModel
import tomuch.coffee.tokoonline.model.Transaksi

class RiwayatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)
        Helper().setToolbar(this, toolbar, "Riwayat Belanja")

        getHistory()

    }

    fun getHistory() {
        val id = SharedPref(this).getUser()!!.id
        ApiConfig.instanceRetrofit.getHistory(id.toString()).enqueue(object :
            Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.succes == 1) {
                    displayRiwayat(res.transaksis)
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

        })


    }

    fun displayRiwayat(transaksis: ArrayList<Transaksi>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL


        rv_riwayat.adapter = AdapterRiwayat(transaksis, object : AdapterRiwayat.Listeners {
            override fun onClicked(data: Transaksi) {

            }
        })
        rv_riwayat.layoutManager = layoutManager
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}