package uz.mrsolijon.movieapp.ui.main.viewmodel

import uz.mrsolijon.movieapp.data.local.db.entity.MovieEntity

sealed interface MainUIState {

    data object Loading : MainUIState

    data class Success(val data: List<MovieEntity>) : MainUIState

    sealed interface Error : MainUIState {
        data object NetworkError : Error
        data object HttpError : Error
        data class UnknownError(val error: String) : Error
    }

}