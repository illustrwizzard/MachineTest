package com.hanashi.shoppingcart.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hanashi.shoppingcart.data.model.CartItem

@Database(entities = [CartItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cart_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}