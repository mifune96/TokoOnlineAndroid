package tomuch.coffee.tokoonline.helper

import android.R
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import tomuch.coffee.tokoonline.model.User

class SharedPref(activity: Activity){

    val login = "login"

//    val nama = "nama"
//    val phone = "phone"
//    val email = "email"

    val user = "user"

    val myPref = "MAIN_PREF"
    val sp:SharedPreferences

    init {
        sp = activity.getSharedPreferences(myPref, Context.MODE_PRIVATE)
    }

    fun setStatusLogin(status:Boolean){
        sp.edit().putBoolean(login,status).apply()
    }

    fun getStatusLofgin():Boolean{
        return sp.getBoolean(login, false)
    }

    fun setUser(value: User) {
        val data: String = Gson().toJson(value, User::class.java)
        sp.edit().putString(user, data).apply()
    }

    fun getUser(): User? {
        val data = sp.getString(user, null) ?: return null
        return Gson().fromJson<User>(data, User::class.java)
    }

    fun setSting(key: String, value: String) {
        sp.edit().putString(key,value).apply()
    }

    fun getString(key: String): String {
        return sp.getString(key,"")!!
    }
}