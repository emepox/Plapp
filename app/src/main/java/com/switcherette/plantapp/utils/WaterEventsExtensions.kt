package com.switcherette.plantapp.utils

import com.switcherette.plantapp.data.WaterEvent

fun addAndCalculateNextWaterEvents(events: List<WaterEvent>): List<WaterEvent> {
    val newWaterEvents: MutableList<WaterEvent> = mutableListOf<WaterEvent>().apply {
        addAll(events)
    }

    events.forEach {

        for (i in 1..100) {
            val nextEvent = WaterEvent(
                it.id + i,
                it.plantId,
                it.repeatStart + it.repeatInterval * i,
                it.repeatInterval
            )
            newWaterEvents.add(nextEvent)
        }
    }
    return newWaterEvents
}

fun filterEventsByTime(
    newEvents: List<WaterEvent>?,
    startTime: Long,
    endTime: Long
): List<WaterEvent> {
    val filterEvents = newEvents!!.filter {
        it.repeatStart in startTime..endTime
    }
    return filterEvents
}