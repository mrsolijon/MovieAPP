package uz.mrsolijon.movieapp.data.remote.api

import uz.mrsolijon.movieapp.BuildConfig
import uz.mrsolijon.movieapp.data.remote.model.MovieCastResponse
import uz.mrsolijon.movieapp.data.remote.model.MovieDetailsResponse
import uz.mrsolijon.movieapp.data.remote.model.MovieResponse
import uz.mrsolijon.movieapp.data.remote.model.MovieVideosResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApiService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): MovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailsById(
        @Path("movie_id") movieId: Int,
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): MovieDetailsResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideosById(
        @Path("movie_id") movieId: Int,
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): MovieVideosResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCastById(
        @Path("movie_id") movieId: Int,
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): MovieCastResponse
}