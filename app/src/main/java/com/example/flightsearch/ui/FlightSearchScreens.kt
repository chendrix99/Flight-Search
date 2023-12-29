package com.example.flightsearch.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.Route
import com.example.flightsearch.ui.theme.Gold
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchApp(
    viewModel: FlightSearchViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    // will need to collect the ui state from the view model here

    Scaffold(
        modifier = modifier,
        topBar = { FlightSearchTopAppBar() }
    ) {innerPadding ->
        // Will need to add the ui state from the view model here
        SearchAndResultsScreen(
            viewModel = viewModel,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchTopAppBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {Text(stringResource(id = R.string.app_name))},
        modifier = modifier
    )
}

@Composable
fun SearchAndResultsScreen(
    viewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.flightUiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp)
    ) {
        SearchBar(
            value = uiState.userInput,
            onValueChange = {
                viewModel.updateUiState(it, "")
            }
        )

        if (uiState.userInput != "") {
            val airports by viewModel.getPossibleAirports('%' + uiState.userInput + '%')
                .collectAsState(emptyList())
            SearchSuggestions(
                airportList = airports
            )
        }

        // TODO if there is a selected airport then display the routes for
        // that airport
        if (uiState.currentSelection != "") {
            //val routes by viewModel.get
        }

        // TODO if there is no selected airport then display the list of
        // favorite routes
        if (uiState.currentSelection == "") {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    value: String = "",
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(stringResource(id = R.string.search_title)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
fun SearchSuggestions(
    airportList: List<Airport>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(16.dp)
    ) {
        LazyColumn {
            items(items = airportList) {item ->
                AirportDescription(
                    iataCode = item.iataCode,
                    name = item.name
                )
            }
        }
    }
}

@Composable
fun AirportDescription(
    iataCode: String,
    name: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        // TODO Adjust font size
        Text(
            text = iataCode,
            fontWeight = FontWeight.Bold
        )
        Text(text = name)
    }
}

@Composable
fun RoutesList(
    routeList: List<Route>,
    listTitle: String,
    modifier: Modifier = Modifier
) {
    Column() {
        Text(text = listTitle)
        LazyColumn {
            items(items = routeList) {item ->
                //RouteCard(route = item)
            }
        }
    }
}

/*@Composable
fun RouteCard(
    route: Route,
    modifier: Modifier = Modifier
) {
    val departAirport = route.departAirport
    val arriveAirport = route.arriveAirport
    val favorite = route.favoriteRoute

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.depart)
                )
                AirportDescription(
                    iataCode = departAirport.iataCode,
                    name = departAirport.name
                )
                Text(
                    text = stringResource(id = R.string.arrive)
                )
                AirportDescription(
                    iataCode = arriveAirport.iataCode,
                    name = arriveAirport.name
                )
            }
            FilledTonalIconButton(onClick = {}) {
                if (favorite) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "favorite",
                        tint = Gold
                    )
                } else {
                    Icon(Icons.Filled.Star, contentDescription = "favorite")
                }
            }
        }
    }
}
*/

@Preview
@Composable
fun SearchBarPreview() {
    //SearchBar()
}

@Preview
@Composable
fun AirportDescriptionPreview() {
    AirportDescription(iataCode = "SOV", name = "This is the airport")
}

@Preview
@Composable
fun RouteCardPreview() {

}

@Preview
@Composable
fun SearchAndResultsScreenPreview() {
    //SearchAndResultsScreen()
}