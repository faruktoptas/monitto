package me.toptas.monitto.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*


@Entity(tableName = "site")
data class Site(@PrimaryKey(autoGenerate = true) var _id: Long? = null,
                @ColumnInfo(name = "title")
                var url: String,

                @ColumnInfo(name = "state")
                var state: String = STATE_UNKNOWN,

                @ColumnInfo(name = "updated")
                var updated: Long = Date().time

) {

    fun updatedString(): String {
        val formatter = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        return formatter.format(Date(updated))

    }


    companion object {
        const val STATE_OK = "ok"
        const val STATE_FAIL = "fail"
        const val STATE_UNKNOWN = "unknown"
    }
}