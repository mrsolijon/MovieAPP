package uz.mrsolijon.movieapp.data.repository

import uz.mrsolijon.movieapp.data.local.LocalDataSource
import uz.mrsolijon.movieapp.data.local.db.entity.MovieDetailsEntity
import uz.mrsolijon.movieapp.data.local.db.entity.MovieWithCastEntity
import uz.mrsolijon.movieapp.data.mapper.toMovieDetails
import uz.mrsolijon.movieapp.data.mapper.toMovieWithCastEntity
import uz.mrsolijon.movieapp.data.remote.RemoteDataSource
import uz.mrsolijon.movieapp.data.remote.model.MovieVideosResponse
import uz.mrsolijon.movieapp.utils.Resource
import uz.mrsolijon.movieapp.utils.isNetworkAvailable
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MovieDetailsRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    @ApplicationContext val context: Context
) {
    suspend fun upsertMovieDetails(movieDetailsEntity: MovieDetailsEntity) {
        localDataSource.upsertMovieDetails(movieDetailsEntity)
    }

    suspend fun getMovieDetailsById(movieId: Int): Resource<MovieDetailsEntity> =
        withContext(Dispatchers.IO) {
            val isExist = localDataSource.isExistMovieDetails(movieId)
            if (isNetworkAvailable(context)) {
                try {
                    if (!isExist) {
                        val response = remoteDataSource.getMovieDetailsById(movieId)
                        localDataSource.upsertMovieDetails(response.toMovieDetails())
                    }
                    Resource.Success(localDataSource.getMovieDetailsById(movieId))
                } catch (e: HttpException) {
                    Resource.Error.HttpError
                } catch (e: Exception) {
                    Resource.Error.UnknownError(e.localizedMessage ?: "Unknown error occurred")

                }
            } else {
                if (!isExist) {
                    Resource.Error.NetworkError
                } else {
                    Resource.Success(localDataSource.getMovieDetailsById(movieId))
                }
            }
        }

    suspend fun getMovieVideos(movieId: Int): Resource<MovieVideosResponse> =
        withContext(Dispatchers.IO) {
            if (isNetworkAvailable(context)) {
                try {
                    val response = remoteDataSource.getMovieVideosById(movieId)
                    Resource.Success(response)
                } catch (e: HttpException) {
                    Resource.Error.HttpError
                } catch (e: Exception) {
                    Resource.Error.UnknownError(e.localizedMessage ?: "Unknown error occurred")
                }
            } else {
                Resource.Error.NetworkError
            }
        }

    suspend fun getMovieCastById(
        movieId: Int
    ): Resource<MovieWithCastEntity> = withContext(Dispatchers.IO) {
        val isExistMovieCast = localDataSource.isExistMovieCast(movieId)
        if (isNetworkAvailable(context)) {
            try {
                if (!isExistMovieCast) {
                    val response = remoteDataSource.getMovieCastById(movieId)
                    localDataSource.upsertCast(response.toMovieWithCastEntity())
                }
                Resource.Success(localDataSource.getMovieCastById(movieId))
            } catch (e: HttpException) {
                Resource.Error.HttpError
            } catch (e: Exception) {
                Resource.Error.UnknownError(
                    e.localizedMessage ?: "Unknown error occurred"
                )
            }
        } else {
            if (!isExistMovieCast) {
                Resource.Error.NetworkError
            } else {
                Resource.Success(localDataSource.getMovieCastById(movieId))
            }
        }
    }
}