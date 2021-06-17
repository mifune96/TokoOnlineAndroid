package tomuch.coffee.tokoonline.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tomuch.coffee.tokoonline.MainActivity
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.app.ApiConfig
import tomuch.coffee.tokoonline.helper.SharedPref
import tomuch.coffee.tokoonline.model.ResponModel

class RegisterActivity : AppCompatActivity() {

    lateinit var s: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        s = SharedPref(this)

        btn_register.setOnClickListener {
            register()
        }

        btn_google.setOnClickListener {
            dataDummy()
        }
    }

    fun dataDummy() {

        edt_nama.setText("Ali")
        edt_email.setText("ali@gmail.com")
        edt_nomortlp.setText("08127771")
        edt_password.setText("12345678")
    }

    fun register() {
        if (edt_nama.text.isEmpty()) {
            edt_nama.error = "Kolom Nama tidak boleh kosong"
            edt_nama.requestFocus()
            return

        } else if (edt_email.text.isEmpty()) {
            edt_email.error = "Kolom Email tidak boleh kosong"
            edt_email.requestFocus()
            return

        } else if (edt_nomortlp.text.isEmpty()) {
            edt_nomortlp.error = "Kolom Nomor Telpon tidak boleh kosong"
            edt_nomortlp.requestFocus()
            return

        } else if (edt_password.text.isEmpty()) {
            edt_password.error = "Kolom Password tidak boleh kosong"
            edt_password.requestFocus()
            return
        }

        pb_register.visibility = View.VISIBLE

        ApiConfig.instanceRetrofit.register(
            edt_nama.text.toString(),
            edt_email.text.toString(),
            edt_nomortlp.text.toString(),
            edt_password.text.toString()
        ).enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                pb_register.visibility = View.GONE
                val respon = response.body()!!
                if (respon.succes == 1) {
                    //Respon berhasil
                    s.setStatusLogin(true)
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(
                        this@RegisterActivity,
                        "Selamat Datang " + respon.user.name,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    //Respon Gagal
                    Toast.makeText(
                        this@RegisterActivity,
                        "Error: " + respon.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                pb_register.visibility = View.GONE
                Toast.makeText(this@RegisterActivity, "Error: " + t.message, Toast.LENGTH_SHORT)
                    .show()
            }

        })


    }
}