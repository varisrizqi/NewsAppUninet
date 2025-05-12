package com.tipiz.newsappuninet.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tipiz.core.domain.model.DataHeadLinesNews
import com.tipiz.newsappuninet.R
import com.tipiz.newsappuninet.databinding.ItemNewsListBinding
import com.tipiz.newsappuninet.uitls.DateUtil

class NewsAdapter(private val listener: OnPagingListener) :
    PagingDataAdapter<DataHeadLinesNews, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataHeadLinesNews>() {
            override fun areItemsTheSame(
                oldItem: DataHeadLinesNews,
                newItem: DataHeadLinesNews
            ): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(
                oldItem: DataHeadLinesNews,
                newItem: DataHeadLinesNews
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class NewsViewHolder(private val binding: ItemNewsListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val dateUtil = DateUtil()
        fun bind(data: DataHeadLinesNews) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.urlToImage)
                    .placeholder(R.drawable.thumbnail_load_product)
                    .error(R.drawable.thumbnail_load_product)
                    .into(imgNews)
                title.text = data.title
                publishedAt.text = dateUtil.dateFormat(data.publishedAt)


                itemView.setOnClickListener {
                    listener.onClick(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemNewsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        data?.let {
            (holder as NewsViewHolder).bind(it)
        }
    }

    interface OnPagingListener {
        fun onClick(news: DataHeadLinesNews)
    }
}

