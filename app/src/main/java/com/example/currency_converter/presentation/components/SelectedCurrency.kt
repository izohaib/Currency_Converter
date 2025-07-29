package com.example.currency_converter.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.currency_converter.R
import com.example.currency_converter.presentation.theme.LocalTheme

@Composable
fun SelectedCurrency(
    modifier: Modifier,
    currencyCode: String = "USD",
    onClick: () -> Unit = {},
    countryCode: String
) {

    val theme = LocalTheme.current
    Box(
        modifier = modifier
            .height(65.dp)
            .clip(
                MaterialTheme.shapes.large
            )
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = MaterialTheme.shapes.large
            )
            .clickable( onClick = onClick )
            .background(theme.background)
            .padding(12.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://flagcdn.com/w320/${countryCode}.png",
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = currencyCode.uppercase(),
                color = theme.onBackground,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 18.sp
            )

            IconButton(onClick = { onClick() }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = theme.onBackground
                )
            }
        }
    }
}

