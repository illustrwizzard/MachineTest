package com.hanashi.shoppingcart.ui.itemlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hanashi.shoppingcart.data.model.Item
import com.hanashi.shoppingcart.databinding.ItemRowBinding

class ItemAdapter(
    private val onAddClick: (Item) -> Unit
) : ListAdapter<Item, ItemAdapter.ItemViewHolder>(DiffCallback()) {

    inner class ItemViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.tvName.text = item.itemName
            binding.tvPrice.text = "₹%.2f".format(item.sellingPrice)
            binding.tvTax.text = "Tax: %.1f%%".format(item.taxPercentage)
            binding.btnAdd.setOnClickListener { onAddClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(old: Item, new: Item) = old.itemID == new.itemID
        override fun areContentsTheSame(old: Item, new: Item) = old == new
    }
}