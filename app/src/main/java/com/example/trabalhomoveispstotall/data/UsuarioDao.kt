package com.example.trabalhomoveispstotall.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UsuarioDao {

    @Insert
    suspend fun inserir(usuario: Usuario)

    @Query("SELECT * FROM usuarios")
    suspend fun listarTodos(): List<Usuario>

    @Query("SELECT * FROM usuarios WHERE nome = :nome AND senha = :senha LIMIT 1")
    suspend fun autenticar(nome: String, senha: String): Usuario?

    @Update
    suspend fun atualizar(usuario: Usuario)

    @Delete
    suspend fun deletar(usuario: Usuario)
}
