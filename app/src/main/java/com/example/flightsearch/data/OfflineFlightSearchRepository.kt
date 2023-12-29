package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

class OfflineFlightSearchRepository(
    private val airportDao: AirportDao,
    private val routeDao: RouteDao
) : FlightSearchRepository {
    override fun getAllAirportsExceptInputStream(iataCode: String): Flow<List<Airport>> =
        airportDao.getAllAirportsExceptInput(iataCode)

    override fun getPossibleAirportFromPartialStream(input: String): Flow<List<Airport>> =
        airportDao.getPossibleAirportFromPartial(input)

    override suspend fun insertRoute(route: Route) =
        routeDao.insert(route)

    override suspend fun deleteRoute(route: Route) =
        routeDao.delete(route)

    override fun getAllFavoriteRoutesStream(): Flow<List<Route>> =
        routeDao.getAllFavoriteRoutes()

    override fun getSingleAirport(iataCode: String): Flow<Airport> =
        airportDao.getAirportFromCode(iataCode)
}