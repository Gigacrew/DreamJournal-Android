package com.gigacrew.dreamjournal

import androidx.room.TypeConverter

class ArrayListConverter {

    @TypeConverter
    fun fromArrayList(list: ArrayList<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toArrayList(data: String): ArrayList<String> {
        return ArrayList(data.split(","))
    }
}
