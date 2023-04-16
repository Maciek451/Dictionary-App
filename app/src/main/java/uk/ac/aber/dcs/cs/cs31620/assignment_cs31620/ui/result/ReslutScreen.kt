package uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.R
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model.datastore.DataStore
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.navigation.Screen
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.ui.theme.Assignment_CS31620Theme

/**
 * This file creates "Results" screen
 *
 * @author Maciej Traczyk
 * @version 1.0 7 January 2023
 */

/**
 * Contains layout and code for "Results" screen
 *
 * @param navController NavHostController
 */
@Composable
fun ResultScreenScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val dataStore = DataStore(context)
    val quizResult = dataStore.getString(key = "RESULT").collectAsState(initial = "")

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.result),
            fontSize = 25.sp,
            modifier = Modifier.padding(all = 15.dp)
        )

        Text(
            text = quizResult.value!!,
            modifier = Modifier.padding(all = 15.dp),
            fontSize = 30.sp
        )

        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.resultimage),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.result_image)
        )

        Button(
            onClick = { navController.navigate(Screen.TestYourself.route) },
            modifier = Modifier.padding(top = 30.dp)
        ) {
            Text(text = stringResource(id = R.string.back_to_tests))
        }

    }
}

/**
 * Preview function for "Results" screen
 *
 */
@Composable
@Preview
private fun ResultScreenPreview() {
    val navController = rememberNavController()
    Assignment_CS31620Theme(dynamicColor = false) {
        ResultScreenScreen(navController)
    }
}