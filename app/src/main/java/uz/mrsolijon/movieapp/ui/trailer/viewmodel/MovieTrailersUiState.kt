package uz.mrsolijon.movieapp.ui.trailer.viewmodel

import uz.mrsolijon.movieapp.data.remote.model.Video


sealed interface MovieTrailersUiState {

    data object Loading : MovieTrailersUiState

    data class Success(val data: List<Video>) : MovieTrailersUiState

    sealed interface Error : MovieTrailersUiState {
        data class UnknownError(val error: String) : Error
        data object NetworkError : Error
        data object HttpError : Error
    }

}