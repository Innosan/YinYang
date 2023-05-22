package com.example.yinyang.viewmodels

//@HiltViewModel
//class FavoriteViewModel @Inject constructor(
//    favoriteRepository: FavoriteRepository,
//) : ViewModel() {
//    private val _favorite = mutableStateOf(emptyList<Favorite>())
//    val favorite: State<List<Favorite>> = _favorite
//
//    init {
//        viewModelScope.launch {
//            _favorite.value = favoriteRepository.getFavorites()
//        }
//    }
//}