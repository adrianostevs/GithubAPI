package com.example.submissiondua

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissiondua.response.FollowersResponseItem
import com.example.submissiondua.databinding.ListFollowersBinding

class ListFollowersAdapter() :
    RecyclerView.Adapter<ListFollowersAdapter.ListViewHolder>() {
    private val listFollowers = ArrayList<FollowersResponseItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListFollowersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    fun setData(followers:List<FollowersResponseItem>){
        listFollowers.clear()
        listFollowers.addAll(followers)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(var binding: ListFollowersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listFollowers: FollowersResponseItem){
            binding.apply{
                Glide.with(itemView)
                    .load(listFollowers.avatarUrl)
                    .circleCrop()
                    .into(imgAvatar)
                tvUsername.text = listFollowers.login
            }
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollowers[position])
    }

    override fun getItemCount(): Int = listFollowers.size
}