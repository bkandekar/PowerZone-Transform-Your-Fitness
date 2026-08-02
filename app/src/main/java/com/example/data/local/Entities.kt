package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trial_bookings")
data class TrialBookingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val phone: String,
    val locality: String,
    val fitnessGoal: String,
    val preferredSlot: String,
    val programTitle: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val programId: String,
    val programTitle: String,
    val category: String,
    val timestamp: Long = System.currentTimeMillis()
)
