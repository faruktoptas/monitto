package me.toptas.monitto.util

import android.content.Context

class PrefStorageImpl(context: Context) : PrefStorage {

    private val pref = context.applicationContext.getSharedPreferences("Prefs", Context.MODE_PRIVATE)

    override fun getUrl() = pref.getString(KEY_URL, "") ?: ""

    override fun setUrl(url: String) {
        pref.edit()
                .putString(KEY_URL, url)
                .apply()
    }

    override fun getUrlStatus(url: String?) = pref.getInt(url, 0)

    override fun setUrlStatus(url: String, code: Int) {
        pref.edit()
                .putInt(url, code)
                .apply()
    }

    companion object {
        private const val KEY_URL = "url"
    }

}

interface PrefStorage {

    fun getUrl(): String

    fun setUrl(url: String)

    fun getUrlStatus(url: String?): Int

    fun setUrlStatus(url: String, code: Int)
}