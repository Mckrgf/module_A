package com.yaobing.framemvpproject.mylibrary

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yaobing.framemvpproject.mylibrary.data.RepoData

class RepoAdapter  : PagingDataAdapter<RepoData, RepoAdapter.ViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<RepoData>() {
            override fun areItemsTheSame(oldItem: RepoData, newItem: RepoData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RepoData, newItem: RepoData): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_name)
//        val description: TextView = itemView.findViewById(R.id.description_text)
//        val starCount: TextView = itemView.findViewById(R.id.star_count_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = getItem(position)
        if (repo != null) {
            holder.name.text = repo.name
//            holder.description.text = repo.description
//            holder.starCount.text = repo.starCount.toString()
        }
    }

}