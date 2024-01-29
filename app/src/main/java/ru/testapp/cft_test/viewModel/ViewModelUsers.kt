package ru.testapp.cft_test.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.testapp.cft_test.dto.User
import ru.testapp.cft_test.repo.RepoUsers
import javax.inject.Inject

@HiltViewModel
class ViewModelUsers @Inject constructor(
    repo: RepoUsers
) : ViewModel() {
    private val cached: Flow<PagingData<User>> = repo.data.cachedIn(viewModelScope)
    val users: Flow<PagingData<User>> = cached.flowOn(Dispatchers.Default)

    val usersData = flow {
        val list = repo.getAllUsers().results
        emit(list)
    }
}