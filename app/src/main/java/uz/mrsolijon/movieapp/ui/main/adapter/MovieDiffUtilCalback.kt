package uz.mrsolijon.movieapp.ui.main.adapter

import uz.mrsolijon.movieapp.data.local.db.entity.MovieEntity
import uz.mrsolijon.movieapp.utils.DiffUtilCallback



val movieDiffUtilCallback = DiffUtilCallback<MovieEntity>({ oldItem, newItem ->
    oldItem.movieId == newItem.movieId
}, { oldItem, newItem ->
    oldItem == newItem
})