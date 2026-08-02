package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TrialBookingEntity::class, BookmarkEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PowerZoneDatabase : RoomDatabase() {
    abstract fun powerZoneDao(): PowerZoneDao

    companion object {
        @Volatile
        private var INSTANCE: PowerZoneDatabase? = null

        fun getDatabase(context: Context): PowerZoneDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PowerZoneDatabase::class.java,
                    "powerzone_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
