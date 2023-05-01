package com.kevinbuenano.fidofriend.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity


@Database(entities = arrayOf(UsuarioEntity::class), version = 1)
abstract class appDatabase : RoomDatabase() {
    abstract fun usuarioDao() : UsuarioDAO

    companion object {
        private var instance:UsuarioDAO? = null

        fun getInstance(context: Context): UsuarioDAO{
            return instance ?: Room.databaseBuilder(context, appDatabase::class.java, "fidofriend").build().usuarioDao().also { instance = it }
        }
    }
}