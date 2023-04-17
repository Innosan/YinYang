package com.example.yinyang.ui.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yinyang.ui.shared.modifiers.tabIndicatorOffset
import com.example.yinyang.ui.theme.OverpassFamily

@Composable
fun FilterList(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabClick: (Int) -> Unit
) {
    val density = LocalDensity.current
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabs.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,

        divider = {
            TabRowDefaults.Indicator(
                color = Color.Transparent
            )
        },

        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .tabIndicatorOffset(
                        currentTabPosition = tabPositions[selectedTabIndex],
                        tabWidth = tabWidths[selectedTabIndex]
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }
    ) {
        tabs.forEachIndexed { tabIndex, tab ->
            val selected = selectedTabIndex == tabIndex

            Tab(
                selected = selected,

                modifier =
                if (selected)
                    Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(24.dp)
                        )
                else
                    Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            color = Color(303030),
                            shape = RoundedCornerShape(24.dp)
                        ),

                onClick = { onTabClick(tabIndex) },

                /**
                 * TODO: Color doesn't work
                 */
                unselectedContentColor = Color.Gray,
                selectedContentColor = MaterialTheme.colorScheme.onBackground,

                text = {
                    Text(
                        fontFamily = OverpassFamily,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        text = tab,
                        color = MaterialTheme.colorScheme.onBackground,

                        onTextLayout = { textLayoutResult ->
                            tabWidths[tabIndex] =
                                with(density) { textLayoutResult.size.width.toDp() }
                        }
                    )
                }
            )
        }
    }
}