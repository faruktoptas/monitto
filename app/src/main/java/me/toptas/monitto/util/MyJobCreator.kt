package me.toptas.monitto.util

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator

class MyJobCreator(private val manager: SiteManager) : JobCreator {

    override fun create(tag: String): Job? {
        return when (tag) {
            CHECK_SERVER_TAG -> MyJob(manager)
            else -> null
        }
    }

    companion object {
        const val CHECK_SERVER_TAG = "checkServer"
    }

}