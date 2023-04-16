package uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.start_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model.datastore.DataStore
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.change_languages.ChangeLanguageScreen
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.explore_dictionary.ExploreDictionary
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.theme.Assignment_CS31620Theme

/**
 * This file creates "Starting" screen witch appears first time running the app
 *
 * @author Maciej Traczyk
 * @version 1.0 7 January 2023
 */

/**
 * To display "Change languages" screen first time running the app
 *
 * @param navController NavHostController
 */
@Composable
fun StartScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val dataStore = DataStore(context)
    val firstTime = dataStore.getString("IS_FIRST_TIME").collectAsState(initial = "")
    if (firstTime.value == null || firstTime.value == "") {
        ChangeLanguageScreen(navController = navController)
    } else {
        ExploreDictionary(navController = navController)
    }
}