package tomuch.coffee.tokoonline.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.helper.SharedPref

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AkunFragment : Fragment() {

    lateinit var s:SharedPref
    lateinit var btnLogout:TextView
    lateinit var tvNama:TextView
    lateinit var tvEmail:TextView
    lateinit var tvNotlpn:TextView

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_akun, container, false)
        init(view)

        s = SharedPref(activity!!)


        btnLogout.setOnClickListener {
            s.setStatusLogin(false)
        }

        setData()
        return view
    }

    fun setData() {
        tvNama.text = s.getString(s.nama)
        tvEmail.text = s.getString(s.email)
        tvNotlpn.text = s.getString(s.phone)

    }

    private fun init(view: View) {
        btnLogout = view.findViewById(R.id.btn_logout)
        tvNama = view.findViewById(R.id.tv_namaprofil)
        tvNotlpn = view.findViewById(R.id.tv_nomortlpprofil)
        tvEmail = view.findViewById(R.id.tv_emailprofil)
    }


}