package com.kevinbuenano.fidofriend.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity


@Database(entities = arrayOf(UsuarioEntity::class, MascotaEntity::class), version = 1)
abstract class appDatabase : RoomDatabase() {
    abstract fun usuarioDao() : UsuarioDAO
    abstract fun mascotaDao(): MascotaDAO

    companion object {
        private var instance:appDatabase? = null

        fun getInstance(context: Context): appDatabase{
            synchronized(this){
                var instance = instance
                if (instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, appDatabase::class.java, "fidofriend").build()
                }
                return instance
            }
        }
    }
}