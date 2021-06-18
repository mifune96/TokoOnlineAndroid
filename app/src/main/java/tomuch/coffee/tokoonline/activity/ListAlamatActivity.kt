package tomuch.coffee.tokoonline.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_list_alamat.*
import tomuch.coffee.tokoonline.R

class ListAlamatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_alamat)

        mainButton()
    }

    private fun mainButton() {
        btn_tambahAlamatlist.setOnClickListener {
            startActivity(Intent(this, TambahAlamatActivity::class.java))
        }
    }
}