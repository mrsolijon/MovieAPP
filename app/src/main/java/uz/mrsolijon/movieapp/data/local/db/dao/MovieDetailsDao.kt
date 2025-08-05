package uz.mrsolijon.movieapp.data.local.db.dao

import uz.mrsolijon.movieapp.data.local.db.entity.MovieDetailsEntity
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface MovieDetailsDao {

    @Upsert
    suspend fun upsertMovieDetails(movieDetailsEntity: MovieDetailsEntity)

    @Query("SELECT EXISTS(SELECT * FROM movie_details_table WHERE movie_id=:movieId)")
    suspend fun isExistMovieDetails(movieId: Int): Boolean

    @Query("SELECT * FROM movie_details_table WHERE movie_id=:id")
    suspend fun getMovieDetailsById(id: Int): MovieDetailsEntity
}