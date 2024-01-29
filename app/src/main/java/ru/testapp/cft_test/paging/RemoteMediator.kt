package ru.testapp.cft_test.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import ru.testapp.cft_test.api.ApiServiceUsers
import ru.testapp.cft_test.dao.DaoUser
import ru.testapp.cft_test.entity.EntityUser
import ru.testapp.cft_test.errors.ApiError


@OptIn(ExperimentalPagingApi::class)
class RemoteMediatorUser(
    private val apiServiceUsers: ApiServiceUsers,
    private val dao: DaoUser
) : RemoteMediator<Int, EntityUser>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EntityUser>
    ): MediatorResult {
        try {
            val response = when (loadType) {
                LoadType.REFRESH -> apiServiceUsers.getUsersLatest()
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> return MediatorResult.Success(true)
            }

            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body()?.results ?: throw ApiError(
                response.code(),
                response.message()
            )

            dao.insert(body.map(EntityUser::toEntity))

            return MediatorResult.Success(body.isEmpty())

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}