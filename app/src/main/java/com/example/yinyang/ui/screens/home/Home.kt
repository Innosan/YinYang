package com.example.yinyang.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.yinyang.R
import com.example.yinyang.ui.screens.home.components.FoodConstructor
import com.example.yinyang.ui.screens.home.components.SectionHeader
import com.example.yinyang.ui.shared.components.FilterList
import com.example.yinyang.ui.shared.components.NavBar
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.ui.shared.components.SectionContainer
import com.example.yinyang.ui.shared.models.Message
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

val client = createSupabaseClient(supabaseUrl = "https://liskfjzxdlaenoukvmer.supabase.co", supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imxpc2tmanp4ZGxhZW5vdWt2bWVyIiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODA5NDE2NzgsImV4cCI6MTk5NjUxNzY3OH0.0QqDcjanSHr4T3Rtk2APYryyGkgDlkkQRs5xCn18bcI") {

    //...

    install(Postgrest) {
        // settings
    }

}

suspend fun get(): List<Message> {
    val result = client.postgrest["messages"]
        .select {
            Message::authorId eq "someid"
            Message::text neq "This is a text!"
        }

    println(result.decodeList<Message>())
    return result.decodeList()
}

@Preview
@Composable
fun HomePage() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val (messages, setMessages) = remember { mutableStateOf<Message?>(null) }

    val getMessagesOnClick: () -> Unit = {
        coroutineScope.launch {
            val message = get()
        }
    }

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
                    FoodConstructor(background = R.drawable.bg_pizza_construct, title = "Pizza")
                    FoodConstructor(background = R.drawable.bg_wok_construct, title = "Wok")
                }
            }

            //Main menu section
            SectionContainer {
                SectionHeader(iconId = R.drawable.ic_bar_menu, title = "Menu")

                var selectedTabIndex by remember { mutableStateOf(0) }
                val filterWords: List<String> = listOf("Sets", "Rolls", "Pizza", "Snacks", "Soups")

                FilterList(
                    tabs = filterWords,
                    selectedTabIndex = selectedTabIndex,
                ) { tabIndex ->
                    selectedTabIndex = tabIndex

                    Toast.makeText(context, "Selected $selectedTabIndex tab!", Toast.LENGTH_SHORT).show()
                }
            }

            Button(onClick = getMessagesOnClick) {
                Text(text = "Get mess")
            }
        }
    }
}