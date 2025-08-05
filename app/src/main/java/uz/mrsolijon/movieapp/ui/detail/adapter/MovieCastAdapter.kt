package uz.mrsolijon.movieapp.ui.detail.adapter

import uz.mrsolijon.movieapp.data.local.db.entity.CastEntity
import uz.mrsolijon.movieapp.utils.Constants
import uz.mrsolijon.movieapp.utils.DiffUtilCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.mrsolijon.movieapp.R
import uz.mrsolijon.movieapp.databinding.ItemMovieCastBinding


class MovieCastAdapter :
    ListAdapter<CastEntity, MovieCastAdapter.CastViewHolder>(callback) {
    inner class CastViewHolder(private val binding: ItemMovieCastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(cast: CastEntity) {
            binding.tvCastName.text = cast.name
            Glide.with(binding.root.context).load("${Constants.IMAGE_URL}${cast.image}")
                .placeholder(R.drawable.placeholder).into(binding.ivCastImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(
            ItemMovieCastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        val callback = DiffUtilCallback<CastEntity>(
            { oldItem, newItem ->
                oldItem.castId == newItem.castId
            },
            { oldItem, newItem ->
                oldItem == newItem
            })
    }
}

