package com.example.yinyang.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FoodConstructor(background: Int, title: String) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(80.dp),

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
            modifier = Modifier
                .background(Color(27, 27, 27, 200), RectangleShape)
                .fillMaxWidth()
                .fillMaxHeight(.5f)
                .padding(5.dp),

            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Start
        )
    }
}