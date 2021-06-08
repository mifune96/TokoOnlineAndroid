package tomuch.coffee.tokoonline.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tomuch.coffee.tokoonline.MainActivity
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.adapter.AdapterProduk
import tomuch.coffee.tokoonline.adapter.AdapterSlider
import tomuch.coffee.tokoonline.app.ApiConfig
import tomuch.coffee.tokoonline.model.Produk
import tomuch.coffee.tokoonline.model.ResponModel


class HomeFragment : Fragment() {

    lateinit var vpSlider: ViewPager
    lateinit var rvProduk: RecyclerView
    lateinit var rvProdukTerlaris: RecyclerView
    lateinit var rvElektronik: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        getProduk()
        displayProduk()


        return view
    }

    fun displayProduk() {
        val arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.slider1)
        arrSlider.add(R.drawable.slider2)
        arrSlider.add(R.drawable.slider3)


        val adapterSlider = AdapterSlider(arrSlider, activity)
        vpSlider.adapter = adapterSlider

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManager2 = LinearLayoutManager(activity)
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManager3 = LinearLayoutManager(activity)
        layoutManager3.orientation = LinearLayoutManager.HORIZONTAL

//        rvProduk.adapter = AdapterProduk(arrProduk)
//        rvProduk.layoutManager = layoutManager
//
//        rvProdukTerlaris.adapter = AdapterProduk(arrProdukTerlaris)
//        rvProdukTerlaris.layoutManager = layoutManager2
//
//        rvElektronik.adapter = AdapterProduk(arrElektronik)
//        rvElektronik.layoutManager = layoutManager3
    }

    fun getProduk() {
        ApiConfig.instanceRetrofit.login(edt_email.text.toString(),edt_password.text.toString()).enqueue(object :
            Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {

            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

        })

    }

    fun init(view: View) {
        vpSlider = view.findViewById(R.id.vp_slider)
        rvProduk = view.findViewById(R.id.rv_produk)
        rvProdukTerlaris = view.findViewById(R.id.rv_terlaris)
        rvElektronik = view.findViewById(R.id.rv_elektronik)
    }

//    val arrProduk: ArrayList<Produk>get(){
//        val arr = ArrayList<Produk>()
//        val p1 = Produk()
//        p1.nama = "HP  14 bs"
//        p1.harga = "Rp.22.000.000"
//        p1.gambar = R.drawable.hp_14_bs749tu
//
//        val p2 = Produk()
//        p2.nama = "HP Envy i3 aq"
//        p2.harga = "Rp.12.000.000"
//        p2.gambar = R.drawable.hp_envy_13_aq0019tx
//
//        val p3 = Produk()
//        p3.nama = "HP Pavilion 13"
//        p3.harga = "Rp.17.080.000"
//        p3.gambar = R.drawable.hp_pavilion_13_an0006na
//
//        arr.add(p1)
//        arr.add(p2)
//        arr.add(p3)
//
//        return arr
//    }
//
//    val arrProdukTerlaris: ArrayList<Produk>get(){
//        val arr = ArrayList<Produk>()
//        val p1 = Produk()
//        p1.nama = "HP  14 bs7110tu"
//        p1.harga = "Rp.5.000.000"
//        p1.gambar = R.drawable.hp_notebook_14_bs710tu
//
//        val p2 = Produk()
//        p2.nama = "HP Pavilion 15 wm"
//        p2.harga = "Rp.92.000.000"
//        p2.gambar = R.drawable.hp_pavilion_15_cx0056wm
//
//        val p3 = Produk()
//        p3.nama = "HP Pavilion 14 Ce150"
//        p3.harga = "Rp.8.080.000"
//        p3.gambar = R.drawable.hp_pavilion_14_ce1507sa
//
//        arr.add(p1)
//        arr.add(p2)
//        arr.add(p3)
//
//        return arr
//    }
//
//    val arrElektronik: ArrayList<Produk>get(){
//        val arr = ArrayList<Produk>()
//        val p1 = Produk()
//        p1.nama = "HP  13 AQ"
//        p1.harga = "Rp.2.000.000"
//        p1.gambar = R.drawable.hp_envy_13_aq0019tx
//
//        val p2 = Produk()
//        p2.nama = "HP Pavilion 13 an"
//        p2.harga = "Rp.17.000.000"
//        p2.gambar = R.drawable.hp_pavilion_13_an0006na
//
//        val p3 = Produk()
//        p3.nama = "HP 14 bs"
//        p3.harga = "Rp.17.080.000"
//        p3.gambar = R.drawable.hp_14_bs749tu
//
//        arr.add(p1)
//        arr.add(p2)
//        arr.add(p3)
//
//        return arr
//    }


}