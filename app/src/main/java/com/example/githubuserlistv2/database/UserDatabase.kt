package com.example.githubuserlistv2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuserlistv2.model.User

@Database(entities = [User::class], version = 1)
abstract class  UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserDatabase {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class.java) {
                    if (INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                            UserDatabase::class.java,"user_database")
                            .build()
                    }
                }
            }
            return INSTANCE as UserDatabase
        }
    }
}