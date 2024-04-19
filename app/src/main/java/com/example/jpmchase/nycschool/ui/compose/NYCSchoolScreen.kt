package com.example.jpmchase.nycschool.ui.compose

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jpmchase.nycschool.R
import com.example.jpmchase.nycschool.api.data.DefaultAppContainer
import com.example.jpmchase.nycschool.api.model.NYCSchoolsData
import com.example.jpmchase.nycschool.api.model.NYCSchoolsSATScoreData
import com.example.jpmchase.nycschool.viewmodel.NYCSchoolViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NYCSchoolScreen(
    navController: NavController,
    closeApp:() -> Unit
) {

    val appContainer = DefaultAppContainer()
    val viewModel = NYCSchoolViewModel(appContainer.nycSchoolRepository)
    val nycSchoolsData = viewModel.nycSchoolsData.collectAsState()
    val displayProgressIndicator = viewModel.loading.collectAsState()
    var nycSchoolsSATScoreData = NYCSchoolsSATScoreData()
    val schoolName : MutableState<String> = remember { mutableStateOf("") }
    val displaySATScore = remember { mutableStateOf(false) }
    val displayToastMsg = remember { mutableStateOf(false) }

    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.nyc_schools),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                        closeApp()
                    }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                })
        },
    ) { it -> Box {
        Modifier
        .padding(it)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp)
        ) {
            NYCSchoolsListScreen(nycSchoolsList = nycSchoolsData.value,
                                 displayProgressIndicator = displayProgressIndicator) {
                val satScoreData = viewModel.filterNYCSchoolSATScore(it.dbn)
                if (satScoreData.isNotEmpty()) {
                    nycSchoolsSATScoreData = satScoreData[0]
                    displaySATScore.value = true
                } else {
                    displayToastMsg.value = true
                }
            }
        }
    }
    if (displaySATScore.value) {
        DisplaySATScores(displaySATScore, nycSchoolsSATScoreData)
    }
    if (displayToastMsg.value) {
        DisplayToastMessage(displayToastMsg = displayToastMsg)
    }
    }
}

@Composable
fun NYCSchoolsListScreen(
    nycSchoolsList: List<NYCSchoolsData>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    displayProgressIndicator: State<Boolean>,
    onSchoolClick:(NYCSchoolsData) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        DisplayProgressIndicator(displayProgressIndicator = displayProgressIndicator)

        LazyColumn(modifier = modifier.padding(top = 10.dp,
            start = 20.dp, end = 20.dp),
            contentPadding = contentPadding
        ) {
            items(
                count = nycSchoolsList.size,
                key = {
                    nycSchoolsList[it].dbn
                },
                itemContent = {
                    SchoolCard(it, nycSchoolsList) { schoolData ->
                        onSchoolClick(schoolData)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolCard (i: Int,
              nycSchoolsList: List<NYCSchoolsData>,
              onSchoolClick:(NYCSchoolsData) -> Unit) {
    Card(
        modifier = Modifier,
        onClick = {
            onSchoolClick(nycSchoolsList[i])
        }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.background(Color.White),
                text = nycSchoolsList[i].schoolName,
                color = Color.Black
            )
        }
    }
}

@Composable
fun DisplayProgressIndicator(
    displayProgressIndicator: State<Boolean>
) {
    if (displayProgressIndicator.value) {
        Box (
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun DisplayToastMessage(
    displayToastMsg: MutableState<Boolean>
) {
    if (displayToastMsg.value) {
        Toast.makeText(
            LocalContext.current, stringResource(id = R.string.no_data),
            Toast.LENGTH_SHORT
        ).show()
        displayToastMsg.value = false
    }
}

@Composable
fun DisplaySATScores(
    displaySATScore: MutableState<Boolean>,
    nycSchoolsSATScoreData: NYCSchoolsSATScoreData
) {
    if (displaySATScore.value) {
        AlertDialog(
            onDismissRequest = {
                displaySATScore.value = false
            },
            title = {
                Text(text = nycSchoolsSATScoreData.schoolName)
            },
            text = {
                Column {
                    Text(
                        text = stringResource(id = R.string.sat_score))
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        stringResource(id = R.string.reading_score,
                        nycSchoolsSATScoreData.satReadingAvgScore))
                    Text(
                        stringResource(id = R.string.math_score,
                            nycSchoolsSATScoreData.satMathAvgScore))
                    Text(
                        stringResource(id = R.string.writing_score,
                            nycSchoolsSATScoreData.satWritingAvgScore))
                }
            },
            confirmButton = {},
            dismissButton = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { displaySATScore.value = false }
                    ) {
                        Text(stringResource(id = R.string.close))
                    }
                }
            }
        )
    }
}

