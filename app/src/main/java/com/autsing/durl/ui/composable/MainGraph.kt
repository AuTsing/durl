package com.autsing.durl.ui.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.autsing.durl.navigation.MainDestination
import com.autsing.durl.navigation.Navigation
import com.autsing.durl.ui.theme.DurlTheme
import com.autsing.durl.viewmodel.RequestViewModel
import com.autsing.durl.viewmodel.ResponseViewModel

@Composable
fun MainGraph(
    requestViewModel: RequestViewModel = viewModel(),
    responseViewModel: ResponseViewModel = viewModel(),
) {
    val navController = rememberNavController()
    val navigation = remember(navController) {
        Navigation(
            navController = navController,
            navDestinations = MainDestination.list,
            startDestination = MainDestination.Request,
        )
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: navigation.getCurrentRoute()
    val context = LocalContext.current
    val requests by requestViewModel.requests.collectAsState()
    val responses by responseViewModel.responses.collectAsState()
    val unreadCount by responseViewModel.unreadCount.collectAsState()

    DurlTheme {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(MainDestination.Request.iconId),
                                contentDescription = "Request navigation",
                            )
                        },
                        label = { Text(MainDestination.Request.label) },
                        onClick = { navigation.navigateTo(MainDestination.Request.route) },
                        selected = currentRoute == MainDestination.Request.route,
                    )
                    NavigationBarItem(
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (unreadCount > 0) {
                                        Badge {
                                            Text(unreadCount.toString())
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(MainDestination.Response.iconId),
                                    contentDescription = "Response navigation",
                                )
                            }
                        },
                        label = { Text(MainDestination.Response.label) },
                        onClick = {
                            navigation.navigateTo(MainDestination.Response.route)
                            responseViewModel.handleResetUnreadCount()
                        },
                        selected = currentRoute == MainDestination.Response.route,
                    )
                }
            },
        ) { innerPadding ->
            Surface(modifier = Modifier.padding(innerPadding)) {
                NavHost(
                    navController = navController,
                    startDestination = navigation.getStartRoute(),
                ) {
                    composable(MainDestination.Request.route) {
                        RequestScreen(
                            requests = requests,
                            onClickAddRequest = { requestViewModel.handleClickAddRequest(context) },
                            onClickSendRequest = requestViewModel::handleClickSendRequest,
                            onClickRemoveRequest = requestViewModel::handleClickRemoveRequest,
                        )
                    }
                    composable(MainDestination.Response.route) {
                        ResponseScreen(
                            responses = responses,
                            onClickClearResponses = responseViewModel::handleClearResponses,
                        )
                    }
                }
            }
        }
    }
}
