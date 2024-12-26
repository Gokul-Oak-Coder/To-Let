package com.example.to_let.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.to_let.ui.theme.ToLetTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.to_let.model.NavigationItem
import kotlinx.coroutines.launch

class OwnerHomeActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ToLetTheme {
                val navController = rememberNavController()
                val items = listOf(
                    NavigationItem(
                        title = "Home",
                        route ="home",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                    ),
                    NavigationItem(
                        title = "Add Property",
                        route ="add_property",
                        selectedIcon = Icons.Filled.AddHome,
                        unselectedIcon = Icons.Outlined.AddHome,
                    ),
                    NavigationItem(
                        title = "Rental Requests",
                        route ="rental_requests",
                        selectedIcon = Icons.Filled.RequestPage,
                        unselectedIcon = Icons.Outlined.RequestPage,
                        badgeCount = 45
                    ),
                    NavigationItem(
                        title = "Messages",
                        route ="messages",
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
                                            navController.navigate(item.route!!){
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true // Clear all back stack entries up to the start destination
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
                            }
                        },
                        drawerState = drawerState
                    ) {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = {
                                        Text(text = "Todo App")
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
                                    }
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
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            OwnerHomeScreen()
        }
        composable("add_property") {
            AddPropertyScreen()
        }
        composable("rental_requests") {
            RentalRequestScreen()
        }
        composable("messages") {
            OwnerMessagesScreen()
        }
        composable("profile") {
            OwnerProfileScreen()
        }
        composable("help") {
            OwnerHelpScreen()
        }
        composable("logout") {
            Toast.makeText(context, "Clicked on Logout", Toast.LENGTH_SHORT).show()
        }
    }
}
@Composable
fun OwnerHomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "Home Screen")
    }
}

@Composable
fun AddPropertyScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "Add Property")
    }
}

@Composable
fun RentalRequestScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "Rental Request")
    }
}

@Composable
fun OwnerMessagesScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "Message")
    }
}

@Composable
fun OwnerProfileScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "Profile")
    }
}

@Composable
fun OwnerHelpScreen(){
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "Profile")
    }
}