package tomuch.coffee.tokoonline.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_pembayaran.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.app.ApiConfig
import tomuch.coffee.tokoonline.model.Checkout
import tomuch.coffee.tokoonline.model.ResponModel

class PembayaranActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)

        btn_bca.setOnClickListener {
            bayar("bca")

        }

        btn_bri.setOnClickListener {
            bayar("bri")

        }

        btn_mandiri.setOnClickListener {
            bayar("mandiri")

        }
    }

    fun bayar(bank: String){

        val  json = intent.getStringExtra("extra")!!.toString()
        val checkout = Gson().fromJson(json,Checkout::class.java)
        checkout.bank = bank

        ApiConfig.instanceRetrofit.checkout(checkout)
            .enqueue(object : Callback<ResponModel> {
                override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                    val respon = response.body()!!
                    if (respon.succes == 1) {

                        Toast.makeText(
                            this@PembayaranActivity,
                            "Berhasil kirim data ke server",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        //Respon Gagal
                        Toast.makeText(
                            this@PembayaranActivity,
                            "Error: " + respon.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                    Toast.makeText(
                        this@PembayaranActivity,
                        "Error: " + t.message,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            })
    }
}