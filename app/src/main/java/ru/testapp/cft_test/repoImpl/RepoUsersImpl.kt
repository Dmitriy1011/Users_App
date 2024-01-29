package ru.testapp.cft_test.repoImpl

import android.accounts.NetworkErrorException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.testapp.cft_test.api.ApiServiceUsers
import ru.testapp.cft_test.dao.DaoUser
import ru.testapp.cft_test.dto.Results
import ru.testapp.cft_test.dto.User
import ru.testapp.cft_test.entity.EntityUser
import ru.testapp.cft_test.paging.RemoteMediatorUser
import ru.testapp.cft_test.repo.RepoUsers
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoUsersImpl @Inject constructor(
    private val apiService: ApiServiceUsers,
    private val daoUser: DaoUser
) : RepoUsers {

    @OptIn(ExperimentalPagingApi::class)
    override val data: Flow<PagingData<User>> = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = daoUser::getPagingSource,
        remoteMediator = RemoteMediatorUser(apiServiceUsers = apiService, dao = daoUser)
    ).flow.map { it.map(EntityUser::toDto) }

    override suspend fun getAllUsers(): Results {
        try {
            val response = apiService.getAllUsers()
            if (!response.isSuccessful) {
                throw RuntimeException(response.message())
            }

            return response.body() ?: throw RuntimeException(response.message())
        } catch (e: IOException) {
            throw NetworkErrorException(e)
        }
    }
}