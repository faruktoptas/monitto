package me.toptas.monitto.util

import android.os.AsyncTask

class DbTask(private val task: Runnable) : AsyncTask<Void, Void, Void>() {

    init {
        execute()
    }

    override fun doInBackground(vararg params: Void?): Void? {
        task.run()
        return null
    }

}