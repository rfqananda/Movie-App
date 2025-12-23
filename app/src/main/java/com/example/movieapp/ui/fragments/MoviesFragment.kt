package com.example.movieapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.MovieAppApplication
import com.example.movieapp.databinding.FragmentMoviesBinding
import com.example.movieapp.ui.adapter.MovieAdapter
import com.example.movieapp.ui.vm.MoviesViewModel
import javax.inject.Inject
import com.example.registrationapp.core.Result

class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MoviesViewModel by viewModels { viewModelFactory }

    private val args: MoviesFragmentArgs by navArgs()

    private val movieAdapter = MovieAdapter()

    private var isLoadingMore = false

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
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupListener()
        observeViewModel()
        viewModel.loadMovies(args.id)
    }

    private fun setupView() {
        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieAdapter
            addOnScrollListener(createScrollListener())
        }
    }

    private fun setupListener() {
        movieAdapter.setOnItemClickListener { movie ->
            val action = MoviesFragmentDirections.moviesToDetail(
                id = movie.id
            )
            findNavController().navigate(action)
        }
    }

    private fun createScrollListener() = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            if (!isLoadingMore &&
                (visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                firstVisibleItemPosition >= 0) {
                viewModel.loadNextPage()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.movies.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.rvMovies.isVisible = false
                    binding.layoutEmpty.isVisible = false
                }
                is Result.Success -> {
                    binding.progressBar.isVisible = false
                    if (result.data.isEmpty()) {
                        binding.layoutEmpty.isVisible = true
                        binding.rvMovies.isVisible = false
                    } else {
                        binding.layoutEmpty.isVisible = false
                        binding.rvMovies.isVisible = true
                        movieAdapter.submitList(result.data)
                    }
                }
                is Result.Error -> {
                    binding.progressBar.isVisible = false
                    binding.rvMovies.isVisible = false
                    binding.layoutEmpty.isVisible = true
                }
                else -> {}
            }
        }

        viewModel.loadMoreMovies.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    isLoadingMore = true
                }
                is Result.Success -> {
                    isLoadingMore = false
                    val currentList = movieAdapter.currentList.toMutableList()
                    currentList.addAll(result.data)
                    movieAdapter.submitList(currentList)
                }
                is Result.Error -> {
                    isLoadingMore = false
                }
                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
