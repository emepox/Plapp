package com.switcherette.plantapp.utils

import android.content.Context
import android.util.Base64
import java.io.File
import java.io.FileInputStream



fun convertToBase64(fileString: String): String {

    val file = File(fileString)
    val fis = FileInputStream(file)
    return Base64.encodeToString(fis.readBytes(), Base64.DEFAULT)

}


