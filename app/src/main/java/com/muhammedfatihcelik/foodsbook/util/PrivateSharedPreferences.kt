package com.muhammedfatihcelik.foodsbook.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class PrivateSharedPreferences {

    companion object{
        private val TIME = "time"
        private var sharedPreferences : SharedPreferences? = null

        @Volatile private var instance : PrivateSharedPreferences? = null

        private val lock = Any()
        operator fun invoke(context: Context) : PrivateSharedPreferences = instance ?: synchronized(lock){
            instance ?: doPrivateSharedPreferences(context).also {
                instance = it
            }
        }

        private fun doPrivateSharedPreferences(context: Context): PrivateSharedPreferences {
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return PrivateSharedPreferences()
        }
    }

    fun saveTime(time: Long){
        sharedPreferences?.edit(commit = true){
            putLong(TIME, time)
        }
    }

    fun getTime() = sharedPreferences?.getLong(TIME, 0)

}