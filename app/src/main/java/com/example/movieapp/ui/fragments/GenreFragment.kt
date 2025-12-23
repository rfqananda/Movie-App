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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.MovieAppApplication
import com.example.movieapp.databinding.FragmentGenreBinding
import com.example.movieapp.ui.adapter.GenreAdapter
import com.example.movieapp.ui.vm.GenreViewModel
import javax.inject.Inject
import com.example.registrationapp.core.Result

class GenreFragment : Fragment() {

    private var _binding: FragmentGenreBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: GenreViewModel by viewModels { viewModelFactory }

    private val genreAdapter = GenreAdapter()

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
        _binding = FragmentGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupListener()
        observeViewModel()
        viewModel.loadGenres()
    }

    private fun setupView() {
        binding.rvGenres.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = genreAdapter
        }
    }

    private fun setupListener() {
        genreAdapter.setOnItemClickListener { genre ->
            val action = GenreFragmentDirections.genreToMovies(
                id = genre.id
            )
            findNavController().navigate(action)
        }

    }

    private fun observeViewModel() {
        viewModel.genres.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.rvGenres.isVisible = false
                    binding.layoutEmpty.isVisible = false
                }

                is Result.Success -> {
                    binding.progressBar.isVisible = false
                    if (result.data.isEmpty()) {
                        binding.layoutEmpty.isVisible = true
                        binding.rvGenres.isVisible = false
                    } else {
                        binding.layoutEmpty.isVisible = false
                        binding.rvGenres.isVisible = true
                        genreAdapter.submitList(result.data)
                    }
                }

                is Result.Error -> {
                    binding.progressBar.isVisible = false
                    binding.rvGenres.isVisible = false
                    binding.layoutEmpty.isVisible = true
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
