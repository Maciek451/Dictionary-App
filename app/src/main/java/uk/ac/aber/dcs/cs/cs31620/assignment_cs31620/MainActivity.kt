package uk.ac.aber.dcs.cs.cs31620.assignment_cs31620

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.multiple_choice.MultipleChoiceScreen
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.add_words.AddWordsScreen
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.change_languages.ChangeLanguageScreen
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.explore_dictionary.ExploreDictionary
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.theme.Assignment_CS31620Theme
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.navigation.Screen
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.result.ResultScreenScreen
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.spelling_the_answer.SpellingTheAnswerScreen
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.start_screen.StartScreen
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.test_yourself.TestYourself

/**
 * MainActivity file
 *
 * @author Maciej Traczyk
 * @version 1.0 7 January 2023
 */

/**
 * To run the app
 *
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment_CS31620Theme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BuildNavigationGraph()
                }
            }
        }
    }
}

/**
 * Navigation Graph
 *
 */
@Composable
private fun BuildNavigationGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.StartScreen.route
    ) {
        composable(Screen.ExploreDictionary.route) { ExploreDictionary(navController) }
        composable(Screen.TestYourself.route) { TestYourself(navController) }
        composable(Screen.AddWords.route) { AddWordsScreen(navController) }
        composable(Screen.ChangeLanguages.route) { ChangeLanguageScreen(navController) }
        composable(Screen.SpellingTheAnswer.route) { SpellingTheAnswerScreen(navController) }
        composable(Screen.MultipleChoice.route) { MultipleChoiceScreen(navController) }
        composable(Screen.ResultScreen.route) { ResultScreenScreen(navController) }
        composable(Screen.StartScreen.route) { StartScreen(navController) }
    }
}

/**
 * Preview function for MainActivity
 *
 */
@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    Assignment_CS31620Theme(dynamicColor = false) {
        BuildNavigationGraph()
    }
}