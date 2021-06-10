package tomuch.coffee.tokoonline.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.adapter.AdapterCart
import tomuch.coffee.tokoonline.adapter.AdapterProduk
import tomuch.coffee.tokoonline.room.MyDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KeranjangFragment : Fragment() {




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        init(view)

        mainButton()
        return view
    }

    private fun displayProduk() {
        val myDb = MyDatabase.getInstance(requireActivity())
        val listProduk = myDb!!.daoCart().getAll() as ArrayList
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rvProduk.adapter = AdapterCart(requireActivity(),listProduk)
        rvProduk.layoutManager = layoutManager
    }

    private fun mainButton() {
        btnBayar.setOnClickListener {

        }

        btnHapus.setOnClickListener {

        }
    }
    lateinit var btnHapus : ImageView
    lateinit var rvProduk : RecyclerView
    lateinit var btnBayar : TextView
    lateinit var tvTotal : TextView
    private fun init(view: View) {
        btnHapus = view.findViewById(R.id.btn_delet)
        rvProduk = view.findViewById(R.id.rv_produk)
        btnBayar = view.findViewById(R.id.btn_bayar)
        tvTotal = view.findViewById(R.id.tv_totalbayar)
    }

    override fun onResume() {
        displayProduk()
        super.onResume()
    }


}