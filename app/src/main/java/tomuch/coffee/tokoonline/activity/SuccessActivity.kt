package tomuch.coffee.tokoonline.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_success.*
import kotlinx.android.synthetic.main.toolbar.*
import tomuch.coffee.tokoonline.MainActivity
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.helper.Helper
import tomuch.coffee.tokoonline.model.Bank
import tomuch.coffee.tokoonline.model.Checkout
import tomuch.coffee.tokoonline.model.Transaksi
import tomuch.coffee.tokoonline.room.MyDatabase

class SuccessActivity : AppCompatActivity() {

    var nominal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        Helper().setToolbar(this, toolbar, "Bank Transfer")

        setValue()
        mainButton()

    }

    fun mainButton() {
        btn_copyNoRek.setOnClickListener {
            copyText(nominal.toString())

        }

        btn_copyNominal.setOnClickListener {
            copyText(nominal.toString())

        }

        btn_cekStatus.setOnClickListener {
            startActivity(Intent(this, RiwayatActivity::class.java))

        }
    }

    fun copyText(text:String){
        val copyManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val copyText = ClipData.newPlainText("text",text)
        copyManager.setPrimaryClip(copyText)

        Toast.makeText(this, "Text berhasil di copy", Toast.LENGTH_SHORT).show()
    }

    private fun setValue() {
        val jsBank = intent.getStringExtra("bank")
        val jsTransaksi = intent.getStringExtra("transaksi")
        val jsCheckout = intent.getStringExtra("checkout")

        val bank = Gson().fromJson(jsBank, Bank::class.java)
        val transaksi = Gson().fromJson(jsTransaksi, Transaksi::class.java)
        val checkout = Gson().fromJson(jsCheckout, Checkout::class.java)

        //hapus cart

        val myDb = MyDatabase.getInstance(this)!!

        for (produk in checkout.produks){
            myDb.daoCart().deleteById(produk.id)
        }

        tv_nomorRekening.text = bank.rekening
        tv_namaPenerima.text = bank.penerima
        image_bank.setImageResource(bank.image)

        nominal = Integer.valueOf(transaksi.total_transfer) + transaksi.kode_unik
        tv_nominal.text = Helper().changeRupiah(nominal)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }

}