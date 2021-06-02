package tomuch.coffee.tokoonline.helper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharedPref(activity: Activity){

    val login = "login"

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
}