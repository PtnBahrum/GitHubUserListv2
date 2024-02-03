package com.example.githubuserlistv2.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserlistv2.databinding.ItemUserBinding
import com.example.githubuserlistv2.model.User
import com.example.githubuserlistv2.ui.detail.DetailActivity

class UserAdapter(private val users : List<User>): RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (user : User) {
            binding.apply {
                with(user) {
                    itemView.setOnClickListener{
                        val intent = Intent(itemView.context, DetailActivity::class.java)
                        intent.putExtra("USERNAME", login)
                        itemView.context.startActivity(intent)
                    }
                    Glide.with(itemView)
                        .load(avatar_url)
                        .circleCrop()
                        .into(ivUser)
                    tvUsername.text = login
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

}