package com.drozdova.catapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.drozdova.catapi.data.Cat
import com.drozdova.catapi.databinding.RvItemCatBinding

class CatAdapter(context: Context, private var clickListener: OnCatItemClickListener,) : PagingDataAdapter<Cat, CatViewHolder>(CatDiffItemCallBack){

    private val layoutInflater : LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemCatBinding.inflate(layoutInflater, parent, false)
        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

//    fun addItems(newItems: List<Cat>) {
//        items.addAll(newItems)
//        notifyDataSetChanged()
//    }
}

class CatViewHolder(private val binding: RvItemCatBinding) : RecyclerView.ViewHolder(binding.root) {
    private val imvCat = binding.imvCat
    fun bind(cat: Cat?, action: OnCatItemClickListener) {
        if (cat != null) {
            imvCat.setOnClickListener { action.onItemClick(cat) }
            imvCat.load(cat.url) {
                crossfade(true)
            }
        }
    }
}

private object CatDiffItemCallBack : DiffUtil.ItemCallback<Cat>() {
    override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
        return oldItem.id == newItem.id
    }

}

interface OnCatItemClickListener {
    fun onItemClick(cat: Cat)
}