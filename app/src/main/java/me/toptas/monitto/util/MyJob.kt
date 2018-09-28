package me.toptas.monitto.util

import com.evernote.android.job.Job
import com.evernote.android.job.JobManager
import com.evernote.android.job.JobRequest
import me.toptas.monitto.logv

class MyJob(private val manager: SiteManager) : Job() {

    override fun onRunJob(params: Params): Result {
        logv("Job ran successfully")
        manager.checkAll(context)
        return Result.SUCCESS
    }

    companion object {
        fun scheduleJob() {
            val jobId = JobRequest.Builder(MyJobCreator.CHECK_SERVER_TAG)
                    .setPeriodic(900000)
                    .build()
                    .schedule()
            logv("Job Id: $jobId")
        }

        fun cancelJob(id: Int) = JobManager.instance().cancel(id)
    }

}