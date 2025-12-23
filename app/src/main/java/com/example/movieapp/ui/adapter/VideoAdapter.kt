package com.example.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemVideoBinding
import com.example.movieapp.ui.model.MovieVideoUiModel

class VideoAdapter : ListAdapter<MovieVideoUiModel, VideoAdapter.VideoViewHolder>(VideoDiffCallback()) {

    private var onItemClickListener: ((MovieVideoUiModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (MovieVideoUiModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = getItem(position)
        holder.bind(video)
    }

    inner class VideoViewHolder(
        private val binding: ItemVideoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.invoke(getItem(position))
                }
            }
        }

        fun bind(video: MovieVideoUiModel) {
            binding.apply {
                tvVideoTitle.text = video.title

                Glide.with(itemView.context)
                    .load(video.thumbnailUrl)
                    .placeholder(R.drawable.ic_placeholder_video)
                    .error(R.drawable.ic_placeholder_video)
                    .into(ivThumbnail)
            }
        }
    }

    private class VideoDiffCallback : DiffUtil.ItemCallback<MovieVideoUiModel>() {
        override fun areItemsTheSame(
            oldItem: MovieVideoUiModel,
            newItem: MovieVideoUiModel
        ): Boolean {
            return oldItem.youtubeKey == newItem.youtubeKey
        }

        override fun areContentsTheSame(
            oldItem: MovieVideoUiModel,
            newItem: MovieVideoUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}
