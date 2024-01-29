package ru.testapp.cft_test.typeConverters
//
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.testapp.cft_test.entity.LocationEmbeddable

private val gson = Gson()
private val forLocation = object : TypeToken<LocationEmbeddable>() {}.type

class TypeConverterUser {

    @TypeConverter
    fun fromLocationToString(value: LocationEmbeddable): String = gson.toJson(value)

    @TypeConverter
    fun fromStringToLocation(value: String): LocationEmbeddable = gson.fromJson(value, forLocation)
}