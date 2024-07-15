package com.example.si.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT name FROM user WHERE id = :userId")
    suspend fun getUserNameById(userId: Int): String?

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}
