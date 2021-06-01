package tomuch.coffee.tokoonline.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.adapter.AdapterProduk
import tomuch.coffee.tokoonline.adapter.AdapterSlider
import tomuch.coffee.tokoonline.model.Produk


class HomeFragment : Fragment() {

    lateinit var vpSlider: ViewPager
    lateinit var rvProduk: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        vpSlider = view.findViewById(R.id.vp_slider)
        rvProduk = view.findViewById(R.id.rv_produk)

        val arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.slider1)
        arrSlider.add(R.drawable.slider2)
        arrSlider.add(R.drawable.slider3)


        val adapterSlider = AdapterSlider(arrSlider, activity)
        vpSlider.adapter = adapterSlider

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        rvProduk.adapter = AdapterProduk(arrProduk)
        rvProduk.layoutManager = layoutManager

        return view
    }

    val arrProduk: ArrayList<Produk>get(){
        val arr = ArrayList<Produk>()
        val p1 = Produk()
        p1.nama = "HP  14 bs"
        p1.harga = "Rp.22.000.000"
        p1.gambar = R.drawable.hp_14_bs749tu

        val p2 = Produk()
        p2.nama = "HP Envy i3 aq"
        p2.harga = "Rp.12.000.000"
        p2.gambar = R.drawable.hp_envy_13_aq0019tx

        val p3 = Produk()
        p3.nama = "HP Pavilion 13"
        p3.harga = "Rp.17.080.000"
        p3.gambar = R.drawable.hp_pavilion_13_an0006na

        arr.add(p1)
        arr.add(p2)
        arr.add(p3)

        return arr
    }


}