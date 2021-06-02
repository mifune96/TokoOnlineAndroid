package tomuch.coffee.tokoonline.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.helper.SharedPref

class LoginActivity : AppCompatActivity() {

    lateinit var s:SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        s = SharedPref(this)
        btn_prosesLogin.setOnClickListener {
            s.setStatusLogin(true)
        }
    }
}