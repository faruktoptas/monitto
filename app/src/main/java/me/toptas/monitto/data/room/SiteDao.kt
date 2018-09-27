package me.toptas.monitto.data.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import me.toptas.monitto.model.Site

@Dao
interface SiteDao {

    @Query("Select * from site")
    fun getAll(): LiveData<List<Site>>

    @Insert(onConflict = REPLACE)
    fun insert(site: Site)

    @Update
    fun update(site: Site)
}