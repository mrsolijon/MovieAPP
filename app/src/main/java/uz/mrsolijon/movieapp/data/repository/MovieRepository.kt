package uz.mrsolijon.movieapp.data.repository

import uz.mrsolijon.movieapp.data.local.LocalDataSource
import uz.mrsolijon.movieapp.data.local.db.entity.MovieEntity
import uz.mrsolijon.movieapp.data.mapper.toMovieEntity
import uz.mrsolijon.movieapp.data.remote.RemoteDataSource
import uz.mrsolijon.movieapp.utils.Constants.cacheTimeout
import uz.mrsolijon.movieapp.utils.MovieType
import uz.mrsolijon.movieapp.utils.Resource
import uz.mrsolijon.movieapp.utils.isNetworkAvailable
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    @ApplicationContext val context: Context
) {
    private val cachedMovies =
        mutableMapOf<MovieType, MutableStateFlow<Resource<List<MovieEntity>>>>(
            MovieType.NOW_PLAYING to MutableStateFlow(Resource.Loading),
            MovieType.TOP_RATED to MutableStateFlow(Resource.Loading)
        )

    private var job: Job? = null

    init {
        getMovies(MovieType.NOW_PLAYING)
        getMovies(MovieType.TOP_RATED)
    }

    private fun getMovies(movieType: MovieType) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val cachedMovies = localDataSource.getAllMovies(movieType.name)
            if (isNetworkAvailable(context)) {
                try {
                    coroutineScope {
                        val shouldFetchFromRemote =
                            cachedMovies.isEmpty() || System.currentTimeMillis() - cachedMovies.last().timestamp > cacheTimeout
                        if (shouldFetchFromRemote) {
                            val deferredList = async {
                                when (movieType) {
                                    MovieType.NOW_PLAYING -> remoteDataSource.getNowPlayingMovies()
                                    MovieType.TOP_RATED -> remoteDataSource.getTopRatedMovies()
                                }
                            }
                            val response = deferredList.await().films.map {
                                it.toMovieEntity().copy(movieType = movieType.name)
                            }
                            localDataSource.clearMoviesByType(movieType.name)
                            localDataSource.insertMovies(response)
                        }
                        this@MovieRepository.cachedMovies[movieType]?.value =
                            Resource.Success(localDataSource.getAllMovies(movieType.name))
                    }
                } catch (e: HttpException) {
                    this@MovieRepository.cachedMovies[movieType]?.value = Resource.Error.HttpError
                } catch (e: Exception) {
                    this@MovieRepository.cachedMovies[movieType]?.value =
                        Resource.Error.UnknownError(e.localizedMessage ?: "Unknown error occurred")
                }
            } else {
                if (cachedMovies.isNotEmpty()) {
                    this@MovieRepository.cachedMovies[movieType]?.value =
                        Resource.Success(localDataSource.getAllMovies(movieType.name))
                } else {
                    this@MovieRepository.cachedMovies[movieType]?.value =
                        Resource.Error.NetworkError
                }
            }
        }
    }

    fun getCachedMovies(movieType: MovieType) = cachedMovies[movieType]?.asStateFlow()
        ?: throw IllegalArgumentException("Invalid movie type")

    fun getBookmarkedMovies() = localDataSource.getBookmarkedMovies()

    fun onClearJob() {
        job?.cancel()
        job = null
    }

}
