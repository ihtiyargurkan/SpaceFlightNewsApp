package com.example.spaceflightnewsapp.ui.newslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.spaceflightnewsapp.R
import com.example.spaceflightnewsapp.data.model.Article
import com.example.spaceflightnewsapp.databinding.ItemArticleBinding
import com.example.spaceflightnewsapp.util.DateUtils

class NewsListAdapter(
    private val onItemClick: (Article) -> Unit
) : ListAdapter<Article, NewsListAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ArticleViewHolder(
        private val binding: ItemArticleBinding,
        private val onItemClick: (Article) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.textTitle.text = article.title
            binding.textSummary.text = article.summary.trim()
            binding.textPublishedDate.text = DateUtils.formatPublishedDate(article.publishedAt)

            article.imageUrl?.let { url ->
                binding.imageArticle.load(url) {
                    placeholder(R.drawable.ic_launcher_foreground)
                    error(R.drawable.ic_launcher_foreground)
                }
            } ?: run {
                binding.imageArticle.setImageResource(R.drawable.ic_launcher_foreground)
            }

            binding.root.setOnClickListener { onItemClick(article) }
        }
    }

    private class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem
    }
}
