package ru.testapp.cft_test.repo

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.testapp.cft_test.dto.Results
import ru.testapp.cft_test.dto.User

interface RepoUsers {
    val data: Flow<PagingData<User>>
    suspend fun getAllUsers(): Results
}