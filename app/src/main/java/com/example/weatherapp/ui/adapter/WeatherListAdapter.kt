package com.example.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.BR
import com.example.weatherapp.data.model.ListItem
import com.example.weatherapp.databinding.ListItemBinding

class WeatherListAdapter(private var items: ArrayList<ListItem>) :
    RecyclerView.Adapter<WeatherListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ListItem = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    fun add(item: ListItem) {
        if (!items.contains(item)) {
            items.add(item)
            notifyItemInserted(items.lastIndex)
        }
    }

    class ViewHolder(private val itemBinding: ListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: ListItem) {
            itemBinding.setVariable(BR.item, item)
            itemBinding.executePendingBindings()
        }
    }
}