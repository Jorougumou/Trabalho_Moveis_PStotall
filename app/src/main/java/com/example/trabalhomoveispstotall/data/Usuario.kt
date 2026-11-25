package com.example.trabalhomoveispstotall.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val gmail: String,
    val curso: String,
    val senha: String
)
