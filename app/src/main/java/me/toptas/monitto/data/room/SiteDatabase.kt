package me.toptas.monitto.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import me.toptas.monitto.data.room.SiteDao
import me.toptas.monitto.model.Site

@Database(entities = [(Site::class)], version = 1)
abstract class SiteDatabase : RoomDatabase() {

    abstract fun siteDao(): SiteDao

    companion object {
        private var instance: SiteDatabase? = null

        fun getInstance(context: Context): SiteDatabase? {
            if (instance == null) {
                synchronized(SiteDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext,
                            SiteDatabase::class.java, "sites.db")
                            .build()
                }
            }
            return instance
        }
    }
}