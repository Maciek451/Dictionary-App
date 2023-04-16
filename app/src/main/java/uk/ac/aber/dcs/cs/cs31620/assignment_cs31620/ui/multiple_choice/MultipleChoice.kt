package uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.multiple_choice

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import kotlin.random.Random

/**
 * This file creates "Multiple choice" screen
 *
 * @author Maciej Traczyk
 * @version 1.0 7 January 2023
 */

/**
 * Contains layout and code for "Multiple choice" screen
 *
 * @param navController NavHostController
 * @param dictEntryViewModel viewModel
 */
@Composable
fun MultipleChoiceScreen(
    navController: NavHostController,
    dictEntryViewModel: DictEntryViewModel = viewModel()
) {
    val dictEntries by dictEntryViewModel.dictEntries.observeAsState(listOf())
    val context = LocalContext.current
    val dataStore = DataStore(context)
    var correctTranslation by remember {
        mutableStateOf("")
    }
    var question by remember {
        mutableStateOf("")
    }
    var firstQuestion by remember {
        mutableStateOf(true)
    }
    var questionNumber by remember {
        mutableStateOf(1)
    }

    var answer by remember {
        mutableStateOf("")
    }
    val maxNumQuestions = dataStore.getInt("NUMBER_OF_QUESTIONS").collectAsState(initial = 0)

    val radioItems = rememberSaveable { mutableListOf<String>() }

    var (selectedOption, onOptionSelected) = remember {
        mutableStateOf("")
    }

    val indexes = rememberSaveable { mutableListOf<Int>() }
    val answersIndexes = rememberSaveable { mutableListOf<Int>() }

    if (dictEntries.isNotEmpty() && firstQuestion) {
        val size = dictEntries.size
        while (indexes.size < maxNumQuestions.value!!) {
            val x = Random.nextInt(0, size)
            if (x !in indexes) {
                indexes.add(x)
            }
        }
        val index = indexes[questionNumber - 1]
        correctTranslation = dictEntries[index].translation
        question = dictEntries[index].word
        radioItems.add(correctTranslation)
        firstQuestion = false

        var i = 0
        val shuffledValues = (dictEntries.indices).toMutableList()
        shuffledValues.shuffle()

        while (answersIndexes.size < 3) {
            val temp = shuffledValues[i]
            if (temp != index && !answersIndexes.contains(temp)) {
                answersIndexes.add(temp)
                radioItems.add(dictEntries[temp].translation)
            }
            i++
        }
        radioItems.shuffle()
    }
    var score by remember {
        mutableStateOf(0)
    }
    val scope = rememberCoroutineScope()
    var isDialogOpen by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            stringResource(id = R.string.pick_answer),
            fontSize = 25.sp,
            modifier = Modifier.padding(all = 10.dp)
        )

        Text(
            question,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 50.dp)
        )

        Spacer(modifier = Modifier.padding(30.dp))

        Column() {
            for (radioItem in radioItems) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (radioItem == selectedOption),
                        onClick = {
                            onOptionSelected(radioItem)
                            answer = radioItem
                            answer = selectedOption
                        }
                    )
                    .padding(horizontal = 16.dp)
                ) {
                    RadioButton(
                        selected = (radioItem == selectedOption),
                        onClick = {
                            onOptionSelected(radioItem)
                            answer = radioItem
                        }
                    )
                    Text(
                        text = radioItem,
                        style = MaterialTheme.typography.body1.merge(),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(100.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = { isDialogOpen = true },
                modifier = Modifier
                    .padding(all = 10.dp)
            ) {
                Text(text = stringResource(id = R.string.cancel_button))
            }

            Button(
                onClick = {
                    radioItems.clear()
                    if (answer == correctTranslation) {
                        score++
                    }
                    if (questionNumber >= maxNumQuestions.value!!) {
                        //score
                        val result = "$score/${maxNumQuestions.value!!}"
                        scope.launch {
                            dataStore.saveString(name = result, key = "RESULT")
                        }
                        //save result using saveString
                        navController.navigate(Screen.ResultScreen.route)
                    } else {
                        answersIndexes.clear()
                        questionNumber++
                        val index = indexes[questionNumber - 1]
                        correctTranslation = dictEntries[index].translation
                        question = dictEntries[index].word
                        radioItems.add(correctTranslation)
                        firstQuestion = false

                        var i = 0
                        val shuffledValues = (dictEntries.indices).toMutableList()
                        shuffledValues.shuffle()
                        radioItems.shuffle()

                        while (answersIndexes.size < 3) {
                            val temp = shuffledValues[i]
                            if (temp != index && !answersIndexes.contains(temp)) {
                                answersIndexes.add(temp)
                                radioItems.add(dictEntries[temp].translation)
                            }
                            i++
                        }
                        radioItems.shuffle()
                        answer = ""
                        selectedOption = ""
                        onOptionSelected(answer)
                    }
                },
                modifier = Modifier
                    .padding(all = 10.dp),
                enabled = answer.isNotEmpty()
            ) {
                Text(stringResource(id = R.string.next_button))
            }
        }
        ConfirmDialog(
            navController = navController,
            dialogIsOpen = isDialogOpen,
            dialogOpen = { isOpen ->
                isDialogOpen = isOpen
            }
        )
    }
}

/**
 * Creates confirmation dialog for "Multiple Choice" screen
 *
 * @param dialogIsOpen checks if dialog is opened
 * @param dialogOpen variable for onClick action
 * @param navController NavHostController
 */
@Composable
fun ConfirmDialog(
    dialogIsOpen: Boolean,
    dialogOpen: (Boolean) -> Unit = {},
    navController: NavHostController
) {
    if (dialogIsOpen) {
        AlertDialog(
            onDismissRequest = { /* Empty so clicking outside has no effect */ },
            title = {
                Text(
                    text = stringResource(id = R.string.finish_text),
                    fontSize = 20.sp
                )
            },
            text = {

                Text(
                    text = stringResource(id = R.string.lose_data),
                    fontSize = 15.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        dialogOpen(false)
                        navController.navigate(Screen.TestYourself.route)
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
 * Preview function for "Multiple choice" screen
 *
 */
@Preview
@Composable
fun MultipleChoicePreview() {
    val navController = rememberNavController()
    Assignment_CS31620Theme(dynamicColor = false) {
        MultipleChoiceScreen(navController)
    }
}