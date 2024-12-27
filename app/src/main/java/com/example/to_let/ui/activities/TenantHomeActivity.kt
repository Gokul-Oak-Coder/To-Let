package com.example.to_let.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BadgedBox
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.to_let.ui.theme.ToLetTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Hub
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.to_let.R
import com.example.to_let.model.BottomNavItem
import com.example.to_let.model.BottomNavigationItem
import com.example.to_let.model.Owner
import com.example.to_let.model.ShelterData
import com.example.to_let.model.Tenant
import com.example.to_let.model.UserType
import com.google.accompanist.pager.ExperimentalPagerApi


class TenantHomeActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val role = intent.getStringExtra("ROLE")
        setContent {
            ToLetTheme {
                val navController = rememberNavController()
                val items = listOf(
                    BottomNavigationItem(
                        title = "Home",
                        route = "home",
                        selectedIcon = Icons.Filled.Home,
                        unSelectedIcon = Icons.Outlined.Home,
                        hasNews = false,
                    ),
                    BottomNavigationItem(
                        title = "Rentals",
                        route = "rentals",
                        selectedIcon = Icons.Filled.Hub,
                        unSelectedIcon = Icons.Outlined.Hub,
                        hasNews = false,
                    ),
                    BottomNavigationItem(
                        title = "Messages",
                        route = "messages",
                        selectedIcon = Icons.Filled.Message,
                        unSelectedIcon = Icons.Outlined.Message,
                        hasNews = false,
                        badgeCount = 45
                    ),
                    BottomNavigationItem(
                        title = "Profile",
                        route = "profile",
                        selectedIcon = Icons.Filled.Person,
                        unSelectedIcon = Icons.Outlined.Person,
                        hasNews = true,
                    ),
                )
                var selectedItemIndex by rememberSaveable {
                    mutableStateOf(0)
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                items.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            navController.navigate(item.route) {
                                                popUpTo(id = navController.graph.findStartDestination().id) {
                                                    saveState =
                                                        true // Clear all back stack entries up to the start destination
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        label = {
                                            Text(text = item.title)
                                        },
                                        alwaysShowLabel = true,
                                        icon = {
                                            BadgedBox(
                                                badge = {
                                                    if (item.badgeCount != null) {
                                                        Badge {
                                                            Text(text = item.badgeCount.toString())
                                                        }
                                                    } else if (item.hasNews) {
                                                        Badge()
                                                    }
                                                }) {
                                                Icon(
                                                    imageVector = if (index == selectedItemIndex) {
                                                        item.selectedIcon
                                                    } else {
                                                        item.unSelectedIcon
                                                    },
                                                    contentDescription = item.title
                                                )


                                            }
                                        }
                                    )
                                }
                            }
                        }
                    ) {
                        Navigation(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen()
        }
        composable("rentals") {
            RentalScreen()
        }
        composable("messages") {
            MessagesScreen()
        }
        composable("profile") {
            ProfileScreen()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen() {
    var selectedCategory by remember { mutableStateOf("House") }
    val pagerState = rememberPagerState()

    /*// Access PagerState properties directly
    val currentPage = pagerState.currentPage
    val currentPageOffset = pagerState.currentPageOffset*/

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Location and Weather Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    // Location Icon and Name
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location Icon"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Location Name", style = MaterialTheme.typography.labelSmall)
                }
                // Weather Icon and Details
                Row {
                    Icon(
                        imageVector = Icons.Default.Cloud,
                        contentDescription = "Weather Icon"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "25°C", style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Column(
                horizontalAlignment = CenterHorizontally,
            ) {
                // Greeting User
                Text(
                    text = "Welcome Gokul!!!",
                    style = TextStyle(
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                // Tagline
                Text(
                    text = "Find your shelter effortlessly",
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 20.sp, color = Color.Gray)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            /*// Category Selection Buttons (House, PG, Rooms, Hotel)
            HorizontalPager(
                count = 1, // Number of buttons
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                when (page) {
                    0 -> ShelterButton("House", selectedCategory) { selectedCategory = "House" }
                    1 -> ShelterButton("PG", selectedCategory) { selectedCategory = "PG" }
                    2 -> ShelterButton("Rooms", selectedCategory) { selectedCategory = "Rooms" }
                    3 -> ShelterButton("Hotel", selectedCategory) { selectedCategory = "Hotel" }
                }
            }*/
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LazyRow {
                    item {
                        ShelterButton("House", selectedCategory) { selectedCategory = "House" }
                        ShelterButton("PG", selectedCategory) { selectedCategory = "PG" }
                        ShelterButton("Rooms", selectedCategory) { selectedCategory = "Rooms" }
                        ShelterButton("Hotel", selectedCategory) { selectedCategory = "Hotel" }
                    }


                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display the list of static data based on selection
            when (selectedCategory) {
                "House" -> ShelterList(
                    listOf(
                        ShelterData(
                            R.drawable.pg_1,
                            "John Doe",
                            "123 Main St",
                            "Location A",
                            "123-456-7890"
                        ),
                        ShelterData(
                            R.drawable.pg_8,
                            "John Doe",
                            "123 Main St",
                            "Location A",
                            "123-456-7890"
                        ),
                        ShelterData(
                            R.drawable.pg_2,
                            "John Doe",
                            "123 Main St",
                            "Location A",
                            "123-456-7890"
                        )
                    )
                )

                "PG" -> ShelterList(
                    listOf(
                        ShelterData(
                            R.drawable.pg_3,
                            "Alice",
                            "456 Oak Rd",
                            "Location B",
                            "987-654-3210"
                        ),
                        ShelterData(R.drawable.pg_4, "Alice", "456 Oak Rd", "Location B", "987-654-3210")
                    )
                )

                "Rooms" -> ShelterList(
                    listOf(
                        ShelterData(
                            R.drawable.pg_5,
                            "Bob",
                            "789 Pine St",
                            "Location C",
                            "555-123-4567"
                        )
                    )
                )

                "Hotel" -> ShelterList(
                    listOf(
                        ShelterData(
                            R.drawable.pg_6,
                            "Charlie",
                            "101 Maple Ave",
                            "Location D",
                            "444-567-8901"
                        ),
                        ShelterData(
                            R.drawable.pg7,
                            "Charlie",
                            "101 Maple Ave",
                            "Location D",
                            "444-567-8901"
                        )
                    )
                )
            }
        }
    }
}

@Composable
fun ShelterCard(shelter: ShelterData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        //elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Shelter Image (Placeholder)
            Image(
                painter = painterResource(shelter.image),
                contentDescription = "Shelter Image"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Shelter Details
            Text(
                text = "Owner: ${shelter.ownerName}",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(text = "Address: ${shelter.address}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Location: ${shelter.location}", style = MaterialTheme.typography.bodySmall)
            Text(
                text = "Owner Contact: ${shelter.ownerContact}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun ShelterList(shelters: List<ShelterData>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
       /* shelters.forEach { shelter ->
            ShelterCard(shelter)
        }*/
        items(shelters) { shelter ->
            ShelterCard(shelter)
        }
    }
}

/*@Composable
fun ShelterButton(
    label: String,
    selectedCategory: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(1.dp, Color.Blue ),
        modifier = Modifier
            .size(120.dp, 50.dp)
            .background( color = if (selectedCategory == label) Color.Gray else Color.Transparent,
                shape = RoundedCornerShape(50)
            )
            .padding(8.dp),
        // contentPadding = PaddingValues(16.dp)
    ) {
        Text(text = label)
    }
}*/

@Composable
fun ShelterButton(
    label: String,
    selectedCategory: String,
    onClick: () -> Unit
) {
    val isSelected = selectedCategory == label
    val backgroundColor = if (isSelected) Color.Blue else Color.White
    //val borderColor = if (isSelected) Color.Transparent else Color.Blue // Hide border when selected

    Button(
        onClick = onClick,
        border = BorderStroke(1.dp, Color.Blue),
        colors = ButtonDefaults.buttonColors(backgroundColor),
        modifier = Modifier
            .size(120.dp, 50.dp)
            /*.background(
                color = backgroundColor,
              //  shape = RoundedCornerShape(50)
            )*/
            .padding(8.dp),
    ) {
        Text(text = label, color = if (isSelected) Color.White else Color.Blue)
    }
}

@Composable
fun RentalScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "Rental Screen")
    }
}

@Composable
fun MessagesScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "Messages Screen")
    }
}

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {

    }
}