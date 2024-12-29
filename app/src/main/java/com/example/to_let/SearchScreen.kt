package com.example.to_let

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_let.model.RentalOption

@Composable
fun SearchScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp), contentAlignment = Alignment.Center
    ) {
        var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
        var filteredOptions by remember { mutableStateOf(emptyList<RentalOption>()) }
        var showFilterDialog by remember { mutableStateOf(false) }
        var selectedFilters by remember {
            mutableStateOf(
                setOf(
                    "PG",
                    "Rooms",
                    "Rental Houses",
                    "Hotels"
                )
            )
        }

        val allRentalOptions = listOf(
            RentalOption(
                "PG",
                "Dharmapuri PG 1",
                "https://via.placeholder.com/150",
                "Owner 1",
                "1234567890",
                "Dharmapuri Location 1"
            ),
            RentalOption(
                "Rooms",
                "Dharmapuri Room 1",
                "https://via.placeholder.com/150",
                "Owner 2",
                "0987654321",
                "Hosur Location 2"
            ),
            RentalOption(
                "Rental Houses",
                "Dharmapuri House 1",
                "https://via.placeholder.com/150",
                "Owner 3",
                "1122334455",
                "Bangalore Location 3"
            ),
            RentalOption(
                "Hotels",
                "Dharmapuri Hotel 1",
                "https://via.placeholder.com/150",
                "Owner 4",
                "5566778899",
                "Krishnagiri Location 4"
            ),
            RentalOption(
                "PG",
                "Dharmapuri PG 2",
                "https://via.placeholder.com/150",
                "Owner 5",
                "2233445566",
                "Salem Location 5"
            )
        )

        val filteredItems = allRentalOptions.filter { rental ->
            rental.location.contains(
                searchQuery.text,
                ignoreCase = true
            ) && rental.type in selectedFilters
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SearchBar(searchQuery = searchQuery, onSearchQueryChange = { searchQuery = it })
            Spacer(modifier = Modifier.height(8.dp))
            FilterIconButton { showFilterDialog = true }
            Spacer(modifier = Modifier.height(8.dp))

            RentalOptionsList(rentalOptions = filteredItems)
        }

        if (showFilterDialog) {
            FilterDialog(
                selectedFilters = selectedFilters,
                onDismiss = { showFilterDialog = false },
                onFilterChange = { selectedFilters = it }
            )
        }
    }
}

@Composable
fun SearchBar(searchQuery: TextFieldValue, onSearchQueryChange: (TextFieldValue) -> Unit) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = {
            if (searchQuery.text.isEmpty()) {
                Text("Enter city name...", color = Color.Gray)
            }
        },
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    )
}

@Composable
fun FilterIconButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(Icons.Filled.FilterList, contentDescription = "Filter")
    }
}

@Composable
fun RentalOptionsList(rentalOptions: List<RentalOption>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(rentalOptions) { rental ->
            RentalOptionCard(rental = rental)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun RentalOptionCard(rental: RentalOption) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        // elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Image
            Image(
                painter = painterResource(R.drawable.to_let),
                contentDescription = "Rental Image",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Rental Info
            Text(
                text = rental.name,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize
                )
            )
            Text(text = "Type: ${rental.type}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))

            // Owner Info
            Text(text = "Owner: ${rental.ownerName}", style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "Contact: ${rental.ownerContact}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Location with map icon
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Map,
                    contentDescription = "Location",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = rental.location, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun FilterDialog(
    selectedFilters: Set<String>,
    onDismiss: () -> Unit,
    onFilterChange: (Set<String>) -> Unit
) {
    val allFilters = listOf("PG", "Rooms", "Rental Houses", "Hotels")

    androidx.compose.material.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Filters") },
        buttons = {
            Column {
                allFilters.forEach { filter ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Checkbox(
                            checked = selectedFilters.contains(filter),
                            onCheckedChange = {
                                onFilterChange(
                                    if (selectedFilters.contains(filter)) {
                                        selectedFilters - filter
                                    } else {
                                        selectedFilters + filter
                                    }
                                )
                            }
                        )
                        Text(text = filter)
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Close")
                    }
                }
            }
        }
    )
}