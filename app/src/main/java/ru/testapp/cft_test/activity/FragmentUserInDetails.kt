package ru.testapp.cft_test.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.testapp.cft_test.R
import ru.testapp.cft_test.activity.FragmentsUsers.Companion.userId
import ru.testapp.cft_test.databinding.FragmentUserInDetailsBinding
import ru.testapp.cft_test.utils.load
import ru.testapp.cft_test.viewModel.ViewModelUsers

@AndroidEntryPoint
class FragmentUserInDetails : Fragment(R.layout.fragment_user_in_details) {
    private val viewModel: ViewModelUsers by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentUserInDetailsBinding.bind(view)

        val arg = arguments?.userId

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.usersData.collectLatest { users ->
                    users?.map { arg?.let { strId -> it.userId?.copy(value = strId) } }
                    users?.find { it.userId?.value == arg }?.let { user ->
                        binding.apply {
                            userDetailsPhoto.load(user.picture?.thumbnail.toString())
                            userDetailsId.text = user.userId?.value
                            userDetailsRegisteredDate.text =
                                user.registered?.registeredDate
                            userDetailsTitle.text = user.name?.title
                            userDetailsFirstName.text = user.name?.first
                            userDetailsLastName.text = user.name?.last
                            val address =
                                "${user.location.street?.number}, ${user.location.street?.name}, ${user.location.city}, ${user.location.state}, ${user.location.country}, ${user.location.postcode}, ${user.location.coordinates?.latitude}/${user.location.coordinates?.longitude}, ${user.location.timeZone?.offset}/${user.location.timeZone?.description} "
                            userDetailsAddress.text = address
                            userDetailsPhone.text = user.phone
                            userDetailsEmail.text = user.email
                            userDetailsLogin.text = user.login?.username
                            userDetailsAge.text = user.dob?.dobAge.toString()
                            userDetailsNat.text = user.nat
                            val coords =
                                "${user.location.coordinates?.latitude} / ${user.location.coordinates?.longitude}"
                            userDetailsCorrdinates.text = coords

                            userDetailsEmail.setOnClickListener {
                                val intent = Intent(
                                    Intent.ACTION_SENDTO,
                                    Uri.fromParts("mailto", user.email, null)
                                )

                                val shareIntent = Intent.createChooser(
                                    intent,
                                    getString(R.string.share_email)
                                )
                                startActivity(shareIntent)
                            }

                            userDetailsPhone.setOnClickListener {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(user.phone))
                                startActivity(intent)
                            }

                            userDetailsCorrdinates.setOnClickListener {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("geo:" + user.location.coordinates?.latitude + ", " + user.location.coordinates?.longitude)
                                )
                                startActivity(intent)
                            }
                        }
                    }
                }
            }
        }
    }
}