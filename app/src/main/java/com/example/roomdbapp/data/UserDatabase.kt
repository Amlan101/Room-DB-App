package com.example.roomdbapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
// It is a good practise to have version history of our schema but we don't need that here
// so the exportSchema is set to false
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    // Everything within this companion objecct is visible to other classes
    companion object{
        @Volatile // It means writes to this field are immediately made visible to other threads
        private var INSTANCE: UserDatabase? = null
        // We want to make UserDatabase class into a singleton class i.e.only have a single instance for it.
        // Reason -> Having multiple instances can be expensive for the app's performance

        // If we have an instance we return it else we make an instance in this synchronized block
        fun getDatabase(context: Context): UserDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            // A synchronized block prevents concurrent execution by multiple threads
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}