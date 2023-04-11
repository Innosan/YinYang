package com.example.yinyang.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yinyang.R
import com.example.yinyang.ui.screens.home.components.FoodConstructor
import com.example.yinyang.ui.screens.home.components.SectionHeader
import com.example.yinyang.ui.shared.components.*
import com.example.yinyang.ui.shared.models.Product
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

val client = createSupabaseClient(
    supabaseUrl = "https://liskfjzxdlaenoukvmer.supabase.co",
    supabaseKey =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imxpc2tmanp" +
            "4ZGxhZW5vdWt2bWVyIiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODA5NDE2NzgsImV4cCI6MTk5NjUxNzY" +
            "3OH0.0QqDcjanSHr4T3Rtk2APYryyGkgDlkkQRs5xCn18bcI"
) {

    //...

    install(Postgrest) {
        // settings
    }

}

suspend fun get(): List<Product> {
    val result = client.postgrest["product"]
        .select("*, category_id(title)")

    return result.decodeList()
}

@Preview
@Composable
fun HomePage() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val (products, setProducts) = remember { mutableStateOf<List<Product>?>(null) }

    val getProducts: () -> Unit = {
        coroutineScope.launch {
            setProducts(get())
        }
    }

    getProducts()

    ScreenContainer(
    ) {
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
                val filterWords: List<String> = listOf("Sets", "Rolls", "Pizza", "Snacks", "Soups")

                Button(onClick = getProducts) {
                    Text(text = "Update products")
                }

                FilterList(
                    tabs = filterWords,
                    selectedTabIndex = selectedTabIndex,
                ) { tabIndex ->
                    selectedTabIndex = tabIndex

                    Toast.makeText(context, "Selected $selectedTabIndex tab!", Toast.LENGTH_SHORT).show()
                }

                if (products != null) {
                    LazyColumn(
                        modifier = Modifier.height(720.dp).padding(vertical = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                    ) {
                        item {

                        }

                        items(products) { product -> ProductCard(product = product)}
                    }
                }
                else {
                    Text(text = "Loading...")
                }
            }
        }
    }
}