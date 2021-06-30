package com.example.passwordmanager.screens.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanager.R
import com.example.passwordmanager.models.Note
import com.google.android.material.card.MaterialCardView
import java.util.*
import kotlin.collections.ArrayList


class ItemRecyclerViewAdapter(
    private val onClick: (Note) -> Unit,
    private val emptySearchListCallback: (Boolean) -> Unit,
    private var currentList: MutableList<Note> = mutableListOf(),
) : RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder>(),
    Filterable {

    var itemFilteredList: MutableList<Note>

    init {
        itemFilteredList = currentList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): ItemViewHolder = ItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false),
        ::onItemClick
    )

    private fun onItemClick(position: Int) {
        onClick(itemFilteredList[position])
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (itemFilteredList.isNotEmpty()) {
            holder.bind(itemFilteredList[position])
        } else {
            holder.bind(currentList[position])
        }
    }

    inner class ItemViewHolder(
        itemView: View,
        private val onItemClick: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvSiteUrl = itemView.findViewById<TextView>(R.id.tvSiteUrl)
        private val tvLogin = itemView.findViewById<TextView>(R.id.tvLogin)
        private val ivIcon = itemView.findViewById<TextView>(R.id.ivIcon)
        private val itemCard = itemView.findViewById<MaterialCardView>(R.id.itemCard)


        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }


        @Suppress("DEPRECATION")
        fun bind(item: Note) {
            tvSiteUrl.text = item.siteUrl
            tvLogin.text = item.login
            ivIcon.text = setImageSymbol(item.siteUrl)
            itemCard.setCardBackgroundColor(item.color)
        }
    }


    private fun setImageSymbol(name: String): String {
        return name.substring(0, 1)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString().lowercase(Locale.ROOT)
                if (charSearch.isEmpty()) {
                    itemFilteredList = currentList
                } else {
                    val resultList = ArrayList<Note>()
                    for (item in currentList) {
                        if (item.siteUrl.lowercase(Locale.ROOT)
                                .contains(charSearch) || item.login.lowercase(
                                Locale.ROOT
                            ).contains(charSearch)
                        ) {
                            resultList.add(item)
                        }
                        itemFilteredList = resultList
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = itemFilteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemFilteredList = results?.values as ArrayList<Note>
                if (itemFilteredList.count() == 0) {
                    emptySearchListCallback(true)
                } else {
                    emptySearchListCallback(false)
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return if (itemFilteredList.isNotEmpty()) {
            itemFilteredList.size
        } else {
            currentList.size
        }
    }


}
