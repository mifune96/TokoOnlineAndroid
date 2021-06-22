package tomuch.coffee.tokoonline.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_transaksi.*
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.internal.http2.Header
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.adapter.AdapterProdukTransaksi
import tomuch.coffee.tokoonline.helper.Helper
import tomuch.coffee.tokoonline.model.DetailTransaksi
import tomuch.coffee.tokoonline.model.Transaksi

class DetailTransaksiActivity : AppCompatActivity() {

    var transaksi = Transaksi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaksi)
        Helper().setToolbar(this, toolbar, "Detail Transaksi")

        val json = intent.getStringExtra("transaksi")
        transaksi = Gson().fromJson(json, Transaksi::class.java)

        setData(transaksi)
        displayProduk(transaksi.details)
    }

    private fun displayProduk(transaksis: ArrayList<DetailTransaksi>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_produk.adapter = AdapterProdukTransaksi(transaksis)
        rv_produk.layoutManager = layoutManager

    }


    private fun setData(t: Transaksi) {
        tv_status.text = t.status
        tv_tgl.text = t.created_at
        tv_penerima.text = t.name + " - " + t.phone
        tv_alamat.text = t.detail_lokasi
        tv_kodeUnik.text = Helper().changeRupiah(t.kode_unik)
        tv_totalBelanja.text = Helper().changeRupiah(t.total_harga)
        tv_ongkir.text = Helper().changeRupiah(t.ongkir)
        tv_total.text = Helper().changeRupiah(t.total_transfer)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}