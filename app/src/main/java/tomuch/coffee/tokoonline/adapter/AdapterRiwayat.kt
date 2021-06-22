package tomuch.coffee.tokoonline.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.activity.DetailProdukActivity
import tomuch.coffee.tokoonline.helper.Helper
import tomuch.coffee.tokoonline.model.Alamat
import tomuch.coffee.tokoonline.model.Produk
import tomuch.coffee.tokoonline.model.Transaksi
import tomuch.coffee.tokoonline.util.Config

class AdapterRiwayat(var data: ArrayList<Transaksi>, var listener : Listeners):
    RecyclerView.Adapter<AdapterRiwayat.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val tvTangal = view.findViewById<TextView>(R.id.tv_tgl)
        val tvJumlah = view.findViewById<TextView>(R.id.tv_jumlah)
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
        val btnDetail = view.findViewById<TextView>(R.id.btn_detail)
        val layout = view.findViewById<CardView>(R.id.layout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // tempat set value

        val a = data[position]

        val name = a.details[0].produk.name
        holder.tvNama.text = name
        holder.tvHarga.text = Helper().changeRupiah(a.total_transfer)
        holder.tvJumlah.text = a.total_item + " Items"
        holder.tvStatus.text = a.status
        holder.tvTangal.text = a.created_at

        holder.layout.setOnClickListener {
            listener.onClicked(a)
        }
    }

    interface Listeners{
        fun onClicked(data: Transaksi)
    }

}