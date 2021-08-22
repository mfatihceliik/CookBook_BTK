package com.muhammedfatihcelik.foodsbook.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.muhammedfatihcelik.foodsbook.model.Food
import com.muhammedfatihcelik.foodsbook.service.FoodDatabase
import com.muhammedfatihcelik.foodsbook.view.FoodsDetailFragment
import kotlinx.coroutines.launch

class FoodDetailViewModel(application: Application) : BaseViewModel(application) {

    val foodLiveData = MutableLiveData<Food>()

    fun getRoomData(uuid: Int){

        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            val food = dao.getFood(uuid)
            foodLiveData.value = food
        }
    }
}