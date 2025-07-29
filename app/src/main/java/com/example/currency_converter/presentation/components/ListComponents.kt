package com.example.currency_converter.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.currency_converter.R
import com.example.currency_converter.presentation.theme.LocalTheme

@Composable
fun ListComponents(
    code: String,
    name: String,
    onClick: () -> Unit
) {
    val theme = LocalTheme.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp)
    ) {

        AsyncImage(
            model = "https://flagcdn.com/w320/${code.lowercase()}.png",
            contentDescription = null,
            modifier = Modifier.size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = name,
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            color = theme.onBackground
        )
    }
}

@Composable
fun ListComponentss(code: String = "usa", name: String = "ua", onClick: () -> Unit) {
    val theme = LocalTheme.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(theme.background)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(theme.background)
                .clip(MaterialTheme.shapes.medium)
                .clickable {
                    onClick()
                }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://flagcdn.com/w80/${code}.png",
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = name, fontSize = 16.sp, color = theme.onBackground, fontFamily = FontFamily(
                    Font(R.font.poppins_medium)
                ), fontWeight = FontWeight.Medium
            )
        }

    }
}