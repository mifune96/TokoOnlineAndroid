package tomuch.coffee.tokoonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_home.*
import tomuch.coffee.tokoonline.activity.LoginActivity
import tomuch.coffee.tokoonline.activity.MasukActivity
import tomuch.coffee.tokoonline.fragment.AkunFragment
import tomuch.coffee.tokoonline.fragment.HomeFragment
import tomuch.coffee.tokoonline.fragment.KeranjangFragment
import tomuch.coffee.tokoonline.helper.SharedPref

class MainActivity : AppCompatActivity() {

    private val fragmentHome : Fragment = HomeFragment()
    private val fragmentAkun : Fragment = AkunFragment()
    private val fragmentKeranjang : Fragment = KeranjangFragment()
    private val fm: FragmentManager = supportFragmentManager
    private var active: Fragment = fragmentHome

    private lateinit var menu: Menu
    private lateinit var menuItem: MenuItem
    private lateinit var bottomNavigationView: BottomNavigationView

    private val statusLogin = false

    private lateinit var s:SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        s = SharedPref(this)

        setUpBottomNav()
    }

    fun setUpBottomNav (){
        fm.beginTransaction().add(R.id.container, fragmentHome).show(fragmentHome).commit()
        fm.beginTransaction().add(R.id.container, fragmentAkun).hide(fragmentAkun).commit()
        fm.beginTransaction().add(R.id.container, fragmentKeranjang).hide(fragmentKeranjang).commit()

        bottomNavigationView = findViewById(R.id.nav_view)
        menu = bottomNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.navigation_home -> {
                    callFragment(0,fragmentHome)
                }

                R.id.navigation_keranjang -> {
                    callFragment(1,fragmentKeranjang)
                }

                R.id.navigation_akun -> {
                    if (s.getStatusLofgin()){
                        callFragment(2,fragmentAkun)
                    } else{
                        startActivity(Intent(this, MasukActivity::class.java ))
                    }

                }

            }

            false
        }
    }

    fun callFragment(int: Int,fragment: Fragment){
        Log.d("Respons", "Akun ")
        menuItem = menu.getItem(int)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }
}