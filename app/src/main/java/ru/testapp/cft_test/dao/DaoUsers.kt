package ru.testapp.cft_test.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.testapp.cft_test.entity.EntityUser

@Dao
interface DaoUser {
    @Transaction
    @Query("SELECT * FROM EntityUser ORDER BY initialId DESC")
    fun getPagingSource(): PagingSource<Int, EntityUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: List<EntityUser>)
}