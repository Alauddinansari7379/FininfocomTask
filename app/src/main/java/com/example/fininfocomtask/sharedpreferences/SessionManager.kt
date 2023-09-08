package com.example.ehcf.sharedpreferences

import android.content.Context
import android.preference.PreferenceManager

class SessionManager(context: Context?) {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {

        private const val IS_LOGIN = "islogin"
        private const val BLOOD_GROUP="blood_group"

    }

    var isLogin: Boolean
        get() = prefs.getBoolean(IS_LOGIN, false)
        set(isLogin) {
            prefs.edit().putBoolean(IS_LOGIN, isLogin).apply()
        }

    fun logout() {
        prefs.edit().clear().apply()
    }



}