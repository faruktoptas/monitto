package me.toptas.monitto.data.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import me.toptas.monitto.model.Site

@Dao
interface SiteDao {

    @Query("Select * from site")
    fun getAll(): LiveData<List<Site>>

    @Query("Select * from site")
    fun getAllNoLive(): List<Site>

    @Insert(onConflict = REPLACE)
    fun insert(site: Site)

    @Update
    fun update(site: Site)

    @Delete
    fun delete(site: Site)
}