package uz.mrsolijon.movieapp.ui.bookmark.viewmodel

import uz.mrsolijon.movieapp.data.local.db.entity.MovieEntity

sealed interface MovieBookmarkUiState {
    data class Success(val movieEntityData: List<MovieEntity>) : MovieBookmarkUiState
    data class Error(val error: String) : MovieBookmarkUiState
    data object Empty : MovieBookmarkUiState
    data object Loading : MovieBookmarkUiState
}