package com.example.yinyang.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yinyang.R
import com.example.yinyang.ui.screens.home.components.FoodConstructor
import com.example.yinyang.ui.screens.home.components.SectionHeader
import com.example.yinyang.ui.shared.components.*
import com.example.yinyang.ui.shared.models.product.ProductViewModel
import com.example.yinyang.ui.shared.models.constructorItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun HomePage(
    navigator: DestinationsNavigator,
) {
    val productViewModel = remember { ProductViewModel() }
    val products by productViewModel.products

    ScreenContainer {
        Column {
            NavBar()

            //DIY section
            SectionContainer {
                SectionHeader(iconId = R.drawable.ic_food_constructor, title = "Do it Yourself")
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    FoodConstructor(
                        background = R.drawable.bg_pizza_construct,
                        title = "Pizza",
                        fraction = .45f,
                        constructorItems = constructorItems
                    )

                    FoodConstructor(
                        background = R.drawable.bg_wok_construct,
                        title = "Wok",
                        fraction = .85f,
                        constructorItems = constructorItems
                    )
                }
            }

            //Main menu section
            SectionContainer {
                SectionHeader(iconId = R.drawable.ic_bar_menu, title = "Menu")

                var selectedTabIndex by remember { mutableStateOf(0) }
                val filterWords: List<String> = listOf("Все", "Сеты", "Роллы", "Пицца", "Снеки", "Супы")

                FilterList(
                    tabs = filterWords,
                    selectedTabIndex = selectedTabIndex,
                ) { tabIndex ->
                    selectedTabIndex = tabIndex
                }

                if (products.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .height(720.dp)
                            .padding(vertical = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                    ) {
                        items(
                            products.filter {
                                product ->
                            if (selectedTabIndex == 0) true
                            else
                                filterWords[selectedTabIndex] ==
                                        product.categoryId
                                            .getValue("title")
                                            .toString()
                                            .removeSurrounding("\"")
                            },

                            key = {product -> product.id}
                        ) { product -> ProductCard(product = product) }
                    }
                }
                else {
                    GifImage(image = R.drawable.im_loader, contentDescription = "Loading...")
                }
            }
        }
    }
}