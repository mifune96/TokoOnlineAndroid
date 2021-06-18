package tomuch.coffee.tokoonline.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_list_alamat.*
import kotlinx.android.synthetic.main.toolbar.*
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.helper.Helper

class ListAlamatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_alamat)
        Helper().setToolbar(this, toolbar, "Pilih Alamat")


        mainButton()
    }

    private fun mainButton() {
        btn_tambahAlamatlist.setOnClickListener {
            startActivity(Intent(this, TambahAlamatActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}