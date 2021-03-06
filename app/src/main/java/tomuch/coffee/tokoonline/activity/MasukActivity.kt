package tomuch.coffee.tokoonline.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_masuk.*
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.helper.SharedPref

class MasukActivity : AppCompatActivity() {

    lateinit var s: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masuk)

        s = SharedPref(this)

        mainButton()

    }

    private fun mainButton() {
        btn_prosesLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))

        }

        btn_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}