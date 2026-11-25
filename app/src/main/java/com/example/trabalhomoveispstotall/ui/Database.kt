package com.example.trabalhomoveispstotall

import androidx.room.Index
import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nomeUsuario: String,
    val gmail: String,
    val curso: String,
    val senha: String
)

@Entity(tableName = "projetos")
data class ProjetoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val descricao: String = ""
)

@Entity(
    tableName = "processos",
    foreignKeys = [
        ForeignKey(
            entity = ProjetoEntity::class,
            parentColumns = ["id"],
            childColumns = ["id_projeto"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["id_projeto"])
    ]
)
data class ProcessosEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val data_abertura: String,
    val data_encerramento: String,
    val descricao: String,
    val id_projeto: Int
)


