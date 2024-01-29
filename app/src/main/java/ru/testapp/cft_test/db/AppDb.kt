package ru.testapp.cft_test.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.testapp.cft_test.dao.DaoUser
import ru.testapp.cft_test.entity.EntityUser
import ru.testapp.cft_test.typeConverters.TypeConverterUser

@Database(entities = [EntityUser::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverterUser::class)
abstract class AppDb : RoomDatabase() {
    abstract fun userDao(): DaoUser
}