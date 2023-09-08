package com.example.fininfocomtask.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.*
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.fininfocomtask.R
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun myToast(activity: Activity, message: String) {
    val layout = activity.layoutInflater.inflate(
        R.layout.my_toast,
        activity.findViewById(R.id.toast_container)
    )
    val textView = layout.findViewById<TextView>(R.id.toast_text)
    textView.text = message
    val myToast = Toast(activity)
    myToast.duration = Toast.LENGTH_SHORT
    myToast.setGravity(Gravity.BOTTOM, 0, 40)
    myToast.view = layout //setting the view of custom toast layout
    myToast.show()
}

fun progrossDilog(context: Context) {

    var progressDialog: ProgressDialog? = null
    progressDialog = ProgressDialog(context)
    progressDialog!!.setMessage("Loading..")
    progressDialog!!.setTitle("Please Wait")
    progressDialog!!.isIndeterminate = false
    progressDialog!!.setCancelable(true)
    progressDialog.show()
}
fun convertTo12Hour(Time: String): String? {
    var Time = Time
    if (Time.length == 5) {
        Time = "$Time:00"
    }
    val f1: DateFormat = SimpleDateFormat("hh:mm:ss") //11:00 pm
    var d: Date? = null
    try {
        d = f1.parse(Time)
    } catch (e: ParseException) {
        // TODO Auto-generated catch block
        e.printStackTrace()
    }
    val f2: DateFormat = SimpleDateFormat("hh:mm a")
    return f2.format(d)
}

@RequiresApi(Build.VERSION_CODES.M)
fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
    }
    return false
}


