package me.toptas.monitto

import android.os.AsyncTask
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class RequestManager {

    fun makeGetRequest(url: String, listener: OnResponseCodeListener) {
        if (url.isValidUrl()) {
            val httpCall = HttpCall(url, listener)
            httpCall.execute()
            logv("Request sent: $url")
        } else {
            loge("Url not valid")
        }
    }

    private class HttpCall(private val url: String,
                           private val mOnResponseCodeListener: OnResponseCodeListener) : AsyncTask<String, Int, Int>() {


        override fun doInBackground(vararg params: String): Int? {
            var inputStream: InputStream? = null
            try {
                val httpURLConnection = URL(url).openConnection() as HttpURLConnection
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
            mOnResponseCodeListener.onResponseCode(url, httpCode)
            logv("Response code for $url -> $httpCode")
        }


    }

    interface OnResponseCodeListener {
        fun onResponseCode(url: String, code: Int)
    }

    companion object {
        val instance = RequestManager()
    }
}