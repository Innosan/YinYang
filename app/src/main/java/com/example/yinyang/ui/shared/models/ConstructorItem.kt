package com.example.yinyang.ui.shared.models

data class ConstructorItem(
    val id: Int,
    val title: String,
    val options: List<ConstructorOption>,
)

data class ConstructorOption(
    val id: Int,
    val title: String,
    val price: Int = 0,
)

val constructorItems = listOf(
    ConstructorItem(
        id = 1,
        title = "Лапша/Рис",
        options = listOf(
            ConstructorOption(
                id = 1,
                title = "Якисоба",
            ),
            ConstructorOption(
                id = 2,
                title = "Удон",
            ),
            ConstructorOption(
                id = 3,
                title = "Рисовая",
            ),
            ConstructorOption(
                id = 4,
                title = "Яичная",
            ),
            ConstructorOption(
                id = 5,
                title = "Рис",
            ),
        )
    ),
    ConstructorItem(
        id = 2,
        title = "Соус",
        options = listOf(
            ConstructorOption(
                id = 1,
                title = "Терияки",
            ),
            ConstructorOption(
                id = 2,
                title = "Кикоман",
            ),
            ConstructorOption(
                id = 3,
                title = "Кислосладкий",
            ),
            ConstructorOption(
                id = 4,
                title = "Кимчи",
            ),
        )
    ),
    ConstructorItem(
        id = 3,
        title = "Топпинги",
        options = listOf(
            ConstructorOption(
                id = 1,
                title = "Говядина",
                price = 100,
            ),
            ConstructorOption(
                id = 2,
                title = "Свинина",
                price = 80,
            ),
            ConstructorOption(
                id = 3,
                title = "Курица",
                price = 70,
            ),
            ConstructorOption(
                id = 1,
                title = "Лосось",
                price = 100,
            ),
        )
    )
)
