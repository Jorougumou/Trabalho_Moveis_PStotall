package com.example.trabalhomoveispstotall.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Usuario::class, ProcessoSeletivo::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun processoSeletivoDao(): ProcessoSeletivoDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pstotal_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
            }
            return INSTANCE!!
        }
    }
}
