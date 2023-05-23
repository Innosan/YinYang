package com.example.yinyang.ui.shared.components

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
fun ProductCard(
    product: Product,
    viewModel: ProfileViewModel
) {
    val context = LocalContext.current

    val userFavorites = viewModel.profile.value.userFavorite?.value
    var isNotInFavorite = true
    var favoriteId = 0

    val addToCartController = remember { mutableStateOf(false) }

    userFavorites?.forEach {favoriteItem ->
        if (favoriteItem.product_id.id == product.id) {
            isNotInFavorite = false
            favoriteId = favoriteItem.id!!
        }
    }

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
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .background(Color(27, 27, 27, 200), RoundedCornerShape(10.dp))
                        .padding(7.dp),
                )
                Text(
                    text = product.category_id.title,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .background(Color(27, 27, 27, 200), RoundedCornerShape(10.dp))
                        .padding(7.dp),
                )
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = product.title,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = "${product.weight} гр.",
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Text(text = product.description)

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick =
                {
                    addToCartController.value = true
                },

                Modifier.fillMaxWidth(.70f)
            ) {
                Text(
                    text = "${product.price} ₽",
                    style = buttonTextStyle,
                    fontSize = 20.sp
                )
            }

            val favButtonWidth: Float by animateFloatAsState(if (isNotInFavorite) 0.75f else 0.85f)
            val buttonContainerColor by animateColorAsState(
                if (isNotInFavorite)
                    MaterialTheme.colorScheme.primary.copy(0.7f)
                else
                    MaterialTheme.colorScheme.primary
            )

            Button(
                onClick =
                {
                    if (isNotInFavorite) {
                        viewModel.profile.value.userInfo?.value?.id?.let {
                            viewModel.favoriteManager.addFavorite(
                                it,
                                product.id
                            )
                        }
                    } else {
                        viewModel.favoriteManager.deleteFavorite(
                            favoriteId
                        )
                    }

                    Toast.makeText(
                        context,
                        "Added ${product.title} in favourite!",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                modifier = Modifier.fillMaxWidth(favButtonWidth),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonContainerColor
                ),
            ) {
                Crossfade(targetState = isNotInFavorite) { targetIsNotInFavorite ->
                    val icon =
                        if (targetIsNotInFavorite) R.drawable.ic_favorite
                        else R.drawable.ic_unfavorite

                    Icon(
                        painter = painterResource(id = icon),

                        contentDescription = "Toggle icon"
                    )
                }
            }

            if (addToCartController.value) {
                val quantity = remember { mutableStateOf(1) }

                AlertDialog(
                    onDismissRequest = {
                        addToCartController.value = false
                    },
                    title = {
                        Text(text = stringResource(id = R.string.add_to_cart_note))
                    },
                    text = {
                        Row() {
                            Button(onClick = { quantity.value-- }) {
                                Text(text = "-")
                            }

                            Text(text = quantity.value.toString())

                            Button(onClick = { quantity.value++ }) {
                                Text(text = "+")
                            }
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.getUserId()
                                ?.let {
                                    viewModel.cartManager.addToCart(
                                        it,
                                        product.id,
                                        quantity = quantity.value
                                    )
                                }

                            addToCartController.value = false

                            Toast.makeText(
                                context,
                                "Added to cart",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                            Text(text = stringResource(id = R.string.add_button))
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                addToCartController.value = false
                            }) {
                            Text(text = stringResource(id = R.string.close_button))
                        }
                    }
                )
            }
        }
    }
}