package com.example.passwordmanager.screens.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanager.R
import com.example.passwordmanager.models.Note

class ItemRecyclerViewAdapter(
    private val onClick: (Note) -> Unit
) : ListAdapter<Note, ItemRecyclerViewAdapter.ItemViewHolder>(ItemAdapterDiffCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ItemViewHolder = ItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.record_list_item, parent, false),
        ::onItemClick
    )

    private fun onItemClick(position: Int) {
        onClick(getItem(position))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(
        itemView: View,
        private val onItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvSiteUrl = itemView.findViewById<TextView>(R.id.tvSiteUrl)
        private val tvLogin = itemView.findViewById<TextView>(R.id.tvLogin)


        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        fun bind(item: Note) {
            tvSiteUrl.text = item.siteUrl
            tvLogin.text = item.login
        }
    }
}

class ItemAdapterDiffCallBack : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.login == newItem.login && oldItem.siteUrl == newItem.siteUrl
                && oldItem.password == newItem.password
    }

}