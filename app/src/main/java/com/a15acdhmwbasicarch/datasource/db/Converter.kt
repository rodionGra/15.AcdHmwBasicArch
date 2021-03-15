package com.a15acdhmwbasicarch.datasource.db

import androidx.room.TypeConverter
import com.a15acdhmwbasicarch.datasource.model.AddedFrom

class Converter {

    @TypeConverter
    fun intToAddedFrom(value: Int) = enumValues<AddedFrom>()[value]

    @TypeConverter
    fun addedFromToInt(value: AddedFrom) = value.ordinal
}
