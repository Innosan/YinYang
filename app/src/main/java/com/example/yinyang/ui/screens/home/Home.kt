package com.example.yinyang.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yinyang.R
import com.example.yinyang.models.constructorItems
import com.example.yinyang.ui.screens.destinations.OrderDestination
import com.example.yinyang.ui.screens.home.components.FoodConstructor
import com.example.yinyang.ui.shared.components.containers.ScreenContainer
import com.example.yinyang.ui.shared.components.containers.SectionContainer
import com.example.yinyang.ui.shared.components.containers.SectionHeader
import com.example.yinyang.ui.shared.components.product.CartItemCard
import com.example.yinyang.ui.shared.components.product.FilterList
import com.example.yinyang.ui.shared.components.product.ProductCard
import com.example.yinyang.ui.shared.components.service.BottomSheetWrapper
import com.example.yinyang.ui.shared.components.user.TotalBlock
import com.example.yinyang.ui.shared.styles.buttonTextStyle
import com.example.yinyang.utils.getTotal
import com.example.yinyang.utils.setHorizontalEnter
import com.example.yinyang.utils.setHorizontalExit
import com.example.yinyang.viewmodels.ProductViewModel
import com.example.yinyang.viewmodels.ProfileViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class
)
@Destination
@Composable
fun HomePage(
    navigator: DestinationsNavigator,
    productViewModel: ProductViewModel,
    profileViewModel: ProfileViewModel,
) {
    val scope = rememberCoroutineScope()

    val products by productViewModel.products.collectAsState()
    val cart = profileViewModel.profile.value.userCart?.value

    val refreshing by productViewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(refreshing, { productViewModel.refresh() })

    var showButton by remember { mutableStateOf(false) }

    val scrollState = rememberLazyListState()
    val scaffoldState = rememberBottomSheetScaffoldState()

    val total = getTotal(cart)

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { index ->
                showButton = index >= 10
            }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,

        sheetContent = {
            BottomSheetWrapper {
                SectionHeader(
                    iconId = R.drawable.ic_cart,
                    title = R.string.cart_screen
                )

                if (total?.quantity == 0) {
                    Text(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.onSurfaceVariant,
                                RoundedCornerShape(10.dp)
                            )
                            .padding(20.dp),

                        text = stringResource(id = R.string.empty_cart_note),
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                if (cart != null) {
                    LazyColumn(
                        modifier = Modifier
                            .height(cart.size.dp * 145),

                        verticalArrangement = Arrangement.spacedBy(20.dp),
                    ) {
                        items(
                            cart,

                            key = {cartItem -> cartItem.product_id.id}
                        ) { cartItem ->
                            CartItemCard(
                                cartItem = cartItem,
                                quantity = cartItem.quantity,
                                deleteCartItem = {
                                    cartItem.id?.let { cartItemId ->
                                        profileViewModel.cartManager.deleteCartItem(cartItemId)
                                    }
                                },
                            )
                        }
                    }

                    if (total?.quantity != 0) {
                        TotalBlock(total = total)

                        Button(
                            onClick = {
                                navigator.navigate(OrderDestination)
                            },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = stringResource(id = R.string.make_order_button).uppercase(),
                                style = buttonTextStyle
                            )
                        }
                    }
                }
            }
        },

        sheetPeekHeight = 60.dp,
        sheetShadowElevation = 20.dp
    ) {
        ScreenContainer {

            /**
             * DIY Section
             */
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
                        fraction = .35f,
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

            /**
             * Menu Section
             */
            SectionContainer {
                SectionHeader(iconId = R.drawable.ic_bar_menu, title = R.string.menu_section)

                var selectedTabIndex by remember { mutableStateOf(0) }
                val filterWords: List<String> = listOf("Все", "Сеты", "Роллы", "Пицца", "Снеки", "Супы", "Салаты", "Суши")

                val filteredProducts = products.filter {
                        product ->
                    if (selectedTabIndex == 0) {true}
                    else filterWords[selectedTabIndex] == product.category_id.title
                }

                /**
                 * Filtering tab
                 */
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
                        color = MaterialTheme.colorScheme.onBackground,

                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.onSurfaceVariant,
                                RoundedCornerShape(4.dp)
                            )
                            .padding(10.dp)
                    )
                }

                /**
                 * Products list
                 */
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

                        verticalArrangement = Arrangement.spacedBy(40.dp),
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
                            enter = setHorizontalEnter(),
                            exit = setHorizontalExit()
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
                        modifier = Modifier.align(Alignment.TopCenter),
                        backgroundColor = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}