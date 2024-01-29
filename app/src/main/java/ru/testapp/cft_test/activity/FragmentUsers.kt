package ru.testapp.cft_test.activity

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.testapp.cft_test.R
import ru.testapp.cft_test.adapter.AdapterUsers
import ru.testapp.cft_test.adapter.OnIteractionListener
import ru.testapp.cft_test.databinding.FragmentUsersBinding
import ru.testapp.cft_test.dto.User
import ru.testapp.cft_test.utils.UserIdArg
import ru.testapp.cft_test.viewModel.ViewModelUsers

@AndroidEntryPoint
class FragmentsUsers : Fragment(R.layout.fragment_users) {

    private val viewModel: ViewModelUsers by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentUsersBinding.bind(view)

        val adapter = AdapterUsers(object : OnIteractionListener {
            override fun onDetails(user: User) {
                findNavController().navigate(
                    R.id.action_fragmentsUsers_to_fragmentUserInDetails,
                    Bundle().apply {
                        userId = user.userId?.value
                    })
            }
        })

        binding.usersList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.users.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collectLatest { state ->
                    binding.swipeRefresh.isRefreshing =
                        state.refresh is LoadState.Loading
                    binding.errorGroup.isVisible =
                        state.refresh is LoadState.Error
                    binding.progressBar.isVisible =
                        state.refresh is LoadState.Loading
                    if (state.refresh is LoadState.Error) {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.data_loading_error),
                            Snackbar.LENGTH_LONG
                        ).setAction(
                            getString(R.string.retry)
                        ) {
                            adapter.refresh()
                        }.show()
                    }
                }
            }
        }

        binding.retryButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.users.collectLatest {
                        adapter.submitData(it)
                    }
                }
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }

        binding.retryButton.setOnClickListener {
            adapter.refresh()
        }
    }

    companion object {
        var Bundle.userId: String? by UserIdArg
    }
}