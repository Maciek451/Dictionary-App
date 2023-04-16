package uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.test_yourself

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.R
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model.datastore.DataStore
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model.DictEntryViewModel
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.multiple_choice.MultipleChoiceScreen
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.navigation.Screen
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.theme.Assignment_CS31620Theme

/**
 * This file creates "Test yourself" screen
 *
 * @author Maciej Traczyk
 * @version 1.0 7 January 2023
 */

/**
 * Contains layout and code for "Test yourself" screen
 *
 * @param navController NavHostController
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TestYourself(
    navController: NavHostController
) {
    TopLevelScaffold(
        navController = navController
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            testYourselfContent(
                navController
            )
        }
    }
}

/**
 * To hold content of "Test yourself" screen
 *
 * @param navController NavHostController
 * @param dictEntryViewModel viewModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun testYourselfContent(
    navController: NavHostController,
    dictEntryViewModel: DictEntryViewModel = viewModel()
) {
    val dictEntries by dictEntryViewModel.dictEntries.observeAsState(listOf())
    var pos by rememberSaveable { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val dataStore = DataStore(context)
    var maxNumQuestions by rememberSaveable { mutableStateOf(0f) }
    maxNumQuestions = if (dictEntries.isNotEmpty()) {
        dictEntries.size.toFloat()
    } else {
        0f
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(260.dp),
            painter = painterResource(id = R.drawable.testyourselfimage),
            contentDescription = stringResource(R.string.test_yourself_image),
            contentScale = ContentScale.Crop
        )
        Text(
            text = String.format(stringResource(id = R.string.number_of_questions), pos.toInt())
        )
        Slider(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp),
            value = pos ,
            onValueChange = { pos = it },
            valueRange = 0f..maxNumQuestions
        )

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Card(
                modifier = Modifier
                    .height(200.dp)
                    .width(180.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.spelling_the_answer),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(all = 8.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.spelling_the_answer_description),
                        modifier = Modifier.padding(all = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            scope.launch {
                                dataStore.saveInt(pos.toInt(), key = "NUMBER_OF_QUESTIONS")
                            }
                            navController.navigate(Screen.SpellingTheAnswer.route)
                                  },
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .size(width = 90.dp, height = 40.dp),
                        enabled = pos >= 1
                    ) {
                        Text(text = stringResource(id = R.string.begin_button))
                    }
                }
            }
            Card(
                modifier = Modifier
                    .height(200.dp)
                    .width(180.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.multiple_choice),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(all = 8.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.multiple_choice_description),
                        modifier = Modifier.padding(all = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            scope.launch {
                                dataStore.saveInt(pos.toInt(), key = "NUMBER_OF_QUESTIONS")
                            }
                            navController.navigate(Screen.MultipleChoice.route)
                                  },
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .size(width = 90.dp, height = 40.dp),
                        enabled = pos >= 4
                    ) {
                        Text(text = stringResource(id = R.string.begin_button))
                    }
                }
            }
        }
    }
}

/**
 * Preview function for "Test yourself" screen
 *
 */
@Preview
@Composable
fun TestYourselfPreview() {
    val navController = rememberNavController()
    Assignment_CS31620Theme(dynamicColor = false) {
        TestYourself(navController)
    }
}