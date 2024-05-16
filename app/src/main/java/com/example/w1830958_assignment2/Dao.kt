package com.example.w1830958_assignment2

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Dao
@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(vararg meal: Meals)
    @Insert
    suspend fun insertAll(vararg meal: Meals)
    @Query("Select * from Meals")
    suspend fun getAll(): List<Meals>
}