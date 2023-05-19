package com.kevinbuenano.fidofriend.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity


@Database(entities = arrayOf(UsuarioEntity::class, MascotaEntity::class), version = 1, exportSchema = false)
abstract class appDatabase : RoomDatabase() {
    abstract fun usuarioDao() : UsuarioDAO
    abstract fun mascotaDao(): MascotaDAO
    abstract fun historialMedicoDao(): HistorialMedicoDAO

    companion object {
        @Volatile
        private var INSTANCE: appDatabase? = null
        fun getDatabase(context: Context): appDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    appDatabase::class.java,
                    "fidofriend"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}