package com.switcherette.plantapp.data.repositories

import android.content.Context

object SharedPrefsRepository {
    val id = "user prefs"
    fun readBoolean(context: Context, key: String): Boolean {
        val sharedPrefs = context.getSharedPreferences(id, Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean(key, true)
    }
    fun writeBoolean(context: Context, key: String, value: Boolean){
        val sharedPrefs = context.getSharedPreferences(id, Context.MODE_PRIVATE)
        sharedPrefs.edit().putBoolean(key, value).apply()
    }
}