package com.switcherette.plantapp.data.repositories

import android.content.Context

class SharedPrefsRepository(private val context: Context) {
    val id = "user prefs"
    fun readBoolean(key: String): Boolean {
        val sharedPrefs = context.getSharedPreferences(id, Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean(key, true)
    }
    fun writeBoolean(key: String, value: Boolean){
        val sharedPrefs = context.getSharedPreferences(id, Context.MODE_PRIVATE)
        sharedPrefs.edit().putBoolean(key, value).apply()
    }
}