package com.muhammedfatihcelik.foodsbook.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muhammedfatihcelik.foodsbook.model.Food
import com.muhammedfatihcelik.foodsbook.service.FoodAPIService
import com.muhammedfatihcelik.foodsbook.service.FoodDatabase
import com.muhammedfatihcelik.foodsbook.util.PrivateSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application) : BaseViewModel(application) {
    val foods = MutableLiveData<List<Food>>()
    val foodErrorMessage = MutableLiveData<Boolean>()
    val foodLoading = MutableLiveData<Boolean>()

    private val foodApiService = FoodAPIService()
    private val disposable = CompositeDisposable()

    private val privateSharedPreferences = PrivateSharedPreferences(getApplication())
    private var updateTime = 10 * 60 * 1000 * 1000 * 1000L

    fun refreshData() {
        val saveTime = privateSharedPreferences.getTime()
        if(saveTime != null && saveTime != 0L && System.nanoTime() - saveTime < updateTime){
            // get sql
            getDataFromSqLite()
        }else{
            // get internet
            getAllDataFromApi()
        }
    }

    fun refreshFromInternet(){
        getAllDataFromApi()
    }

    private fun getDataFromSqLite(){
        foodLoading.value = true
        launch {
            val foodList = FoodDatabase(getApplication()).foodDao().getAllFood()
            showFoods(foodList)
            Toast.makeText(getApplication(),"We got foods data from sqLite",Toast.LENGTH_LONG).show()
        }
    }

    private fun getAllDataFromApi() {
        foodLoading.value = true

        disposable.add(
            foodApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Food>>() {
                    override fun onSuccess(t: List<Food>) {
                        // Success
                        storeInSql(t)
                        Toast.makeText(getApplication(),"We got foods data from internet",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        // Error
                        foodErrorMessage.value = true
                        foodLoading.value = false
                        e.printStackTrace()
                    }

                })
        )

    }

    private fun showFoods(foodList: List<Food>){
        foods.value = foodList
        foodErrorMessage.value = false
        foodLoading.value = false
    }

    private fun storeInSql(foodList: List<Food>){
        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            dao.deleteAllFood()
            // * => tek tek listeyi ver demek
            val uuidList = dao.insertAll(*foodList.toTypedArray())
            var i = 0
            while(i < foodList.size){
                foodList[i].uuid = uuidList[i].toInt()
                i++
            }
            showFoods(foodList)
        }
        privateSharedPreferences.saveTime(System.nanoTime())
    }
}