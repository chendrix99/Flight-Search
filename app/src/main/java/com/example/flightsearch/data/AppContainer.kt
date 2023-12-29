package com.example.flightsearch.data

import android.content.Context

interface AppContainer {
    val flightSearchRepository: FlightSearchRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val flightSearchRepository: FlightSearchRepository by lazy {
        OfflineFlightSearchRepository(
            AirportDatabase.getDatabase(context).airportDao(),
            AirportDatabase.getDatabase(context).routeDao()
        )
    }
}