package com.switcherette.plantapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileInputStream
import java.util.Base64.getEncoder


@RequiresApi(Build.VERSION_CODES.O)
fun convertToBase64(fileString: String): String {
    val file = File(fileString)
    val fis = FileInputStream(file)
    return getEncoder().encodeToString(fis.readBytes())
}


