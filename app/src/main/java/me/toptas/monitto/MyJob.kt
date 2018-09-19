package me.toptas.monitto

import com.evernote.android.job.Job
import com.evernote.android.job.JobManager
import com.evernote.android.job.JobRequest

class MyJob : Job(), RequestManager.OnResponseCodeListener {


    override fun onResponseCode(url: String, code: Int) {
        PrefStorageImpl(context).setUrlStatus(url, code)
    }

    override fun onRunJob(params: Params): Result {
        logv("Job ran successfully")
        val url = PrefStorageImpl(context).getUrl()
        RequestManager.instance.makeGetRequest(url, this)
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