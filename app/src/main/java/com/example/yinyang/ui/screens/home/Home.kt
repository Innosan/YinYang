package com.example.yinyang.ui.screens.home

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yinyang.R
import com.example.yinyang.models.constructorItems
import com.example.yinyang.ui.screens.home.components.FoodConstructor
import com.example.yinyang.ui.shared.components.SectionHeader
import com.example.yinyang.ui.shared.components.*
import com.example.yinyang.ui.shared.styles.buttonTextStyle
import com.example.yinyang.viewmodels.ProductViewModel
import com.example.yinyang.viewmodels.ProfileViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun HomePage(
    navigator: DestinationsNavigator,
    productViewModel: ProductViewModel,
    profileViewModel: ProfileViewModel,
) {
    val scope = rememberCoroutineScope()
    val products by productViewModel.products.collectAsState()

    val refreshing by productViewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(refreshing, { productViewModel.refresh() })

    var showButton by remember { mutableStateOf(false) }
    val scrollState = rememberLazyListState()

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { index ->
                showButton = index >= 3
            }
    }

    ScreenContainer {
        NavBar()

        //DIY section
        SectionContainer {
            SectionHeader(iconId = R.drawable.ic_food_constructor, title = R.string.diy_section)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                FoodConstructor(
                    background = R.drawable.bg_pizza_construct,
                    title = R.string.pizza_constructor_button,
                    fraction = .45f,
                    constructorItems = constructorItems
                )

                FoodConstructor(
                    background = R.drawable.bg_wok_construct,
                    title = R.string.wok_constructor_button,
                    fraction = .85f,
                    constructorItems = constructorItems
                )
            }
        }

        //Main menu section
        SectionContainer {
            SectionHeader(iconId = R.drawable.ic_bar_menu, title = R.string.menu_section)

            var selectedTabIndex by remember { mutableStateOf(0) }
            val filterWords: List<String> = listOf("Все", "Сеты", "Роллы", "Пицца", "Снеки", "Супы")

            val filteredProducts = products.filter {
                    product ->
                if (selectedTabIndex == 0) {true}
                else filterWords[selectedTabIndex] == product.category_id.title
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,

                modifier = Modifier.fillMaxWidth()
            ) {
                FilterList(
                    tabs = filterWords,
                    selectedTabIndex = selectedTabIndex,
                ) { tabIndex ->
                    selectedTabIndex = tabIndex
                }
                Text(
                    text = "${filteredProducts.size} / ${products.size}",
                    style = buttonTextStyle,

                    modifier = Modifier
                        .background(Color.White.copy(0.14f), RoundedCornerShape(4.dp))
                        .padding(10.dp)
                )
            }

            Box(
                modifier = Modifier
                    .pullRefresh(pullRefreshState)
                    .fillMaxWidth()
                    .height(460.dp)

            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .clip(RoundedCornerShape(20.dp)),

                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    state = scrollState
                ) {
                    item {
                        AnimatedVisibility(
                            visible = filteredProducts.isEmpty(),
                        ) {
                            Text(
                                text = stringResource(R.string.no_products_note),
                                style = buttonTextStyle,
                                fontSize = 26.sp,
                                modifier = Modifier.align(Alignment.TopCenter)
                            )
                        }
                    }
                    items(
                        filteredProducts,

                        key = {product -> product.id}
                    ) { product ->
                        ProductCard(
                            product = product,
                            profileViewModel
                        )
                    }
                }

                IconButton(
                    onClick = {
                        scope.launch {
                            scrollState.animateScrollToItem(0)
                        }
                    },

                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(6.dp)
                        .height(120.dp)

                ) {
                    AnimatedVisibility(
                        visible = showButton,
                        enter = slideInHorizontally(
                            initialOffsetX = { 160 }
                        ) + fadeIn(
                            initialAlpha = 0f
                        ),
                        exit = slideOutHorizontally(
                            targetOffsetX = { 160 }
                        ) + fadeOut(
                            targetAlpha = 0f
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_to_top),

                            contentDescription = "Go to top product",
                            modifier = Modifier
                                .background(Color.Black.copy(0.8f), RoundedCornerShape(20.dp))
                                .padding(10.dp)
                                .height(120.dp)

                        )
                    }
                }

                PullRefreshIndicator(
                    refreshing = refreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}