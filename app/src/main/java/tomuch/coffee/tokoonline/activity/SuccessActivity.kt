package tomuch.coffee.tokoonline.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_success.*
import kotlinx.android.synthetic.main.toolbar.*
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.helper.Helper
import tomuch.coffee.tokoonline.model.Bank
import tomuch.coffee.tokoonline.model.Transaksi

class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        Helper().setToolbar(this, toolbar, "Bank Transfer")

        setValue()
    }

    private fun setValue() {
        val jsBank = intent.getStringExtra("bank")
        val jsTransaksi = intent.getStringExtra("transaksi")

        val bank = Gson().fromJson(jsBank, Bank::class.java)
        val transaksi = Gson().fromJson(jsTransaksi, Transaksi::class.java)

        tv_nomorRekening.text = bank.rekening
        tv_namaPenerima.text = bank.penerima
        image_bank.setImageResource(bank.image)

        tv_nominal.text = Helper().changeRupiah(Integer.valueOf(transaksi.total_transfer) + transaksi.kode_unik)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}