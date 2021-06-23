package tomuch.coffee.tokoonline.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
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
import java.text.SimpleDateFormat

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

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context = parent.context
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

        // 2021-04-30 18:30:20 //24
        // jam 1 k || 01 kk
        // 09:20:20 am 12/pm/am
        //proses comfer datetime dari server ke text view

        val formatBaru ="d MMM yyyy"
        holder.tvTangal.text = Helper().convertDateTime(a.created_at, formatBaru)

        var color = context.getColor(R.color.menungu)
        if (a.status == "SELESAI") color = context.getColor(R.color.selesai)
        else if (a.status == "BATAL") color = context.getColor(R.color.batal)

        holder.tvStatus.setTextColor(color)

        holder.layout.setOnClickListener {
            listener.onClicked(a)
        }
    }

    interface Listeners{
        fun onClicked(data: Transaksi)
    }

}