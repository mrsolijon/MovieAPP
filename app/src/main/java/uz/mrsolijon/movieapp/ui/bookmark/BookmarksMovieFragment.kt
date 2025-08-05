package uz.mrsolijon.movieapp.ui.bookmark

import uz.mrsolijon.movieapp.R
import uz.mrsolijon.movieapp.data.local.db.entity.MovieEntity
import uz.mrsolijon.movieapp.databinding.FragmentBookmarksMovieBinding
import uz.mrsolijon.movieapp.ui.bookmark.adapter.BookmarkMoviesAdapter
import uz.mrsolijon.movieapp.ui.bookmark.viewmodel.BookmarkMoviesViewModel
import uz.mrsolijon.movieapp.ui.bookmark.viewmodel.MovieBookmarkUiState
import uz.mrsolijon.movieapp.utils.errorDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class BookmarksMovieFragment : Fragment(R.layout.fragment_bookmarks_movie) {

    private var _binding: FragmentBookmarksMovieBinding? = null

    private val binding get() = _binding!!

    private val viewmodel: BookmarkMoviesViewModel by viewModels()

    private lateinit var bookmarkAdapter: BookmarkMoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBookmarksMovieBinding.bind(view)

        configureRecyclerView()

        observeMovies()

    }

    private fun observeMovies() {
        viewmodel.bookmarkedMoviesState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                when (state) {
                    MovieBookmarkUiState.Empty -> showEmptyData()
                    is MovieBookmarkUiState.Error -> showError(state.error)
                    is MovieBookmarkUiState.Success -> showData(state.movieEntityData)
                    MovieBookmarkUiState.Loading -> {
                        showLoading()
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showLoading() {
        binding.apply {
            ivNoMoviesImage.visibility = View.INVISIBLE
            tvNoMoviesLabel.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun showData(movieEntityData: List<MovieEntity>) {
        binding.apply {
            ivNoMoviesImage.visibility = View.INVISIBLE
            tvNoMoviesLabel.visibility = View.INVISIBLE
            progressBar.visibility = View.INVISIBLE
            bookmarkAdapter.submitList(movieEntityData)
        }
    }

    private fun showError(error: String) {
        binding.apply {
            ivNoMoviesImage.visibility = View.INVISIBLE
            tvNoMoviesLabel.visibility = View.INVISIBLE
            progressBar.visibility = View.INVISIBLE
        }
        MaterialAlertDialogBuilder(requireContext()).errorDialog(getString(R.string.error_occurred),
            error,
            onRetry = {
                viewmodel.refresh()
            },
            onCancel = {
                requireActivity().finish()
            })
    }

    private fun showEmptyData() {
        binding.apply {
            ivNoMoviesImage.visibility = View.VISIBLE
            tvNoMoviesLabel.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
        }
    }

    private fun configureRecyclerView() {
        bookmarkAdapter = BookmarkMoviesAdapter { movieId ->
            val action =
                BookmarksMovieFragmentDirections.actionBookmarkMoviesFragmentToMovieDetailsFragment(
                    movieId
                )
            findNavController().navigate(action)
        }
        binding.rvMovies.apply {
            adapter = bookmarkAdapter
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}