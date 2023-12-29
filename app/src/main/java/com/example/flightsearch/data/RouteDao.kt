package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(route: Route)

    @Delete
    suspend fun delete(route: Route)

    @Query("SELECT * from favorite")
    fun getAllFavoriteRoutes(): Flow<List<Route>>
}