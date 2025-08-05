package uz.mrsolijon.movieapp.ui.movies.viewmodel

import uz.mrsolijon.movieapp.data.local.db.entity.MovieEntity
import androidx.paging.PagingData


sealed interface MoviesUiState {

    data object Loading : MoviesUiState

    sealed interface Error : MoviesUiState {

        data object HttpError : Error

        data object NetworkError : Error

        data class UnknownError(val error: String) : Error

    }

    data class Success(val data: PagingData<MovieEntity>) : MoviesUiState
}