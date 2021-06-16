package tomuch.coffee.tokoonline.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.helper.Helper
import tomuch.coffee.tokoonline.model.Produk
import tomuch.coffee.tokoonline.room.MyDatabase
import tomuch.coffee.tokoonline.util.Config
import java.util.*
import kotlin.collections.ArrayList

class AdapterCart(var activity: Activity, var data:ArrayList<Produk>, var listener :Listeners): RecyclerView.Adapter<AdapterCart.Holder>() {

    class Holder(view: View):RecyclerView.ViewHolder(view){
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val layout = view.findViewById<CardView>(R.id.layout)

        val btnTambah = view.findViewById<ImageView>(R.id.btn_tambah)
        val btnKurang = view.findViewById<ImageView>(R.id.btn_kurang)
        val btnDelet = view.findViewById<ImageView>(R.id.btn_delete)

        val checkbox = view.findViewById<CheckBox>(R.id.cb_produk)
        val tvJumlah = view.findViewById<TextView>(R.id.tv_jumlah)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return  data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val produk = data[position]
        val harga = Integer.valueOf(produk.harga)

        // tempat set value
        holder.tvNama.text = produk.name
        holder.tvHarga.text = Helper().changeRupiah(harga * produk.jumlah)

        var jumlah = data[position].jumlah
        holder.tvJumlah.text = jumlah.toString()

        holder.checkbox.isChecked = produk.selected
        holder.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->

            produk.selected = isChecked
            update(produk)
        }

//        val image = "http://192.168.100.50/tokoonline/public/storage/produk/" +data[position].image

        val image = Config.produkUrl +produk.image
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.product)
            .error(R.drawable.product)
            .resize(400,400)
            .into(holder.imgProduk)

        holder.btnTambah.setOnClickListener {
            jumlah++
            produk.jumlah = jumlah
            update(produk)
            holder.tvJumlah.text = jumlah.toString()
            holder.tvHarga.text = Helper().changeRupiah(harga * jumlah)
        }

        holder.btnKurang.setOnClickListener {
            if (jumlah <= 1) return@setOnClickListener
            jumlah--

            produk.jumlah = jumlah
            update(produk)
            holder.tvJumlah.text = jumlah.toString()
            holder.tvHarga.text = Helper().changeRupiah(harga * jumlah)
        }

        holder.btnDelet.setOnClickListener {
            delet(produk)
            listener.onDelet(position)
        }

    }

    interface Listeners{
        fun onUpdate()
        fun onDelet(position: Int)
    }

    private fun update(data: Produk){
        val myDb = MyDatabase.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoCart().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listener.onUpdate()
            })
    }

    private fun delet(data: Produk){
        val myDb = MyDatabase.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoCart().delete(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

            })
    }

}