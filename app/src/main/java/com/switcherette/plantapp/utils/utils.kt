package com.switcherette.plantapp.utils

import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileInputStream
import java.util.Base64.getEncoder


fun convertToBase64(fileString: String): String {
    val file = File(fileString)
    val fis = FileInputStream(file)
    return Base64.encodeToString(fis.readBytes(), Base64.DEFAULT)
}


