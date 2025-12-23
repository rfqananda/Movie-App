package com.example.movieapp.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.MovieAppApplication
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieDetailBinding
import com.example.movieapp.ui.adapter.ReviewAdapter
import com.example.movieapp.ui.adapter.VideoAdapter
import com.example.movieapp.ui.vm.MovieDetailViewModel
import com.example.registrationapp.core.Result
import javax.inject.Inject

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MovieDetailViewModel by viewModels { viewModelFactory }

    private val args: MovieDetailFragmentArgs by navArgs()

    private val videoAdapter = VideoAdapter()
    private val reviewAdapter = ReviewAdapter()

    private var isLoadingMoreReviews = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MovieAppApplication)
            .appComponent
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupListener()
        observeViewModel()
        loadData()
    }

    private fun setupView() {
        binding.rvVideos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = videoAdapter
        }

        binding.rvReviews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reviewAdapter
            addOnScrollListener(createScrollListener())
        }
    }

    private fun setupListener() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        videoAdapter.setOnItemClickListener { video ->
            openYoutube(requireContext(), video.youtubeKey)
        }

        reviewAdapter.setOnReadMoreClickListener { review ->
            viewModel.toggleReviewExpanded(review)
        }
    }

    private fun createScrollListener() = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            if (!isLoadingMoreReviews &&
                (visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                firstVisibleItemPosition >= 0) {
                viewModel.loadNextReviewPage()
            }
        }
    }

    private fun loadData() {
        viewModel.loadMovieDetail(args.id)
        viewModel.loadMovieVideos(args.id)
        viewModel.loadMovieReviews(args.id)
    }

    private fun observeViewModel() {
        viewModel.movieDetail.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Result.Success -> {
                    binding.progressBar.isVisible = false
                    val movie = result.data

                    binding.apply {
                        tvTitle.text = movie.title
                        tvRating.text = getString(R.string.movie_rating, movie.rating)
                        tvRuntime.text = getString(R.string.detail_runtime_format, movie.runtime)
                        tvGenres.text = movie.genres
                        tvOverview.text = movie.overview

                        Glide.with(requireContext())
                            .load(movie.backdropUrl)
                            .placeholder(R.drawable.ic_placeholder_backdrop)
                            .error(R.drawable.ic_placeholder_backdrop)
                            .into(ivBackdrop)

                        Glide.with(requireContext())
                            .load(movie.posterUrl)
                            .placeholder(R.drawable.ic_placeholder_poster)
                            .error(R.drawable.ic_placeholder_poster)
                            .into(ivPoster)
                    }
                }
                is Result.Error -> {
                    binding.progressBar.isVisible = false
                }
                else -> {}
            }
        }

        viewModel.movieVideos.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    // Bisa tambahkan loading indicator untuk videos jika perlu
                }
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        binding.tvVideosLabel.isVisible = false
                        binding.rvVideos.isVisible = false
                    } else {
                        binding.tvVideosLabel.isVisible = true
                        binding.rvVideos.isVisible = true
                        videoAdapter.submitList(result.data)
                    }
                }
                is Result.Error -> {
                    binding.tvVideosLabel.isVisible = false
                    binding.rvVideos.isVisible = false
                }
                else -> {}
            }
        }

        viewModel.movieReviews.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    // Bisa tambahkan loading indicator untuk reviews jika perlu
                }
                is Result.Success -> {

                    if (result.data.isEmpty()) {
                        binding.tvReviewsLabel.isVisible = false
                        binding.rvReviews.isVisible = false
                    } else {
                        binding.tvReviewsLabel.isVisible = true
                        binding.rvReviews.isVisible = true
                        reviewAdapter.submitList(result.data)
                    }
                }
                is Result.Error -> {
                    binding.tvReviewsLabel.isVisible = false
                    binding.rvReviews.isVisible = false
                }
                else -> {}
            }
        }

        viewModel.loadMoreReviews.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    isLoadingMoreReviews = true
                }
                is Result.Success -> {
                    isLoadingMoreReviews = false
                }
                is Result.Error -> {
                    isLoadingMoreReviews = false
                }
                else -> {}
            }
        }

        viewModel.displayedReviews.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) {
                binding.tvReviewsLabel.isVisible = false
                binding.rvReviews.isVisible = false
            } else {
                binding.tvReviewsLabel.isVisible = true
                binding.rvReviews.isVisible = true
                reviewAdapter.submitList(list)
            }
        }
    }

    fun openYoutube(context: Context, youtubeKey: String) {
        if (youtubeKey.isBlank()) {
            Toast.makeText(
                context,
                context.getString(R.string.error_video_not_available),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val appUri = context.getString(R.string.youtube_app_uri, youtubeKey).toUri()
        val webUri = context.getString(R.string.youtube_web_url, youtubeKey).toUri()

        val appIntent = Intent(Intent.ACTION_VIEW, appUri)
        val webIntent = Intent(Intent.ACTION_VIEW, webUri)

        if (appIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(appIntent)
        } else {
            context.startActivity(webIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
