package ru.testapp.cft_test.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.testapp.cft_test.repo.RepoUsers
import ru.testapp.cft_test.repoImpl.RepoUsersImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface ModuleRepo {

    @Binds
    @Singleton
    fun bindRepo(
        repo: RepoUsersImpl
    ): RepoUsers
}