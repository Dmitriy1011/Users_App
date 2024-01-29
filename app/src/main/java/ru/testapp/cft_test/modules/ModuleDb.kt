package ru.testapp.cft_test.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.testapp.cft_test.dao.DaoUser
import ru.testapp.cft_test.db.AppDb
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ModuleDb {

    @Singleton
    @Provides
    fun provideAppDb(
        @ApplicationContext
        context: Context
    ): AppDb = Room.databaseBuilder(
        context, AppDb::class.java, "database-name"
    )

        .build()

    @Singleton
    @Provides
    fun provideUserDao(appDb: AppDb): DaoUser = appDb.userDao()
}