package tomuch.coffee.tokoonline.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.activity.PengirimanActivity
import tomuch.coffee.tokoonline.adapter.AdapterCart
import tomuch.coffee.tokoonline.helper.Helper
import tomuch.coffee.tokoonline.model.Produk
import tomuch.coffee.tokoonline.room.MyDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KeranjangFragment : Fragment() {

    lateinit var myDb: MyDatabase
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        init(view)
        myDb = MyDatabase.getInstance(requireActivity())!!

        mainButton()
        return view
    }

    lateinit var adapter: AdapterCart
    var listProduk = ArrayList<Produk>()
    private fun displayProduk() {
        listProduk = myDb.daoCart().getAll() as ArrayList
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL


        adapter = AdapterCart(requireActivity(), listProduk, object : AdapterCart.Listeners {
            override fun onUpdate() {
                hitungTotal()
            }

            override fun onDelet(position: Int) {
                listProduk.removeAt(position)
                adapter.notifyDataSetChanged()
                hitungTotal()
            }
        })
        rvProduk.adapter = adapter
        rvProduk.layoutManager = layoutManager
    }

    var totalHarga = 0
    fun hitungTotal() {
        val listProduk = myDb.daoCart().getAll() as ArrayList

        totalHarga = 0
        var isSelectedAll = true
        for (produk in listProduk) {
            if (produk.selected) {
                val harga = Integer.valueOf(produk.harga)
                totalHarga += (harga * produk.jumlah)
            } else {
                isSelectedAll = false
            }
        }

        cbAll.isChecked = isSelectedAll
        tvTotal.text = Helper().changeRupiah(totalHarga)
    }

    private fun mainButton() {
        btnBayar.setOnClickListener {
            val intent = (Intent(requireActivity(), PengirimanActivity::class.java))
            intent.putExtra("extra", ""+ totalHarga)
            startActivity(intent)
        }

        btnHapus.setOnClickListener {

        }

        cbAll.setOnClickListener {
            for (i in listProduk.indices) {
                val produk = listProduk[i]
                produk.selected = cbAll.isChecked
                listProduk[i] = produk

            }
            adapter.notifyDataSetChanged()
        }
    }

    lateinit var btnHapus: ImageView
    lateinit var rvProduk: RecyclerView
    lateinit var btnBayar: TextView
    lateinit var tvTotal: TextView
    lateinit var cbAll: CheckBox
    private fun init(view: View) {
        btnHapus = view.findViewById(R.id.btn_delet)
        rvProduk = view.findViewById(R.id.rv_produk)
        btnBayar = view.findViewById(R.id.btn_bayar)
        tvTotal = view.findViewById(R.id.tv_totalbayar)
        cbAll = view.findViewById(R.id.cb_all)
    }

    override fun onResume() {
        displayProduk()
        hitungTotal()
        super.onResume()
    }


}