package com.example.androidtask.presentation.main.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.androidtask.common.utils.toArrayList
import com.example.androidtask.databinding.ItemProductLayoutBinding
import com.example.androidtask.domain.mappers.Product
import javax.inject.Inject

class ProductAdapter @Inject constructor(
    private val glide: RequestManager,
    private val context: Context,
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var products: List<Product>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        //
        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product,
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)

    inner class ProductViewHolder(val itemBinding: ItemProductLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindData(item: Product) {
             glide.load(item.thumbnail).into(itemBinding.productImg)
            itemBinding.productPrice.text = "LE ${item.price}"
            itemBinding.productBrand.text = item.brand
            itemBinding.productName.text = item.title
            itemBinding.productRating.text = item.rating.toString()
            itemBinding.productDiscountTv.text = "${item.discountPercentage}%"
            setupActions(item)
        }

        private fun setupActions(item: Product) {
            itemBinding.root.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(item)
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemBinding =
            ItemProductLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(itemBinding)
    }
    private var onItemClickListener: ((Product) -> Unit)? = null

    fun setOnItemClickListener(listener: (Product) -> Unit) {
        onItemClickListener = listener
    }
    private var onAddCartClickListener: ((Product) -> Unit)? = null

    fun setOnAddCartClickListener(listener: (Product) -> Unit) {
        onAddCartClickListener = listener
    }
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product=products[position]
        holder.apply {
            bindData(product)
        }
    }

    override fun getItemCount(): Int =products.size
}