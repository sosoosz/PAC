package com.example.si.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MedicamentoDao {
    @Insert
    suspend fun insert(medicamento: Medicamento)

    @Update
    suspend fun update(medicamento: Medicamento)

    @Delete
    suspend fun delete(medicamento: Medicamento)

    @Query("SELECT * FROM medicamento WHERE userId = :userId")
    suspend fun getMedicamentosByUserId(userId: Int): List<Medicamento>

    @Query("DELETE FROM medicamento WHERE userId = :userId")
    suspend fun deleteAllByUserId(userId: Int)

    @Query("UPDATE medicamento SET isActive = :isActive WHERE id = :id")
    suspend fun updateIsActive(id: Int, isActive: Boolean)

    @Query("UPDATE medicamento SET isTakenToday = :isTaken WHERE id = :id")
    suspend fun updateIsTakenToday(id: Int, isTaken: Boolean)
}
