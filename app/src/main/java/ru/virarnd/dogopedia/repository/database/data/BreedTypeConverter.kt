package ru.virarnd.dogopedia.repository.database.data

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class BreedTypeConverter {
    private val moshi = Moshi.Builder().build()


    private val stringList = Types.newParameterizedType(List::class.java, String::class.java)

    private val stringAdapter = moshi.adapter<List<String>>(stringList)

    @TypeConverter
    fun fromStringListToString(stringList: List<String>): String = stringAdapter.toJson(stringList)

    @TypeConverter
    fun fromStringToStringList(stringList: String): List<String> = stringAdapter.fromJson(stringList) ?: emptyList()



}
