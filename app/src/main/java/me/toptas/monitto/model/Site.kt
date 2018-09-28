package me.toptas.monitto.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import me.toptas.monitto.toStatus
import java.text.SimpleDateFormat
import java.util.*


@Entity(tableName = "site")
data class Site(@PrimaryKey(autoGenerate = true) var _id: Long? = null,
                @ColumnInfo(name = "title")
                var url: String,

                @ColumnInfo(name = "code")
                var code: Int = -1,

                @ColumnInfo(name = "updated")
                var updated: Long = Date().time

) {

    fun updatedString(): String {
        val formatter = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        return formatter.format(Date(updated))
    }

    fun status() = code.toStatus()

    fun isSuccess() = status() == STATE_OK

    fun isFailed() = status() == STATE_FAIL

    fun isUnknown() = status() == STATE_UNKNOWN


    companion object {
        const val STATE_OK = "ok"
        const val STATE_FAIL = "fail"
        const val STATE_UNKNOWN = "unknown"
    }
}