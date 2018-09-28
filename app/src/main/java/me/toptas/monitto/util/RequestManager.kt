package me.toptas.monitto.util

import android.os.AsyncTask
import io.reactivex.Observable
import me.toptas.monitto.isValidUrl
import me.toptas.monitto.loge
import me.toptas.monitto.logv
import me.toptas.monitto.model.Site
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class RequestManager {

    private fun makeGetRequest(site: Site, listener: OnResponseCodeListener) {
        val url = site.url
        if (url.isValidUrl()) {
            val httpCall = HttpCall(site, listener)
            httpCall.execute()
            logv("Request sent: $url")
        } else {
            loge("Url not valid")
        }
    }

    fun makeGetRequestRx(site: Site): Observable<Pair<Site, Int>> {
        return Observable.create<Pair<Site, Int>> {
            makeGetRequest(site, object : OnResponseCodeListener {
                override fun onResponseCode(site: Site, code: Int) {
                    it.onNext(Pair(site, code))
                    it.onComplete()
                }
            })
        }
    }

    private class HttpCall(private val site: Site,
                           private val mOnResponseCodeListener: OnResponseCodeListener) : AsyncTask<String, Int, Int>() {


        override fun doInBackground(vararg params: String): Int? {
            var inputStream: InputStream? = null
            try {
                val httpURLConnection = URL(site.url).openConnection() as HttpURLConnection
                httpURLConnection.readTimeout = 10000
                httpURLConnection.connectTimeout = 15000
                httpURLConnection.doInput = true
                httpURLConnection.requestMethod = "GET"
                httpURLConnection.connect()
                val responseCode = httpURLConnection.responseCode
                inputStream = if (responseCode == 200) {
                    httpURLConnection.inputStream
                } else {
                    httpURLConnection.errorStream
                }
                return responseCode
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
            return 0
        }

        override fun onPostExecute(httpCode: Int) {
            super.onPostExecute(httpCode)
            mOnResponseCodeListener.onResponseCode(site, httpCode)
            logv("Response code for ${site.url} -> $httpCode")
        }


    }

    interface OnResponseCodeListener {
        fun onResponseCode(site: Site, code: Int)
    }

    companion object {
        val instance = RequestManager()
    }
}