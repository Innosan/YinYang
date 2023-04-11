package com.example.yinyang.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FoodConstructor(background: Int, title: String, fraction: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth(fraction)
            .height(80.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                /**
                 * TODO: This should navigate to according constructor
                 */
            },

        contentAlignment = Alignment.BottomEnd,
        )
    {
        Image(
            painter = painterResource(id = background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(10.dp)),
        )
        Text(
            /**
             * TODO: text color doesn't change, need to fix this
             */

            color = MaterialTheme.colorScheme.onTertiary,
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Start,

            modifier = Modifier
                .background(Color(27, 27, 27, 200), RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .fillMaxHeight(.5f)
                .padding(5.dp),
        )
    }
}