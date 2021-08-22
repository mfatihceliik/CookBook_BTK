package com.muhammedfatihcelik.foodsbook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Food(

    @ColumnInfo(name = "isim")
    @SerializedName("isim")
    val food_name: String?,

    @ColumnInfo(name = "kalori")
    @SerializedName("kalori")
    val food_calorie: String?,

    @ColumnInfo(name= "karbonhidrat")
    @SerializedName("karbonhidrat")
    val food_carbohydrate: String?,

    @ColumnInfo(name = "protein")
    @SerializedName("protein")
    val food_protein: String?,

    @ColumnInfo(name = "yag")
    @SerializedName("yag")
    val food_fat: String?,

    @ColumnInfo(name = "gorsel")
    @SerializedName("gorsel")
    val food_image: String?

    ) {

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}