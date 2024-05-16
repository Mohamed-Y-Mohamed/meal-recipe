package com.example.w1830958_assignment2

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Meals::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDao(): Dao
}
