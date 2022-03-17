package com.switcherette.plantapp.utils

import android.content.Context
import android.net.Uri
import android.util.Base64
import java.io.File
import java.io.FileInputStream



fun convertToBase64(context: Context, uri: Uri?): String {
    val inS = context.contentResolver.openInputStream(uri!!)
    return Base64.encodeToString(inS!!.readBytes(), Base64.DEFAULT)
}


