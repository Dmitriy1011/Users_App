package ru.testapp.cft_test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.testapp.cft_test.databinding.CardUserBinding
import ru.testapp.cft_test.dto.User
import ru.testapp.cft_test.utils.load

interface OnIteractionListener {
    fun onDetails(user: User) {}
}

class AdapterUsers(
    private val listener: OnIteractionListener
) : PagingDataAdapter<User, AdapterUsers.ViewHolderUser>(DiffCallBackUser()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderUser {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderUser(
            CardUserBinding.inflate(inflater, parent, false),
            listener = listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolderUser, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    class ViewHolderUser(
        private val binding: CardUserBinding,
        private val listener: OnIteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.apply {
                val url = user.picture?.thumbnail.toString()
                userPhoto.load(url)

                root.setOnClickListener {
                    listener.onDetails(user)
                }

                userId.text = user.userId?.value
                userTitle.text = user.name?.title
                userFirstName.text = user.name?.first
                userLastName.text = user.name?.last

                userPhone.text = user.phone
                val address =
                    "${user.location.street?.number}, ${user.location.street?.name}, ${user.location.city}, ${user.location.state}, ${user.location.country}, ${user.location.postcode}, ${user.location.coordinates?.latitude}/${user.location.coordinates?.longitude}, ${user.location.timeZone?.offset}/${user.location.timeZone?.description} "
                userAddress.text = address
            }
        }
    }

    class DiffCallBackUser : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            if (oldItem::class != newItem::class) {
                return false
            }

            return oldItem.userId?.value == newItem.userId?.value
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem

    }
}