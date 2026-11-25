package com.example.trabalhomoveispstotall.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "processos_seletivos")
data class ProcessoSeletivo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val projeto: String,
    val dataInicio: String,
    val dataTermino: String,
    val status: String
)
