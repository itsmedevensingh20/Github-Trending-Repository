package com.devendra.devendra_demo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devendra.devendra_demo.databinding.ItemTrendingsBinding
import com.devendra.devendra_demo.response.ListResponse
import kotlinx.android.synthetic.main.item_trendings.view.*

class ListAdapter : PagingDataAdapter<ListResponse, ListAdapter.ViewHolder>(diffUtil) {

    companion object {
        val diffUtil =
            object : DiffUtil.ItemCallback<ListResponse>() {
                override fun areItemsTheSame(
                    oldItem: ListResponse,
                    newItem: ListResponse
                ): Boolean = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: ListResponse,
                    newItem: ListResponse
                ): Boolean = oldItem == newItem
            }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { list ->
            holder.itemView.tag = list
            holder.itemView.name.text = list?.name
            holder.itemView.language.text = list?.language
            Glide.with(holder.itemView.context)
                .load(list?.owner?.avatar_url)
                .centerCrop()
                .into(holder.itemView.avatar)


            holder.itemView.setOnClickListener {
                Toast.makeText(
                    holder.itemView.context,
                    "${list?.name} is selected",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTrendingsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class ViewHolder(private val binding: ItemTrendingsBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

}