package com.hanashi.shoppingcart.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hanashi.shoppingcart.data.model.CartItem
import com.hanashi.shoppingcart.databinding.CartRowBinding

class CartAdapter(
    private val onIncrease: (CartItem) -> Unit,
    private val onDecrease: (CartItem) -> Unit,
    private val onDeleteClick: (CartItem) -> Unit
) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(DiffCallback()) {

    inner class CartViewHolder(private val binding: CartRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            binding.tvName.text = item.itemName
            binding.tvQty.text = item.quantity.toString()
            binding.tvPrice.text = "₹%.2f".format(item.sellingPrice * item.quantity)
            binding.tvTax.text = "Tax: %.1f%%".format(item.taxPercentage)

            binding.btnIncrease.setOnClickListener { onIncrease(item) }
            binding.btnDecrease.setOnClickListener { onDecrease(item) }
            binding.btnRemove.setOnClickListener { onDeleteClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(old: CartItem, new: CartItem) = old.id == new.id
        override fun areContentsTheSame(old: CartItem, new: CartItem) = old == new
    }
}
