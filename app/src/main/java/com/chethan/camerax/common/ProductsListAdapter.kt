package com.chethan.camerax.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.chethan.camerax.AppExecutors
import com.chethan.camerax.R
import com.chethan.camerax.databinding.ProductItemBinding
import com.chethan.camerax.model.Products


class ProductsListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors

) : DataBoundListAdapter<Products, ProductItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.name == newItem.name
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ProductItemBinding {
        val binding = DataBindingUtil.inflate<ProductItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.product_item,
            parent,
            false,
            dataBindingComponent
        )

        return binding
    }

    override fun bind(binding: ProductItemBinding, item: Products) {
        binding.products = item
    }
}
