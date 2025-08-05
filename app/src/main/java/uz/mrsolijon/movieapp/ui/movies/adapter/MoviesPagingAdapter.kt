package uz.mrsolijon.movieapp.ui.movies.adapter

import uz.mrsolijon.movieapp.R
import uz.mrsolijon.movieapp.data.local.db.entity.MovieEntity
import uz.mrsolijon.movieapp.databinding.ItemMovieVerticalBinding
import uz.mrsolijon.movieapp.utils.Constants
import uz.mrsolijon.movieapp.utils.DiffUtilCallback
import uz.mrsolijon.movieapp.utils.configureGenresChipGroup
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.Locale


class MoviesPagingAdapter :
    PagingDataAdapter<MovieEntity, MoviesPagingAdapter.MovieViewHolder>(diffUtilCallback) {
    inner class MovieViewHolder(private val binding: ItemMovieVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(movie: MovieEntity) {
            binding.apply {
                Glide.with(root).load(
                    "${Constants.IMAGE_URL}${movie.movieImage}"
                ).error(
                    R.drawable.error_image
                ).placeholder(R.drawable.placeholder).into(ivMovie)
                tvMovieTitle.text = movie.title
                tvMovieRate.text = root.resources.getString(
                    R.string.text_rate,
                    String.format(Locale.getDefault(), "%.1f", movie.rate)
                )
                tvReleaseDate.text = movie.releaseDate
                configureGenresChipGroup(movie.genres, chipGroup, root.context)
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieVerticalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        val diffUtilCallback = DiffUtilCallback<MovieEntity>(
            { oldItem, newItem ->
                oldItem.movieId == newItem.movieId
            },
            { oldItem, newItem ->
                oldItem == newItem
            })
    }
}