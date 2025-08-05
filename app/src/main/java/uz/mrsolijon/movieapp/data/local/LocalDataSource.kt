package uz.mrsolijon.movieapp.data.local

import uz.mrsolijon.movieapp.data.local.db.dao.MovieCastDao
import uz.mrsolijon.movieapp.data.local.db.dao.MovieDao
import uz.mrsolijon.movieapp.data.local.db.dao.MovieDetailsDao
import uz.mrsolijon.movieapp.data.local.db.entity.MovieDetailsEntity
import uz.mrsolijon.movieapp.data.local.db.entity.MovieEntity
import uz.mrsolijon.movieapp.data.local.db.entity.MovieWithCastEntity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
    private val castDao: MovieCastDao,
    private val detailsDao: MovieDetailsDao
) {

    // Movie dao functions

    suspend fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovies(movies)

    fun getBookmarkedMovies() = movieDao.getBookmarkedMovies()

    suspend fun getAllMovies(movieType: String) = movieDao.getAllMovies(movieType)

    // Movie details dao functions

    suspend fun upsertMovieDetails(movieDetails: MovieDetailsEntity) =
        detailsDao.upsertMovieDetails(movieDetails)

    suspend fun getMovieDetailsById(movieId: Int) = detailsDao.getMovieDetailsById(movieId)

    suspend fun isExistMovieDetails(movieId: Int) = detailsDao.isExistMovieDetails(movieId)

    suspend fun clearMoviesByType(type: String) {
        movieDao.clearMoviesByType(type)
    }

    // Cast dao functions

    suspend fun upsertCast(cast: MovieWithCastEntity) = castDao.upsertCast(cast)

    suspend fun getMovieCastById(movieId: Int) = castDao.getMovieCastById(movieId)

    suspend fun isExistMovieCast(movieId: Int) = castDao.isExistMovieCast(movieId)


}