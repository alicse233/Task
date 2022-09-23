package com.example.taskapp.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.taskapp.R
import com.example.taskapp.data.local.ShortURL
import com.example.taskapp.presentation.ui.theme.Cyan
import com.example.taskapp.presentation.ui.theme.GrayishViolet

@Composable
fun IllustrationHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(60.dp))

        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "logo")

        Spacer(modifier = Modifier.height(12.dp))

        Image(
            painter = painterResource(id = R.drawable.illustration),
            contentDescription = "logo"
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Let's get you started", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Paste your first link into\n" +
                    "the field to shorten it",
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun UrlEntry(
    shortURL: ShortURL,
    onActionClick: (type: String, shortUrl: ShortURL) -> Unit,
) {

    Card(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {

                Text(
                    text = shortURL.original_link,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f),
                    maxLines = 1
                )

                Icon(
                    painter = painterResource(id = R.drawable.del),
                    contentDescription = "delete",
                    Modifier.clickable {
                        onActionClick.invoke("delete", shortURL)
                    }
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(GrayishViolet)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
            )

            Text(
                text = shortURL.full_short_link,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                maxLines = 1,
                color = Cyan
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
            )

            Button(
                onClick = { onActionClick.invoke("copy", shortURL) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (shortURL.isCopied) GrayishViolet else Cyan,
                    contentColor = Color.White,
                ),
                modifier = Modifier
                    .padding(horizontal = 28.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = if (shortURL.isCopied) "copied!" else "copy",
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            )

        }


    }
}