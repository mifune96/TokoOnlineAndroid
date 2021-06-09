package tomuch.coffee.tokoonline.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edt_email
import kotlinx.android.synthetic.main.activity_login.edt_password
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tomuch.coffee.tokoonline.MainActivity
import tomuch.coffee.tokoonline.R
import tomuch.coffee.tokoonline.app.ApiConfig
import tomuch.coffee.tokoonline.helper.SharedPref
import tomuch.coffee.tokoonline.model.ResponModel

class LoginActivity : AppCompatActivity() {

    lateinit var s:SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        s = SharedPref(this)
        btn_login.setOnClickListener {
            login()
        }
    }

    fun login(){
        if (edt_email.text.isEmpty()) {
            edt_email.error = "Kolom Email tidak boleh kosong"
            edt_email.requestFocus()
            return

        }else if (edt_password.text.isEmpty()) {
            edt_password.error = "Kolom Password tidak boleh kosong"
            edt_password.requestFocus()
            return
        }

        pb_login.visibility = View.VISIBLE

        ApiConfig.instanceRetrofit.login(edt_email.text.toString(),edt_password.text.toString()).enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                pb_login.visibility = View.GONE
                val respon = response.body()!!
                if (respon.succes == 1){
                    //Respon berhasil
                    s.setStatusLogin(true)
                    s.setUser(respon.user)
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@LoginActivity,"Selamat Datang " +respon.user.name, Toast.LENGTH_SHORT).show()
                } else{
                    //Respon Gagal
                    Toast.makeText(this@LoginActivity,"Error: " +respon.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                pb_login.visibility = View.GONE
                Toast.makeText(this@LoginActivity,"Error: "+t.message, Toast.LENGTH_SHORT).show()
            }

        })


    }
}