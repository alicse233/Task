package com.example.taskapp.presentation.ui.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskapp.R
import com.example.taskapp.common.copyToClipboard
import com.example.taskapp.presentation.ui.theme.*

@Composable
fun HomeScreenUI(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val list by homeViewModel.localUrlEntries.observeAsState()

    var urlStr by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Box(
        Modifier
            .fillMaxSize()
            .background(OffWhite)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
            ) {

                if (list?.size == 0) {

                    IllustrationHeader()

                } else {

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = "Your Link History",
                        fontWeight = FontWeight.Medium,
                        fontSize = 17.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyColumn() {
                        list?.forEach { entry ->
                            item {
                                UrlEntry(entry) { type, entry ->
                                    when (type) {
                                        "delete" -> {
                                            homeViewModel.deleteEntryInDB(entry)
                                        }
                                        "copy" -> {
                                            context.copyToClipboard(entry.full_short_link)
                                            homeViewModel.updateEntryInDB(entry.copy(isCopied = true))
                                        }
                                    }

                                }
                            }
                        }
                    }

                }

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.25f)
                    .background(GrayishViolet)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.shape),
                    contentDescription = "shape",
                    modifier = Modifier.align(Alignment.TopEnd)
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {

                    TextField(
                        value = urlStr, onValueChange = { urlStr = it },
                        modifier = Modifier
                            .padding(horizontal = 36.dp)
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(4.dp)),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        placeholder = {
                            Text(
                                text = "shorten a link here!",
                                Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = GrayLight
                            )
                        },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = {
                            homeViewModel.convertUrl(urlStr)
                        },
                        modifier = Modifier
                            .padding(horizontal = 36.dp)
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Cyan,
                            contentColor = Color.White
                        ),
                    ) {
                        Text(text = "SHORTEN IT!", fontSize = 17.sp, fontWeight = FontWeight.Medium)
                    }

                }

            }

        }

        if (homeViewModel.uiState.value.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (homeViewModel.uiState.value.error != null) {
            Toast.makeText(context, homeViewModel.uiState.value.error, Toast.LENGTH_SHORT).show()
            homeViewModel.onResultConsumed()
        }

        if (homeViewModel.uiState.value.message != null) {
            urlStr = ""
            homeViewModel.onResultConsumed()
        }

    }
}