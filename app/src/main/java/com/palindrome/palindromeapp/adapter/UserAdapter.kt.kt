package com.palindrome.palindromeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.palindrome.palindromeapp.databinding.ItemUserBinding
import com.palindrome.palindromeapp.model.User

class UserAdapter(private val onUserClick: (User) -> Unit) :
    ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val adapterPosition = absoluteAdapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onUserClick(getItem(adapterPosition))
                }
            }
        }

        fun bind(user: User) {
            binding.tvUserName.text = user.fullName
            binding.tvUserEmail.text = user.email

            // Using Glide as specified in the tech stack
            Glide.with(binding.root.context)
                .load(user.avatar)
                .into(binding.ivUserAvatar)
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}