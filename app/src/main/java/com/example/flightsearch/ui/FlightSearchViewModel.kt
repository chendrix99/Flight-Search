package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.FlightSearchRepository
import com.example.flightsearch.data.Route
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FlightSearchViewModel(
    private val flightSearchRepository: FlightSearchRepository
) : ViewModel() {
    private val _flightUiState = MutableStateFlow(FlightSearchUiState())
    var flightUiState: StateFlow<FlightSearchUiState> = _flightUiState.asStateFlow()

    fun updateUiState(input: String, airportSelected: Boolean) {
        _flightUiState.update { currentState ->
            currentState.copy(
                userInput = input,
                airportSelected = airportSelected
            )
        }
    }

    fun getAllAirports(iataCode: String): Flow<List<Airport>> =
        flightSearchRepository.getAllAirportsExceptInputStream(iataCode)

    fun getPossibleAirports(input: String): Flow<List<Airport>> =
        flightSearchRepository.getPossibleAirportFromPartialStream(input)

    suspend fun addFavoriteRoute(route: Route) =
        flightSearchRepository.insertRoute(route)

    suspend fun removeFavoriteRoute(route: Route) =
        flightSearchRepository.deleteRoute(route)

    fun getFavoriteRoutes(): Flow<List<Route>> =
        flightSearchRepository.getAllFavoriteRoutesStream()

    fun getAirportByCode(iataCode: String): Flow<Airport> =
        flightSearchRepository.getSingleAirport(iataCode)
}

data class FlightSearchUiState(
    val userInput: String = "",
    val airportSelected: Boolean = false
)