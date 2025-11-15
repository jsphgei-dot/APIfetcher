package com.example.apifetcher.userdirectory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    /**
     * Inserts a list of users. If a user already exists, it replaces it.
     * This is key for keeping the cache updated.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    /**
     * Fetches all users from the table, ordered by name.
     * Returns a Flow, so the UI can observe changes automatically.
     */
    @Query("SELECT * FROM users ORDER BY name ASC")
    fun getAllUsers(): Flow<List<User>>

    /**
     * Searches for users where the name OR email matches the query.
     * Returns a Flow for reactive search.
     */
    @Query("SELECT * FROM users WHERE name LIKE '%' || :query || '%' OR email LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchUsers(query: String): Flow<List<User>>
}