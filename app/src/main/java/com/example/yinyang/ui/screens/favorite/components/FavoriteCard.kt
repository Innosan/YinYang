package com.example.yinyang.ui.screens.favorite.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.yinyang.R
import com.example.yinyang.models.Product
import com.example.yinyang.ui.shared.styles.buttonTextStyle
import com.example.yinyang.viewmodels.ProfileViewModel

@Composable
fun FavoriteCard(
    product: Product,
    viewModel: ProfileViewModel
) {
    val context = LocalContext.current

    val userFavorites = viewModel.profile.value.userFavorite?.value
    var favoriteId = 0

    userFavorites?.forEach {favoriteItem ->
        if (favoriteItem.product_id.id == product.id) {
            favoriteId = favoriteItem.id!!
        }
    }

    var isUnfavoriting by remember { mutableStateOf(false) }

    val onUnfavorite = {
        isUnfavoriting = true

        viewModel.favoriteManager.deleteFavorite(
            favoriteId
        )
    }

    Column() {
        AnimatedVisibility(
            visible = !isUnfavoriting,
        ) {
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {

                /**
                 * Product image
                 */
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product.image_url)
                            .crossfade(true) //TODO: should change to better one
                            .build(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp)),

                        contentScale = ContentScale.Crop,
                        contentDescription = "${product.title} image",
                    )

                    /**
                     * Product tags
                     */
                    Row(
                        Modifier
                            .padding(18.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "${product.count} шт.",
                            fontWeight = FontWeight.Black,
                            modifier = Modifier
                                .background(Color(27, 27, 27, 200), RoundedCornerShape(10.dp))
                                .padding(7.dp),
                        )
                        Text(
                            text = product.category_id.title,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier
                                .background(Color(27, 27, 27, 200), RoundedCornerShape(10.dp))
                                .padding(7.dp),
                        )
                    }
                }

                Text(
                    text = product.title,
                    fontWeight = FontWeight.Black,
                    fontSize = 26.sp
                )

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick =
                        {
                            Toast.makeText(
                                context,
                                "Bought ${product.title} for ${product.price}",
                                Toast.LENGTH_SHORT
                            ).show()
                        },

                        Modifier.fillMaxWidth(.70f)
                    ) {
                        Text(
                            text = "${product.price} ₽",
                            style = buttonTextStyle
                        )
                    }

                    Button(
                        onClick =
                        {
                            onUnfavorite()

                            Toast.makeText(
                                context,
                                "Deleted ${product.title} from favorite!",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier.fillMaxWidth(.85f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_unfavorite),

                            contentDescription = "Toggle icon"
                        )
                    }
                }
            }
        }
    }
}