package uz.mrsolijon.movieapp.data.local.db.converter

import uz.mrsolijon.movieapp.data.local.db.entity.CastEntity
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {
    @TypeConverter
    fun fromList(value: List<String>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toList(value: String): List<String> {
        return value.split(",")
    }


    @TypeConverter
    fun fromCastList(value: List<CastEntity>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCastList(value: String): List<CastEntity> {
        val type = object : TypeToken<List<CastEntity>>() {}.type
        return gson.fromJson(value, type)
    }

    companion object {
        val gson = Gson()
    }
}