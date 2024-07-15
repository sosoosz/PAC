package com.example.si.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "medicamento",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Medicamento(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val nome: String,
    val dosagem: String,
    val quantidade: String,
    val data: String,
    val hora: String,
    val prescricao: String,
    val isActive: Boolean = true,
    val isTakenToday: Boolean = false
)

