package uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.add_words

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.R
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model.DictEntry
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model.DictEntryViewModel
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.navigation.Screen
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.theme.Assignment_CS31620Theme

/**
 * This file creates "Add words" screen
 *
 * @author Maciej Traczyk
 * @version 1.0 7 January 2023
 */

/**
 * Contains layout and code for "Add words" screen
 *
 * @param navController NavHostController
 * @param dictEntryViewModel viewModel
 */
@Composable
fun AddWordsScreen(
    navController: NavHostController,
    dictEntryViewModel: DictEntryViewModel = viewModel()
) {

    var word by remember {
        mutableStateOf("")
    }
    var translation by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.add_a_new_word),
            fontSize = 25.sp,
            modifier = Modifier.padding(top = 10.dp)
        )

        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.addscreenimage),
            contentDescription = stringResource(R.string.add_screen_image),
            contentScale = ContentScale.Crop
        )
        OutlinedTextField(
            value = word,
            label = {
                Text(text = stringResource(R.string.word))
            },
            onValueChange = { word = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = translation,
            label = {
                Text(text = stringResource(R.string.translation))
            },
            onValueChange = { translation = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.padding(40.dp))
        
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = { navController.navigate(Screen.ExploreDictionary.route) },
                modifier = Modifier
                    .padding(end = 8.dp)
            ) {
                Text(text = stringResource(id = R.string.cancel_button))
            }

            Button(
                onClick = {
                    val newEntry = DictEntry(
                        word = word,
                        translation = translation
                    )
                    dictEntryViewModel.insertDictEntry(newEntry)
                    navController.navigate(Screen.ExploreDictionary.route)
                },
                enabled = word.isNotEmpty() && translation.isNotEmpty()
            ) {
                Text(text = stringResource(id = R.string.add))
            }
        }
    }
}

/**
 * Preview function for "Add words" screen
 *
 */
@Composable
@Preview
private fun AddWordsScreenPreview() {
    val navController = rememberNavController()
    Assignment_CS31620Theme(dynamicColor = false) {
        AddWordsScreen(navController)
    }
}