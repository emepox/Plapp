package com.switcherette.plantapp.utils

import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.data.WaterEvent
import java.util.*
import java.util.concurrent.TimeUnit

fun createWaterEvent(userPlant: UserPlant): WaterEvent {
    val today = Calendar.getInstance().let {
        it[Calendar.HOUR_OF_DAY] = 12
        it[Calendar.MINUTE] = 0
        it[Calendar.SECOND] = 0
        it[Calendar.MILLISECOND] = 0
        it.timeInMillis
    }
    val repeatInterval = TimeUnit.DAYS.toMillis(userPlant.water.toLong())
    val repeatStart = today + repeatInterval
    return WaterEvent(
        plantId = userPlant.id,
        repeatStart = repeatStart,
        repeatInterval = repeatInterval
    )
}