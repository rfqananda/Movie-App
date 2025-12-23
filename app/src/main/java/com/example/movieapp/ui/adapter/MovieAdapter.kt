package com.example.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.ui.model.DiscoverMovieUiModel

class MovieAdapter : ListAdapter<DiscoverMovieUiModel, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    private var onItemClickListener: ((DiscoverMovieUiModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (DiscoverMovieUiModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    inner class MovieViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.invoke(getItem(position))
                }
            }
        }

        fun bind(movie: DiscoverMovieUiModel) {
            binding.apply {
                tvTitle.text = movie.title
                tvOverview.text = movie.overview
                tvReleaseDate.text = movie.releaseDate
                tvRating.text = itemView.context.getString(
                    R.string.movie_rating,
                    movie.rating
                )


                Glide.with(itemView.context)
                    .load(movie.posterUrl)
                    .placeholder(R.drawable.ic_placeholder_poster)
                    .error(R.drawable.ic_placeholder_poster)
                    .into(ivPoster)
            }
        }
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<DiscoverMovieUiModel>() {
        override fun areItemsTheSame(
            oldItem: DiscoverMovieUiModel,
            newItem: DiscoverMovieUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DiscoverMovieUiModel,
            newItem: DiscoverMovieUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}
