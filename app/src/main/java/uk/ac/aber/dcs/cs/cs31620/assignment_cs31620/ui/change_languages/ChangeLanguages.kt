package uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.change_languages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.R
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model.datastore.DataStore
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model.DictEntryViewModel
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.navigation.Screen
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.theme.Assignment_CS31620Theme

/**
 * This file creates "Change languages" screen
 *
 * @author Maciej Traczyk
 * @version 1.0 7 January 2023
 */

/**
 * Contains layout and code for "Change languages" screen
 *
 * @param navController NavHostController
 */
@Composable
fun ChangeLanguageScreen(
    navController: NavHostController
) {
    var myLanguage by remember {
        mutableStateOf("")
    }
    var foreignLanguage by remember {
        mutableStateOf("")
    }
    var isDialogOpen by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val dataStore = DataStore(context)
    val firstTimeVal = dataStore.getString("IS_FIRST_TIME").collectAsState(initial = "")
    val firstTime = firstTimeVal.value?.isEmpty() == true || firstTimeVal.value == null
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .size(300.dp)
                .padding(bottom = 16.dp, top = 16.dp),
            painter = painterResource(id = R.drawable.navigationdrawerimage),
            contentDescription = stringResource(R.string.nav_drawer_image),
            contentScale = ContentScale.Crop
        )

        OutlinedTextField(
            value = myLanguage,
            label = {
                Text(text = stringResource(id = R.string.your_language))
            },
            onValueChange = { myLanguage = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
        )

        OutlinedTextField(
            value = foreignLanguage,
            label = {
                Text(text = stringResource(id = R.string.foreign_language))
            },
            onValueChange = { foreignLanguage = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
        )

        Spacer(modifier = Modifier.padding(30.dp))

        Button(
            onClick = {
                if (firstTime) {
                    scope.launch {
                        dataStore.saveString("NOT_FIRST_TIME", "IS_FIRST_TIME")
                        dataStore.saveString(name = myLanguage, key = "MY_LANGUAGE")
                        dataStore.saveString(name = foreignLanguage, key = "FOREIGN_LANGUAGE")
                    }
                } else {
                    isDialogOpen = true
                }
            },
            modifier = Modifier
                .padding(top = 8.dp),
            enabled = foreignLanguage.isNotEmpty() && myLanguage.isNotEmpty() && myLanguage != foreignLanguage
        ) {
            Text(text = stringResource(id = R.string.save_your_language))
        }

        Button(
            onClick = {
                navController.navigate(Screen.ExploreDictionary.route)
            },
            enabled = !firstTime,
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            Text(text = stringResource(id = R.string.cancel_button))
        }
    }
    ConfirmDialog(
        navController = navController,
        myLanguage = myLanguage,
        foreignLanguage = foreignLanguage,
        dialogIsOpen = isDialogOpen,
        dialogOpen = { isOpen ->
            isDialogOpen = isOpen
        }
    )
}

/**
 * Creates confirmation dialog for "Change languages" screen
 *
 * @param dialogIsOpen checks if dialog is opened
 * @param dialogOpen variable for onClick action
 * @param dictEntryViewModel viewModel
 * @param navController NavHostController
 * @param myLanguage first language that user provides
 * @param foreignLanguage second language that user provides
 */
@Composable
fun ConfirmDialog(
    dialogIsOpen: Boolean,
    dialogOpen: (Boolean) -> Unit = {},
    dictEntryViewModel: DictEntryViewModel = viewModel(),
    navController: NavHostController,
    myLanguage: String,
    foreignLanguage: String
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = DataStore(context)
    if (dialogIsOpen) {
        AlertDialog(
            onDismissRequest = { /* Empty so clicking outside has no effect */ },
            title = {
                Text(
                    text = stringResource(id = R.string.click_to_confirm),
                    fontSize = 20.sp
                )
            },
            text = {

                Text(
                    text = stringResource(id = R.string.confirmation_text),
                    fontSize = 15.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        dialogOpen(false)
                        scope.launch {
                            dataStore.saveString(name = myLanguage, key = "MY_LANGUAGE")
                            dataStore.saveString(name = foreignLanguage, key = "FOREIGN_LANGUAGE")
                        }
                        dictEntryViewModel.deleteAllEntries()
                        navController.navigate(Screen.ExploreDictionary.route)
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

@Composable
@Preview
private fun ChangeLanguagePreview() {
    val navController = rememberNavController()
    Assignment_CS31620Theme(dynamicColor = false) {
        ChangeLanguageScreen(navController)
    }
}