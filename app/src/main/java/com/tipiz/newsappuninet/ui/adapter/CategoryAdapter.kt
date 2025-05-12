package com.tipiz.newsappuninet.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tipiz.core.domain.model.CategoryModel
import com.tipiz.newsappuninet.R
import com.tipiz.newsappuninet.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val categories: List<CategoryModel>,
    private val listener: OnAdapterListener
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val items = arrayListOf<TextView>()

    class ViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.category.text = category.name

        items.add(holder.binding.category)
        holder.itemView.setOnClickListener {
            listener.onClick(category)
            setColor(holder.binding.category)
        }
        setColor(items[0])
    }

    override fun getItemCount() = categories.size

    private fun setColor(textView: TextView) {
        items.forEach { p -> p.setBackgroundResource(R.color.white) }
        textView.setBackgroundResource(android.R.color.darker_gray)
    }

    interface OnAdapterListener {
        fun onClick(categories: CategoryModel)
    }
}