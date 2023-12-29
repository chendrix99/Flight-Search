package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query("SELECT * from airport WHERE iata_code != :iataCode ORDER BY passengers ASC")
    fun getAllAirportsExceptInput(iataCode: String): Flow<List<Airport>>

    @Query("SELECT * from airport WHERE iata_code LIKE :input OR name LIKE :input")
    fun getPossibleAirportFromPartial(input: String): Flow<List<Airport>>

    @Query("SELECT * from airport WHERE iata_code = :iataCode")
    fun getAirportFromCode(iataCode: String): Flow<Airport>
}