    package com.example.settleinn

    import androidx.lifecycle.LiveData
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.flow.Flow
    import kotlinx.coroutines.launch
    import kotlinx.coroutines.withContext

    class SavedHouseViewModel(private val houseDao: HouseDao) : ViewModel() {

        fun getAllSavedHouses(): List<HouseDetail> {
            return houseDao.getAll()
        }

        fun insertHouse(house: HouseDetail) {
            viewModelScope.launch(Dispatchers.IO) {
                houseDao.insert(house)
            }
        }

        fun deleteHouse(house: HouseDetail) {
            viewModelScope.launch(Dispatchers.IO) {
                houseDao.deleteItem(house)
            }
        }
    }
