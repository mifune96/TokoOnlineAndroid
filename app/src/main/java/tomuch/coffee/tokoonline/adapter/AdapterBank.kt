package tomuch.coffee.tokoonline.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.model.Bank

class AdapterBank(var data: ArrayList<Bank>, var listener: Listeners) :
    RecyclerView.Adapter<AdapterBank.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val image = view.findViewById<ImageView>(R.id.image)
        val layout = view.findViewById<LinearLayout>(R.id.layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_bank, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val a = data[position]

        holder.tvNama.text = a.nama
        holder.image.setImageResource(a.image)

        holder.layout.setOnClickListener {
            listener.onClicked(a, holder.adapterPosition)
        }

    }

    interface Listeners {
        fun onClicked(data: Bank, index: Int)
    }

}