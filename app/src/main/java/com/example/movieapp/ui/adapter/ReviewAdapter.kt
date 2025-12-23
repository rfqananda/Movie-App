package com.example.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ItemReviewBinding
import com.example.movieapp.ui.model.MovieReviewUiModel

class ReviewAdapter : ListAdapter<MovieReviewUiModel, ReviewAdapter.ReviewViewHolder>(ReviewDiffCallback()) {

    private var onReadMoreClickListener: ((MovieReviewUiModel) -> Unit)? = null

    fun setOnReadMoreClickListener(listener: (MovieReviewUiModel) -> Unit) {
        onReadMoreClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    inner class ReviewViewHolder(
        private val binding: ItemReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.tvReadMore.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onReadMoreClickListener?.invoke(getItem(position))
                }
            }
        }

        fun bind(review: MovieReviewUiModel) {
            binding.apply {
                tvAuthor.text = review.author
                tvCreatedAt.text = review.createdAt
                tvContent.text = review.content

                val firstLetter = review.author.firstOrNull()?.uppercase() ?: "A"
                tvAuthorAvatar.text = firstLetter

                tvReadMore.isVisible = review.content.length > 200
            }
        }
    }

    private class ReviewDiffCallback : DiffUtil.ItemCallback<MovieReviewUiModel>() {
        override fun areItemsTheSame(
            oldItem: MovieReviewUiModel,
            newItem: MovieReviewUiModel
        ): Boolean {
            return oldItem.author == newItem.author && oldItem.createdAt == newItem.createdAt
        }

        override fun areContentsTheSame(
            oldItem: MovieReviewUiModel,
            newItem: MovieReviewUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}
