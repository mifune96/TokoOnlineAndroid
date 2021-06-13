package tomuch.coffee.tokoonline.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_produk.*
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import kotlinx.android.synthetic.main.toolbar_custom.*
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.helper.Helper
import tomuch.coffee.tokoonline.model.Produk
import tomuch.coffee.tokoonline.room.MyDatabase

class DetailProdukActivity : AppCompatActivity() {

    lateinit var myDb: MyDatabase
    lateinit var produk: Produk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produk)

        myDb = MyDatabase.getInstance(this)!! // call database
        getInfo()
        mainButton()
        checkCart()

    }

    private fun mainButton(){
        btn_keranjangdetailproduk.setOnClickListener {
            val data = myDb.daoCart().getProduk(produk.id)
            if (data == null){
                insert()
            } else {
                data.jumlah = data.jumlah + 1
                update(data)
            }

        }

        btn_favoritdetailproduk.setOnClickListener {

            val listNote = myDb.daoCart().getAll() // get All data
            for(note :Produk in listNote){
                println("-----------------------")
                println(note.name)
                println(note.harga)
            }
        }
        btn_keranjangcostom.setOnClickListener {
//            startActivity(Intent(this,))
        }
    }

    private fun insert(){
        CompositeDisposable().add(Observable.fromCallable { myDb.daoCart().insert(produk) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkCart()
                Log.d("respons", "data inserted")
                Toast.makeText(this, "Data Berhasil Ditambahkan ke Cart",Toast.LENGTH_SHORT).show()
            })
    }

    private fun update(data: Produk){
        CompositeDisposable().add(Observable.fromCallable { myDb.daoCart().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkCart()
                Log.d("respons", "data inserted")
                Toast.makeText(this, "Data Berhasil Ditambahkan ke Cart",Toast.LENGTH_SHORT).show()
            })
    }

    private fun checkCart(){
        val dataCart = myDb.daoCart().getAll()
        if (dataCart.isNotEmpty()){
            div_angka.visibility = View.VISIBLE
            tv_angka.text = dataCart.size.toString()
        }else{
            div_angka.visibility = View.GONE

        }
    }

    private fun getInfo() {
        val data = intent.getStringExtra("extra")
        produk = Gson().fromJson<Produk>(data, Produk::class.java)

        //setvaluew
        tv_nama_detailproduk.text = produk.name
        tv_hargadetailitem.text = Helper().changeRupiah(produk.harga)
        tv_deskripisdetailproduk.text = produk.deskripsi

//        val img = "http://192.168.1.4/tokoonline/public/storage/produk/" +produk.image
//        val img = "https://06794948d1a0.ngrok.io/tokoonline/public/storage/produk/" +produk.image
        val img = "https://414260128518.ngrok.io/storage/produk/" +produk.image
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.product)
            .error(R.drawable.product)
            .resize(400,400)
            .into(iv_image)

        //settoolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = produk.name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}