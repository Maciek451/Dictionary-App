package uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.R
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model.datastore.DataStore
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model.DictEntryViewModel
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.navigation.Screen
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.theme.Assignment_CS31620Theme

/**
 * This file creates Navigation Drawer
 *
 * @author Maciej Traczyk
 * @version 1.0 7 January 2023
 */

/**
 * Contains layout and code for Navigation Drawer
 *
 * @param navController NavHostController
 * @param drawerState DrawerState
 * @param closeDrawer to hold Drawer
 * @param content to hold content
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyNavigationDrawer(
    navController: NavHostController,
    drawerState: DrawerState,
    closeDrawer: () -> Unit = {},
    content: @Composable () -> Unit = {}

) {

    var isDialogOpen by rememberSaveable { mutableStateOf(false) }
    val items = listOf(
        Pair(
            Icons.Default.CollectionsBookmark,
            stringResource(R.string.change_languages_button)
        ),
        Pair(
            Icons.Default.DeleteForever,
            stringResource(R.string.clear_dict_button)
        )
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            val selectedItem = rememberSaveable { mutableStateOf(0) }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
            ) {
                val context = LocalContext.current
                val dataStore = DataStore(context)

                val language1 =
                    dataStore.getString(key = "MY_LANGUAGE").collectAsState(initial = "")
                val language2 =
                    dataStore.getString(key = "FOREIGN_LANGUAGE").collectAsState(initial = "")

                Text(
                    modifier = Modifier.padding(all = 10.dp),
                    fontSize = 20.sp,
                    text = stringResource(id = R.string.your_first_language)
                )
                Text(
                    modifier = Modifier.padding(all = 10.dp),
                    color = Color.Blue,
                    fontSize = 30.sp,
                    text = language1.value!!
                )
                Text(
                    modifier = Modifier.padding(all = 10.dp),
                    fontSize = 20.sp,
                    text = stringResource(id = R.string.your_second_language)
                )
                Text(
                    modifier = Modifier.padding(all = 10.dp),
                    color = Color.Blue,
                    fontSize = 30.sp,
                    text = language2.value!!
                )

                Divider(
                    modifier = Modifier.padding(bottom = 15.dp),
                    thickness = 1.dp
                )

                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                imageVector = item.first,
                                contentDescription = item.second
                            )
                        },
                        label = { Text(item.second) },
                        selected = index == selectedItem.value,
                        onClick = {
                            selectedItem.value = index
                            if (index == 0) {
                                navController.navigate(route = Screen.ChangeLanguages.route)
                                closeDrawer()
                            } else if (index == 1) {
                                isDialogOpen = true
                                closeDrawer()
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.padding(40.dp))
                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = painterResource(id = R.drawable.navdrawerimage),
                    contentDescription = stringResource(R.string.nav_drawer_image),
                    contentScale = ContentScale.Crop
                )
            }
        },
        content = content
    )
    ConfirmDialog(
        dialogIsOpen = isDialogOpen,
        dialogOpen = { isOpen ->
            isDialogOpen = isOpen
        }
    )
}

/**
 * Creates confirmation dialog for Navigation Drawer
 *
 * @param dialogIsOpen checks if dialog is opened
 * @param dialogOpen variable for onClick action
 * @param dictEntryViewModel viewModel
 */
@Composable
fun ConfirmDialog(
    dialogIsOpen: Boolean,
    dialogOpen: (Boolean) -> Unit = {},
    dictEntryViewModel: DictEntryViewModel = viewModel()
) {
    if (dialogIsOpen) {
        AlertDialog(
            onDismissRequest = { /* Empty so clicking outside has no effect */ },
            title = {
                Text(
                    text = stringResource(R.string.click_to_confirm),
                    fontSize = 20.sp
                )
            },
            text = {

                Text(
                    text = stringResource(R.string.confirmation_text),
                    fontSize = 15.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        dialogOpen(false)
                        dictEntryViewModel.deleteAllEntries()
                    }
                ) {
                    Text(stringResource(R.string.confirm_button))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dialogOpen(false)
                    }
                ) {
                    Text(stringResource(R.string.cancel_button))
                }
            }
        )
    }
}

/**
 * Preview function for Navigation Drawer
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun MyNavigationDrawerPreview() {
    Assignment_CS31620Theme(dynamicColor = false) {
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
        MyNavigationDrawer(navController, drawerState)
    }
}