package tomuch.coffee.tokoonline.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_pembayaran.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.adapter.AdapterBank
import tomuch.coffee.tokoonline.app.ApiConfig
import tomuch.coffee.tokoonline.helper.Helper
import tomuch.coffee.tokoonline.model.Bank
import tomuch.coffee.tokoonline.model.Checkout
import tomuch.coffee.tokoonline.model.ResponModel
import tomuch.coffee.tokoonline.model.Transaksi

class PembayaranActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)
        Helper().setToolbar(this, toolbar, "Pembayaran")

        displayBank()
    }

    fun displayBank() {
        val arrBank = ArrayList<Bank>()
        arrBank.add(Bank("BCA", "0081223411", "Ali Imran", R.drawable.logo_bca))
        arrBank.add(Bank("BRI", "716122112", "Ali Imran", R.drawable.logo_bri))
        arrBank.add(Bank("Mandiri", "1142288131", "Ali Imran", R.drawable.logo_madiri))

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_bank.layoutManager = layoutManager
        rv_bank.adapter = AdapterBank(arrBank, object : AdapterBank.Listeners {
            override fun onClicked(data: Bank, index: Int) {
                bayar(data)
            }
        })
    }

    fun bayar(bank: Bank) {
        val json = intent.getStringExtra("extra")!!.toString()
        val chekout = Gson().fromJson(json, Checkout::class.java)
        chekout.bank = bank.nama

        ApiConfig.instanceRetrofit.chekout(chekout).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
//                Toast.makeText(this, "Error:" + t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {


                if (!response.isSuccessful) {
                    Log.d("Respons", "Erorrnya:" + response.message())
                    return
                }

                val respon = response.body()!!
                Log.d("Respon pembayaran", "isinya responnya" +respon.succes)
                if (respon.succes == 1) {

                    val jsBank = Gson().toJson(bank, Bank::class.java)
                    val jsTransaksi = Gson().toJson(respon.transaksi, Transaksi::class.java)
                    val jsCheckout = Gson().toJson(chekout, Checkout::class.java)

                    val intent = Intent(this@PembayaranActivity, SuccessActivity::class.java)
                    intent.putExtra("bank", jsBank)
                    intent.putExtra("transaksi", jsTransaksi)
                    intent.putExtra("checkout", jsCheckout)
                    startActivity(intent)

                } else {
                    Toast.makeText(this@PembayaranActivity, "Error:" + respon.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}