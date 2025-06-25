package com.hanashi.shoppingcart.ui.itemlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hanashi.shoppingcart.R
import com.hanashi.shoppingcart.data.db.AppDatabase
import com.hanashi.shoppingcart.data.repository.Repository
import com.hanashi.shoppingcart.databinding.FragmentItemListBinding
import com.hanashi.shoppingcart.ui.cart.CartFragment
import com.hanashi.shoppingcart.utils.ViewModelFactory


class ItemListFragment : Fragment() {
    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ItemListViewModel
    private lateinit var adapter: ItemAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)

        val db = AppDatabase.getDatabase(requireContext())
        val repository = Repository(db.cartDao())
        val factory = ViewModelFactory(repository, requireContext())
        viewModel = ViewModelProvider(this, factory)[ItemListViewModel::class.java]

        adapter = ItemAdapter { item -> viewModel.addItemToCart(item) }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            it?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
        }

        binding.btnGoToCart.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CartFragment())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}