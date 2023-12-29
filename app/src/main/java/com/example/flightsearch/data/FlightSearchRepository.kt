package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

interface FlightSearchRepository {
    fun getAllAirportsExceptInputStream(iataCode: String): Flow<List<Airport>>

    fun getPossibleAirportFromPartialStream(input: String): Flow<List<Airport>>

    suspend fun insertRoute(route: Route)

    suspend fun deleteRoute(route: Route)

    fun getAllFavoriteRoutesStream(): Flow<List<Route>>

    fun getSingleAirport(iataCode: String): Flow<Airport>
}