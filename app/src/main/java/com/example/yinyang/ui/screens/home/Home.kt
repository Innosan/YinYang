package com.example.yinyang.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yinyang.R
import com.example.yinyang.ui.screens.home.components.FoodConstructor
import com.example.yinyang.ui.screens.home.components.SectionHeader
import com.example.yinyang.ui.shared.components.*
import com.example.yinyang.ui.shared.models.Product
import com.example.yinyang.ui.shared.models.get
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Destination
@Composable
fun HomePage(
    navigator: DestinationsNavigator,
) {
    val coroutineScope = rememberCoroutineScope()

    val (products, setProducts) = remember { mutableStateOf<List<Product>?>(null) }

    val getProducts: () -> Unit = {
        coroutineScope.launch {
            setProducts(get())
        }
    }

    getProducts()

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
                    FoodConstructor(background = R.drawable.bg_pizza_construct, title = "Pizza", fraction = .45f)
                    FoodConstructor(background = R.drawable.bg_wok_construct, title = "Wok", fraction = .85f)
                }
            }

            //Main menu section
            SectionContainer {
                SectionHeader(iconId = R.drawable.ic_bar_menu, title = "Menu")

                var selectedTabIndex by remember { mutableStateOf(0) }
                val filterWords: List<String> = listOf("Все", "Сеты", "Роллы", "Пицца", "Снеки", "Супы")

                Button(onClick = getProducts) {
                    Text(text = "Update products")
                }

                FilterList(
                    tabs = filterWords,
                    selectedTabIndex = selectedTabIndex,
                ) { tabIndex ->
                    selectedTabIndex = tabIndex
                }

                if (products != null) {
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
                    Icon(
                        painter = painterResource(id = R.drawable.ic_animated_loader),

                        contentDescription = "Loading..."
                    )
                }
            }
        }
    }
}