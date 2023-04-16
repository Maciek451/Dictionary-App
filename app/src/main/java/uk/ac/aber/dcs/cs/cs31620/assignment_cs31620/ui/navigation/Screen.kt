package uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.navigation

/**
 * This class menages all screens in the program
 *
 * @author Maciej Traczyk
 * @version 1.0 7 January 2023
 */

/**
 * To navigate through screens
 *
 * @property route
 */
sealed class Screen(
    val route: String
)   {
    object ExploreDictionary : Screen("explore_dictionary")
    object TestYourself : Screen("test_yourself")
    object AddWords : Screen("add_words")
    object ChangeLanguages : Screen("change_languages")
    object SpellingTheAnswer : Screen("spelling_the_answer")
    object MultipleChoice : Screen("multiple_choice")
    object ResultScreen : Screen("result_screen")
    object StartScreen : Screen("start_screen")
}
val screens = listOf(
    Screen.ExploreDictionary,
    Screen.TestYourself,
)
