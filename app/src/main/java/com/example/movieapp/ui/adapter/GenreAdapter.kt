package com.example.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ItemGenreBinding
import com.example.movieapp.ui.model.GenreUiModel

class GenreAdapter : ListAdapter<GenreUiModel, GenreAdapter.GenreViewHolder>(GenreDiffCallback()) {

    private var onItemClickListener: ((GenreUiModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (GenreUiModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemGenreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = getItem(position)
        holder.bind(genre)
    }

    inner class GenreViewHolder(
        private val binding: ItemGenreBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.invoke(getItem(position))
                }
            }
        }

        fun bind(genre: GenreUiModel) {
            binding.apply {
                tvGenreName.text = genre.name
                tvGenreIcon.text = genre.icon
            }
        }

    }

    private class GenreDiffCallback : DiffUtil.ItemCallback<GenreUiModel>() {
        override fun areItemsTheSame(oldItem: GenreUiModel, newItem: GenreUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GenreUiModel, newItem: GenreUiModel): Boolean {
            return oldItem == newItem
        }
    }
}
