package org.thesheeps.notetaking.data

import androidx.room.TypeConverter
import java.util.*

/**
 * Convert Timestamp to Date and vice versa
 */
class DateConverter {

    @TypeConverter
    fun timestampToDate(timestamp: Long): Date {
        return Date(timestamp)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}