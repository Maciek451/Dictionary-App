package uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.explore_dictionary

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.R
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model.DictEntry
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model.DictEntryViewModel
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.navigation.Screen
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.theme.Assignment_CS31620Theme

/**
 * This file creates "Explore Dictionary" screen
 *
 * @author Maciej Traczyk
 * @version 1.0 7 January 2023
 */

/**
 * Contains layout and code for "Explore dictionary" screen
 *
 * @param navController NavHostController
 * @param dictEntryViewModel viewModel interacts with database
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExploreDictionary(
    navController: NavHostController,
    dictEntryViewModel: DictEntryViewModel = viewModel()
) {
    //list of entries
    val dictEntries by dictEntryViewModel.dictEntries.observeAsState(listOf())
    var isDialogOpen by rememberSaveable { mutableStateOf(false) }
    var dictEntryId by rememberSaveable {
        mutableStateOf(0)
    }

    TopLevelScaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddWords.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add"
                )
            }
        },
        navController = navController
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (dictEntries.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, top = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Divider(startIndent = 8.dp, thickness = 1.dp)
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                )
                {
                    items(dictEntries) { entry ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(horizontalAlignment = Alignment.Start) {
                                Text(
                                    text = entry.word,
                                    modifier = Modifier.padding(
                                        start = 8.dp,
                                        top = 8.dp
                                    )
                                )
                                Text(
                                    text = entry.translation,
                                    fontSize = 20.sp,
                                    color = Color.Blue,
                                    modifier = Modifier.padding(
                                        start = 8.dp,
                                        bottom = 24.dp
                                    )
                                )
                            }
                            IconButton(onClick = {
                                isDialogOpen = true
                                dictEntryId = entry.id
                            }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = "Delete a word"
                                )
                            }
                        }
                    }
                }
            } else {
                EmptyDictionary()
            }
            ConfirmDialog2(
                dialogIsOpen = isDialogOpen,
                dialogOpen = { isOpen ->
                    isDialogOpen = isOpen
                },
                entry = DictEntry(id = dictEntryId)
            )
        }
    }
}

/**
 * Function to display empty list in dictionary
 *
 */
@Composable
private fun EmptyDictionary() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(top = 30.dp),
            text = stringResource(id = R.string.empty_dictionary),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            painter = painterResource(id = R.drawable.exploredictionaryimage),
            contentDescription = stringResource(R.string.explore_dictionary_image),
            contentScale = ContentScale.Crop
        )
    }
}

/**
 * Creates confirmation dialog for "Explore dictionary" screen
 *
 * @param dialogIsOpen checks if dialog is opened
 * @param dialogOpen variable for onClick action
 * @param dictEntryViewModel viewModel
 * @param entry variable to remove single word from dictionary
 */
@Composable
fun ConfirmDialog2(
    dialogIsOpen: Boolean,
    dialogOpen: (Boolean) -> Unit = {},
    dictEntryViewModel: DictEntryViewModel = viewModel(),
    entry: DictEntry
) {
    if (dialogIsOpen) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { /* Empty so clicking outside has no effect */ },
            title = {
                Text(
                    text = stringResource(id = R.string.click_to_confirm),
                    fontSize = 20.sp
                )
            },
            text = {

                Text(
                    text = stringResource(id = R.string.confirmation_text_single_word),
                    fontSize = 15.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        dialogOpen(false)
                        dictEntryViewModel.deleteDictEntry(entry)
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
 * Preview function for "Explore dictionary" screen
 *
 */
@Preview
@Composable
private fun ExploreDictionaryPreview() {
    val navController = rememberNavController()
    Assignment_CS31620Theme(dynamicColor = false) {
        ExploreDictionary(navController)
    }
}