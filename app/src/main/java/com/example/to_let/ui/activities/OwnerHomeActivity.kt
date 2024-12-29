package com.example.to_let.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.util.Property
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.to_let.ui.theme.ToLetTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RequestPage
import androidx.compose.material.icons.outlined.AddHome
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RequestPage
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.to_let.ProfileScreen
import com.example.to_let.PropertyAddScreen
import com.example.to_let.PropertyEditScreen
import com.example.to_let.R
import com.example.to_let.model.Messages
import com.example.to_let.model.NavigationItem
import com.example.to_let.model.Owner
import com.example.to_let.model.Tenant
import com.example.to_let.model.Properties
import com.example.to_let.model.RentalRequest
import com.example.to_let.model.ShelterData
import com.example.to_let.model.UserProfile
import com.example.to_let.ui.auth.UserVerifyActivity
import com.example.to_let.viewmodel.PropertyViewModel
import com.example.to_let.viewmodel.SplashViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class OwnerHomeActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        val role = intent.getStringExtra("ROLE")

        setContent {
            ToLetTheme {
                val navController = rememberNavController()
                val items = listOf(
                    NavigationItem(
                        title = "Home",
                        route = "home",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                    ),
                    NavigationItem(
                        title = "Add Property",
                        route = "add_property",
                        selectedIcon = Icons.Filled.AddHome,
                        unselectedIcon = Icons.Outlined.AddHome,
                    ),
                    NavigationItem(
                        title = "Rental Requests",
                        route = "rental_requests",
                        selectedIcon = Icons.Filled.RequestPage,
                        unselectedIcon = Icons.Outlined.RequestPage,
                        badgeCount = 45
                    ),
                    NavigationItem(
                        title = "Messages",
                        route = "messages",
                        selectedIcon = Icons.Filled.Message,
                        unselectedIcon = Icons.Outlined.Message,
                    ),
                    NavigationItem(
                        title = "Profile",
                        route = "profile",
                        selectedIcon = Icons.Filled.Person,
                        unselectedIcon = Icons.Outlined.Person,
                    ),
                    NavigationItem(
                        title = "Help",
                        route = "help",
                        selectedIcon = Icons.Filled.Help,
                        unselectedIcon = Icons.Outlined.Help,
                    ),
                    NavigationItem(
                        title = "Logout",
                        route = "logout",
                        selectedIcon = Icons.Filled.Logout,
                        unselectedIcon = Icons.Outlined.Logout,
                    ),
                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    //color = MaterialTheme.colorScheme.background
                    color = Color.Blue
                ) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    var selectedItemIndex by rememberSaveable {
                        mutableStateOf(0)
                    }
                    val context = LocalContext.current
                    val versionName = try {
                        context.packageManager.getPackageInfo(context.packageName, 0).versionName
                    } catch (e: Exception) {
                        "Unknown"
                    }
                    ModalNavigationDrawer(
                        drawerContent = {
                            ModalDrawerSheet {
                                Spacer(modifier = Modifier.height(16.dp))
                                items.forEachIndexed { index, item ->
                                    NavigationDrawerItem(
                                        label = {
                                            Text(text = item.title)
                                        },
                                        selected = index == selectedItemIndex,
                                        onClick = {
                                            navController.navigate(item.route!!) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState =
                                                        true // Clear all back stack entries up to the start destination
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                            selectedItemIndex = index
                                            scope.launch {
                                                drawerState.close()
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = if (index == selectedItemIndex) {
                                                    item.selectedIcon
                                                } else item.unselectedIcon,
                                                contentDescription = item.title
                                            )
                                        },
                                        badge = {
                                            item.badgeCount?.let {
                                                Text(text = item.badgeCount.toString())
                                            }
                                        },
                                        modifier = Modifier
                                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                                    )
                                }
                                // Display the app version at the bottom
                                Spacer(modifier = Modifier.weight(1f))  // Pushes the version text to the bottom
                                Text(
                                    text = "App Version: $versionName",
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .align(CenterHorizontally)
                                )
                            }
                        },
                        drawerState = drawerState
                    ) {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = {
                                        Text(
                                            text = "To-Let App",
                                            Modifier.fillMaxWidth(),
                                            //textAlign = TextAlign.Center,
                                        )
                                    },
                                    navigationIcon = {
                                        IconButton(onClick = {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.Menu,
                                                contentDescription = "Menu"
                                            )
                                        }
                                    },
                                )
                            }
                        ) {
                            OwnerNavigation(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OwnerNavigation(navController: NavHostController) {
    val context = LocalContext.current
    var logoutState by remember { mutableStateOf(false) }
    val viewModel: PropertyViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            OwnerHomeScreen("Gokul",
                listOf(
                    Tenant("Gokul Kannan", "PG", true),
                    Tenant("Alice Smith", "House", false),
                    Tenant("Bob Johnson", "Hotel", true),
                    Tenant("Jane Roe", "Rooms", false),
                    Tenant("Jane Roe", "Rooms", false)
                ),
                onAddPropertyClick = { null })
        }
        composable("add_property") {
            AddPropertyScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable("property_add") {
            PropertyAddScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable("property_edit/{propertyId}") {
                backStackEntry ->
            val propertyId = backStackEntry.arguments?.getString("propertyId") ?: return@composable
            PropertyEditScreen(navController, propertyId, viewModel)
            /*PropertyEditScreen(
                navController: navController,
                propertyId: String, // We are passing property id for navigation
                viewModel= viewModel
            )*/
        }
        composable("map_screen") {
            val resultLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result.data?.getParcelableExtra<LatLng>("selected_location")?.let {
                        // Handle the selected location
                      //  mapLocation = "Lat: ${it.latitude}, Long: ${it.longitude}"
                    }
                }
            }

            //MapScreen(resultLauncher = resultLauncher)
        }
        composable("rental_requests") {
            RentalRequestScreen(
                listOf(
                    RentalRequest(
                        "Request for Room A",
                        "1/758, vennampatti, dharmapuri",
                        "Tenant asks about availability."
                    ),
                    RentalRequest(
                        "Request for House B",
                        "2/78, bangalaore, karnataka",
                        "Tenant inquired about rent."
                    )
                )
            )
        }
        composable("messages") {
            OwnerMessagesScreen(
                listOf(
                    Messages("Rented", "Tenant John rented your House."),
                    Messages("Vacated", "Tenant Bob vacated the PG."),
                    Messages("Payment", "Payment for Room A was received.")
                )
            )
        }
        composable("profile") {
            /*OwnerProfileScreen(
                Owner(
                    "Gokul",
                    "gokul.gmail.com",
                    "+91 657858756",
                    listOf()
                )
            )*/
            ProfileScreen()
        }
        composable("help") {
            OwnerHelpScreen()
        }
        composable("logout") {
            var showLogoutDialog by remember { mutableStateOf(false) }

            if (showLogoutDialog) {
                LogoutDialog(navController = navController, onCancel = {
                    showLogoutDialog = false
                })
            } else {
                // Trigger the dialog when this composable is reached
                showLogoutDialog = true
            }
        }
    }
}

@Composable
fun LogoutDialog(navController: NavHostController, onCancel: () -> Unit) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = { onCancel() },
        title = { Text("Logout") },
        text = { Text("Are you sure you want to logout?") },
        confirmButton = {
            TextButton(onClick = {
                /*navController.navigate("user_verify") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true // Clear back stack and start fresh
                    }
                }*/
                val intent = Intent(context, UserVerifyActivity::class.java)
                context.startActivity(intent)
            }) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text("No")
            }
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerHomeScreen(
    userName: String,
    tenants: List<Tenant>,
    onAddPropertyClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Welcome, $userName!") })
        }
    ) {
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
                        text = "Your Guests",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        style = TextStyle(fontSize = 20.sp, color = Color.Gray)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier
                        //.padding(innerPadding)
                        .fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        //  Text("Your Tenants", style = MaterialTheme.typography.headlineSmall)
                    }
                    items(tenants) { tenant ->
                        TenantCard(tenant)
                    }
                }
            }
        }
    }
}

@Composable
fun TenantCard(tenant: Tenant) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(tenant.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Property: ${tenant.propertyType}", style = MaterialTheme.typography.bodyMedium)
            Text(
                "Rent Status: ${if (tenant.rentPaid) "Paid" else "Unpaid"}",
                style = MaterialTheme.typography.bodyMedium
            )
            // Add more tenant details as needed
            Button(onClick = { /* Handle rent status change */ }) {
                Text(text = if (tenant.rentPaid) "Rent Paid" else "Rent Unpaid")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPropertyScreen(
    navController: NavHostController,
    viewModel: PropertyViewModel = viewModel()
) {
    val properties = viewModel.properties.value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("property_add") }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Property")
            }
        },
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Your Properties") })
        }
    ) { innerPadding ->
        if (properties.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Add Property",
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Add your first property", style = MaterialTheme.typography.bodyLarge)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(properties) { property ->
                    PropertyCard(property, navController)
                }
            }
        }
    }
}

@Composable
fun PropertyCard(property: Properties, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("property_edit/${property.id}")
            }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(property.address, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Status: ${if (property.occupied) "Occupied" else "Unoccupied"}",
                style = MaterialTheme.typography.bodyMedium
            )
            // Add more property details and a button for changing occupancy status
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalRequestScreen(requests: List<RentalRequest>) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Rental Requests") })
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(requests) { request ->
                RentalRequestCard(request)
            }
        }
    }
}

@Composable
fun RentalRequestCard(request: RentalRequest) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(request.tenantName, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Property: ${request.propertyAddress}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text("Message: ${request.message}", style = MaterialTheme.typography.bodyMedium)
            // Add more request details and actions
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerMessagesScreen(messages: List<Messages>) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Important Messages") })
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                MessageCard(message)
            }
        }
    }
}

@Composable
fun MessageCard(message: Messages) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(message.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(message.content, style = MaterialTheme.typography.bodyMedium)
            // Add more message details and actions
        }
    }
}

/*@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OwnerProfileScreen(owner: Owner) {
    var userProfile by remember {
        mutableStateOf(
            UserProfile(
                imageUrl = R.drawable.ic_me,
                username = "Gokulkannan G",
                phoneNumber = "+91 6345658778",
                location = "Dharmapuri",
                knownLanguages = listOf("Tamil", "English"),
                dob = "01-01-1990"
            )
        )
    }
    androidx.compose.material.Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp), contentAlignment = Alignment.Center
        ) {
            var showLanguagesDialog by remember { mutableStateOf(false) }
            var selectedLanguages by remember { mutableStateOf(userProfile.knownLanguages) }

            var showLogoutConfirmation by remember { mutableStateOf(false) }

            Column(modifier = Modifier.fillMaxSize()) {
                // Image Card with Blur Effect
                ImageWithBlur(imageUrl = userProfile.imageUrl)

                // Profile Information Section
                ProfileInfo(
                    username = userProfile.username,
                    location = userProfile.location,
                    phoneNumber = userProfile.phoneNumber,
                    knownLanguages = selectedLanguages,
                    dob = userProfile.dob,
                    onLanguagesSelect = {
                        selectedLanguages = it
                    }
                )
            }
        }
    }
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerHelpScreen() {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Help") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:7483689575"))
                context.startActivity(intent)
            }) {
                Text("Contact Developer")
            }
        }
    }
}