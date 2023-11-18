package com.example.yinyang.ui.shared.components.product

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yinyang.R
import com.example.yinyang.models.Product
import com.example.yinyang.ui.shared.components.containers.BackgroundedText
import com.example.yinyang.ui.shared.components.service.AlertDialogButton
import com.example.yinyang.ui.shared.styles.buttonTextStyle
import com.example.yinyang.utils.ButtonType
import com.example.yinyang.utils.QuantityChangeType
import com.example.yinyang.viewmodels.ProfileViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProductCard(
    product: Product,
    viewModel: ProfileViewModel
) {
    val context = LocalContext.current

    val userFavorites = viewModel.profile.value.userFavorite?.value
    val userCart = viewModel.profile.value.userCart?.value

    var isNotInFavorite = true
    var isNotInCart = true

    var favoriteId = 0

    val addToCartController = remember { mutableStateOf(false) }

    userFavorites?.forEach {favoriteItem ->
        if (favoriteItem.product_id.id == product.id) {
            isNotInFavorite = false
            favoriteId = favoriteItem.id!!
        }
    }

    userCart?.forEach {cartItem ->
        if (cartItem.product_id.id == product.id) {
            isNotInCart = false
        }
    }

    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ProductImage(product = product, height = 160, isTagsVisible = true)

        // Product info
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
                color = MaterialTheme.colorScheme.onSurface.copy(.85f),
            )
        }

        // Description
        Text(
            text = product.description,
            color = MaterialTheme.colorScheme.onSurface.copy(.7f),
        )

        // Product bottom bar
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Cart button
            Button(
                onClick =
                {
                    addToCartController.value = true
                },

                enabled = isNotInCart,
                modifier = Modifier.fillMaxWidth(.70f)
            ) {
                Crossfade(targetState = isNotInCart, label = "Add to cart") {
                    val buttonText =
                        if (!it) stringResource(id = R.string.in_cart_note)
                        else "${product.price} ₽"

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        if (!it) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_cart),
                                contentDescription = "In Cart"
                            )
                        }

                        Text(
                            text = buttonText,
                            style = buttonTextStyle,
                            fontSize = 20.sp
                        )
                    }
                }
            }

            val favButtonWidth: Float by animateFloatAsState(if (isNotInFavorite) 0.75f else 0.85f,
                label = "Add to favorite"
            )
            val buttonContainerColor by animateColorAsState(
                if (isNotInFavorite)
                    MaterialTheme.colorScheme.primary.copy(0.7f)
                else
                    MaterialTheme.colorScheme.primary, label = "Add to favorite btn color"
            )

            // Favorite button
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
                Crossfade(targetState = isNotInFavorite, label = "Adding to cart") { targetIsNotInFavorite ->
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
                var quantity by remember {
                    mutableStateOf(1)
                }

                fun changeQuantity(
                    type: QuantityChangeType,
                    minQuantity: Int = 1,
                    maxQuantity: Int = 3
                ) {
                    if (type == QuantityChangeType.ADD && quantity < maxQuantity) {
                        quantity++
                    }
                    else if (type == QuantityChangeType.REMOVE && quantity > minQuantity) {
                        quantity--
                    }
                }

                // Quantity change dialog
                AlertDialog(
                    onDismissRequest = {
                        addToCartController.value = false
                    },
                    title = {
                        Text(
                            text = stringResource(id = R.string.add_to_cart_note),
                        )
                    },
                    textContentColor = MaterialTheme.colorScheme.onBackground,
                    text = {
                        Column {
                            BackgroundedText(textId = R.string.quantity_limits_note)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(.5f),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { changeQuantity(QuantityChangeType.REMOVE) }) {
                                    Icon(
                                        modifier = Modifier.scale(-1f),
                                        painter = painterResource(id = R.drawable.ic_arrow),
                                        contentDescription = "Decrease"
                                    )
                                }

                                /**
                                 * TODO: On release change togetherWith to with
                                 */
                                AnimatedContent(
                                    targetState = quantity,
                                    transitionSpec = {
                                        if (targetState > initialState) {
                                            slideInVertically { -it } togetherWith
                                                    slideOutVertically { it }
                                        } else {
                                            slideInVertically { it } togetherWith
                                                    slideOutVertically { -it }
                                        }
                                    },
                                    label = "Quantity change"
                                ) {
                                    Text(
                                        text = it.toString(),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }

                                IconButton(onClick = { changeQuantity(QuantityChangeType.ADD) }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_arrow),
                                        contentDescription = "Increase"
                                    )
                                }
                            }
                        }
                    },
                    confirmButton = {
                        AlertDialogButton(buttonType = ButtonType.ADD) {
                            viewModel.getUserId()
                                ?.let {
                                    viewModel.cartManager.addToCart(
                                        it,
                                        product.id,
                                        quantity = quantity
                                    )
                                }

                            addToCartController.value = false

                            Toast.makeText(
                                context,
                                "Added to cart",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    dismissButton = {
                        AlertDialogButton(buttonType = ButtonType.CLOSE) {
                            addToCartController.value = false
                        }
                    }
                )
            }
        }
    }
}