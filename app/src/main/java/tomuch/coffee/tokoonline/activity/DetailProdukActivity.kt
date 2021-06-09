package tomuch.coffee.tokoonline.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_produk.*
import kotlinx.android.synthetic.main.toolbar.*
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.helper.Helper
import tomuch.coffee.tokoonline.model.Produk
import tomuch.coffee.tokoonline.room.MyDatabase

class DetailProdukActivity : AppCompatActivity() {

    lateinit var produk: Produk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produk)

        getInfo()
        mainButton()
    }

    fun mainButton(){
        btn_keranjangdetailproduk.setOnClickListener {
            insert()
        }

        btn_favoritdetailproduk.setOnClickListener {
            val myDb: MyDatabase = MyDatabase.getInstance(this)!! // call database
            val listNote = myDb.daoCart().getAll() // get All data
            for(note :Produk in listNote){
                println("-----------------------")
                println(note.name)
                println(note.harga)
            }
        }
    }

    fun insert(){
        val myDb: MyDatabase = MyDatabase.getInstance(this)!! // call database

        CompositeDisposable().add(Observable.fromCallable { myDb.daoCart().insert(produk) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("respons", "data inserted")
            })
    }

    fun getInfo() {
        val data = intent.getStringExtra("extra")
        produk = Gson().fromJson<Produk>(data, Produk::class.java)

        //setvaluew
        tv_nama_detailproduk.text = produk.name
        tv_hargadetailitem.text = Helper().changeRupiah(produk.harga)
        tv_deskripisdetailproduk.text = produk.deskripsi

//        val img = "http://192.168.1.4/tokoonline/public/storage/produk/" +produk.image
//        val img = "https://06794948d1a0.ngrok.io/tokoonline/public/storage/produk/" +produk.image
        val img = "https://6e5b8e882b4e.ngrok.io/storage/produk/" +produk.image
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