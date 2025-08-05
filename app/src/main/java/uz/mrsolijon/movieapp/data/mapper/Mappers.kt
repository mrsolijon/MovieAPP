package uz.mrsolijon.movieapp.data.mapper

import uz.mrsolijon.movieapp.data.local.db.entity.CastEntity
import uz.mrsolijon.movieapp.data.local.db.entity.MovieDetailsEntity
import uz.mrsolijon.movieapp.data.local.db.entity.MovieEntity
import uz.mrsolijon.movieapp.data.local.db.entity.MovieWithCastEntity
import uz.mrsolijon.movieapp.data.remote.model.Cast
import uz.mrsolijon.movieapp.data.remote.model.Film
import uz.mrsolijon.movieapp.data.remote.model.MovieCastResponse
import uz.mrsolijon.movieapp.data.remote.model.MovieDetailsResponse
import uz.mrsolijon.movieapp.utils.getCurrentMovieGenres



fun Film.toMovieEntity() = MovieEntity(
    movieId = id,
    title = title,
    movieImage = backdropPath,
    rate = voteAverage,
    releaseDate = releaseDate,
    genres = getCurrentMovieGenres(genreIds)
)

fun MovieDetailsResponse.toMovieDetails() = MovieDetailsEntity(
    id = this.id,
    title = this.title,
    movieImage = this.backdropPath,
    rate = this.voteAverage,
    releaseDate = this.releaseDate,
    genres = this.genres.map {
        it.name
    },
    runtime = this.runtime,
    overview = this.overview,
    language = this.languages.map {
        it.englishName
    }.lastOrNull() ?: "Unknown"
)

fun MovieDetailsEntity.toMovieEntity() = MovieEntity(
    movieId = id,
    title = title,
    movieImage = movieImage,
    rate = rate,
    releaseDate = releaseDate,
    genres = genres
)

fun MovieCastResponse.toMovieWithCastEntity() = MovieWithCastEntity(
    movieId = id,
    cast = cast.map {
        it.toCastEntity()
    },
    timeStamp = System.currentTimeMillis()
)

fun Cast.toCastEntity() = CastEntity(
    castId = castId,
    name = name,
    image = profilePath
)