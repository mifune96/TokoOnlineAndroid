package tomuch.coffee.tokoonline.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.helper.Helper
import tomuch.coffee.tokoonline.model.Alamat
import tomuch.coffee.tokoonline.model.rajaongkir.Costs

class AdapterKurir(var data: ArrayList<Costs>, var kurir: String, var listener: Listeners) :
    RecyclerView.Adapter<AdapterKurir.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvLamapengiriman = view.findViewById<TextView>(R.id.tv_lamaPengiriman)
        val tvBerat = view.findViewById<TextView>(R.id.tv_berat)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val layout = view.findViewById<LinearLayout>(R.id.layout)
        val rd = view.findViewById<RadioButton>(R.id.rd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_kurir, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val a = data[position]

        holder.rd.isChecked = a.isActive
        holder.tvNama.text = kurir + " " + a.service
        val cost = a.cost[0]
        holder.tvLamapengiriman.text = cost.etd + " hari kerja"
        holder.tvHarga.text = Helper().changeRupiah(cost.value)
        holder.tvBerat.text = "1 Kg x " + Helper().changeRupiah(cost.value)
//        holder.tvAlamat.text = a.alamat +", " + a.kota + ", " + a.kecamatan + ", " + a.kodepos + ", (" + a.type + ")"

        holder.rd.setOnClickListener {
            a.isActive = true
            listener.onClicked(a, holder.adapterPosition)
        }
//        holder.layout.setOnClickListener {
//            a.isSelected = true
//            listener.onClicked(a)
//        }

    }

    interface Listeners {
        fun onClicked(data: Costs, index: Int)
    }

}