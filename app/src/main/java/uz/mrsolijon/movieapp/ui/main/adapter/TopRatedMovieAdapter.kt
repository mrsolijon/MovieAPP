package uz.mrsolijon.movieapp.ui.main.adapter

import uz.mrsolijon.movieapp.R
import uz.mrsolijon.movieapp.data.local.db.entity.MovieEntity
import uz.mrsolijon.movieapp.databinding.ItemMovieVerticalBinding
import uz.mrsolijon.movieapp.utils.Constants
import uz.mrsolijon.movieapp.utils.configureGenresChipGroup
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.Locale



class TopRatedMovieAdapter(
    val onItemClick: (Int) -> Unit
) :
    ListAdapter<MovieEntity, TopRatedMovieAdapter.PopularViewHolder>(movieDiffUtilCallback) {

    inner class PopularViewHolder(private val binding: ItemMovieVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(movieEntity: MovieEntity) {
            binding.apply {
                tvMovieTitle.text = movieEntity.title
                tvMovieRate.text =
                    root.resources.getString(
                        R.string.text_rate,
                        String.format(Locale.getDefault(), "%.1f", movieEntity.rate)
                    )
                Glide.with(root).load("${Constants.IMAGE_URL}${movieEntity.movieImage}")
                    .error(R.drawable.error_image).placeholder(R.drawable.placeholder).into(ivMovie)
                configureGenresChipGroup(movieEntity.genres, chipGroup, root.context)
                tvReleaseDate.text = movieEntity.releaseDate
                root.setOnClickListener {
                    onItemClick.invoke(movieEntity.movieId)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(
            ItemMovieVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

}