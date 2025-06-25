package com.hanashi.shoppingcart.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hanashi.shoppingcart.R
import com.hanashi.shoppingcart.data.db.AppDatabase
import com.hanashi.shoppingcart.data.repository.Repository
import com.hanashi.shoppingcart.databinding.FragmentCartBinding
import com.hanashi.shoppingcart.utils.ViewModelFactory

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CartViewModel
    private lateinit var adapter: CartAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        val db = AppDatabase.getDatabase(requireContext())
        val repository = Repository(db.cartDao())
        val factory = ViewModelFactory(repository, requireContext())
        viewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]

        adapter = CartAdapter(
            onIncrease = { item ->
                item.quantity += 1
                viewModel.updateItem(item)
            },
            onDecrease = { item ->
                if (item.quantity > 1) {
                    item.quantity -= 1
                    viewModel.updateItem(item)
                } else {
                    viewModel.deleteItem(item)
                }
            },
            onDeleteClick = { item ->
                viewModel.deleteItem(item)
            }
        )

        binding.recyclerViewCart.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCart.adapter = adapter

        viewModel.cartItems.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)

            val subtotal = viewModel.getSubtotal(items)
            val tax = viewModel.getTax(items)
            val total = viewModel.getTotal(items)

            binding.tvSubtotal.text = "Subtotal: ₹%.2f".format(subtotal)
            binding.tvTax.text = "Tax: ₹%.2f".format(tax)
            binding.tvTotal.text = "Total: ₹%.2f".format(total)
        }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnClearCart.setOnClickListener {
            viewModel.clearCart()
        }

        return binding.root
    }
}