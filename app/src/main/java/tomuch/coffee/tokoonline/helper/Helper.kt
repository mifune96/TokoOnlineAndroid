package tomuch.coffee.tokoonline.helper

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class Helper {
    fun changeRupiah(string: String): String {
        return NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(string))
    }

    fun changeRupiah(value: Int): String {
        return NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(value)
    }

    fun setToolbar(activity: Activity, toolbar: Toolbar, title: String) {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        activity.supportActionBar!!.title = title
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    fun convertDateTime(tgl: String, formatBaru: String, formatLama: String = "yyyy-MM-dd kk:mm:ss"): String {
        val dateFormat = SimpleDateFormat(formatLama)
        val confert = dateFormat.parse(tgl)
        dateFormat.applyPattern(formatBaru)
        return dateFormat.format(confert)
    }
}
