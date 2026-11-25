package com.example.trabalhomoveispstotall.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProcessoSeletivoDao {

    @Insert
    suspend fun inserir(processo: ProcessoSeletivo)

    @Query("SELECT * FROM processos_seletivos")
    suspend fun listarTodos(): List<ProcessoSeletivo>

    @Update
    suspend fun atualizar(processo: ProcessoSeletivo)

    @Delete
    suspend fun deletar(processo: ProcessoSeletivo)
}
