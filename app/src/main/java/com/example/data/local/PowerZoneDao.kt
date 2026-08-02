package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PowerZoneDao {
    @Query("SELECT * FROM trial_bookings ORDER BY timestamp DESC")
    fun getAllTrialBookings(): Flow<List<TrialBookingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrialBooking(booking: TrialBookingEntity)

    @Query("DELETE FROM trial_bookings WHERE id = :id")
    suspend fun deleteTrialBooking(id: Int)

    @Query("SELECT * FROM bookmarks ORDER BY timestamp DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE programId = :programId")
    suspend fun removeBookmark(programId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE programId = :programId)")
    fun isBookmarked(programId: String): Flow<Boolean>
}
