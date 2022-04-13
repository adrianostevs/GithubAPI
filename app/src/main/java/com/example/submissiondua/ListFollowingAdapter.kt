package com.example.submissiondua

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissiondua.response.FollowingResponseItem
import com.example.submissiondua.databinding.ListFollowingBinding

class ListFollowingAdapter:
    RecyclerView.Adapter<ListFollowingAdapter.ListViewHolder>() {
    private val listFollowing = ArrayList<FollowingResponseItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListFollowingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    fun setData(followings:List<FollowingResponseItem>){
        listFollowing.clear()
        listFollowing.addAll(followings)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(var binding: ListFollowingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listFollowing: FollowingResponseItem){
            binding.apply{
                Glide.with(itemView)
                    .load(listFollowing.avatarUrl)
                    .circleCrop()
                    .into(imgAvatar)
                tvUsername.text = listFollowing.login
            }
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollowing[position])
    }

    override fun getItemCount(): Int = listFollowing.size
}