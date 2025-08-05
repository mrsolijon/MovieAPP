package uz.mrsolijon.movieapp.data.local.db

import uz.mrsolijon.movieapp.data.local.db.converter.Converters
import uz.mrsolijon.movieapp.data.local.db.dao.MovieCastDao
import uz.mrsolijon.movieapp.data.local.db.dao.MovieDao
import uz.mrsolijon.movieapp.data.local.db.dao.MovieDetailsDao
import uz.mrsolijon.movieapp.data.local.db.dao.RemoteKeysDao
import uz.mrsolijon.movieapp.data.local.db.entity.MovieWithCastEntity
import uz.mrsolijon.movieapp.data.local.db.entity.MovieDetailsEntity
import uz.mrsolijon.movieapp.data.local.db.entity.MovieEntity
import uz.mrsolijon.movieapp.data.local.db.entity.RemoteKeysEntity
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [MovieEntity::class, MovieDetailsEntity::class, MovieWithCastEntity::class, RemoteKeysEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun castDao(): MovieCastDao

    abstract fun detailsDao(): MovieDetailsDao

    abstract fun remoteKeysDao(): RemoteKeysDao

}