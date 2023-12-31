package com.example.flightsearch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Airport::class, Route::class], version = 1, exportSchema = false)
abstract class AirportDatabase: RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun routeDao(): RouteDao

    companion object {
        @Volatile
        private var Instance: AirportDatabase? = null

        fun getDatabase(context: Context): AirportDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AirportDatabase::class.java, "flight_search")
                    .createFromAsset("database/flight_search.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {Instance = it}
            }
        }
    }
}