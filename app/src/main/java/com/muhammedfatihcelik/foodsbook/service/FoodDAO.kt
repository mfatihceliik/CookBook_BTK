package com.muhammedfatihcelik.foodsbook.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.muhammedfatihcelik.foodsbook.model.Food

@Dao
interface FoodDAO {

    @Insert
    suspend fun insertAll(vararg food : Food) : List<Long>

    @Query("SELECT * FROM food")
    suspend fun getAllFood() : List<Food>

    @Query("SELECT * FROM food WHERE uuid = :food_id")
    suspend fun getFood(food_id: Int) : Food

    @Query("DELETE FROM food")
    suspend fun deleteAllFood()

}