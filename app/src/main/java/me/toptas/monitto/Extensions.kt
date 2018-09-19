package me.toptas.monitto

import android.app.Activity
import android.support.v7.app.AlertDialog
import android.util.Log
import android.util.Patterns

const val LOG_TAG = "monitto"

fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()

fun logv(msg: String) = Log.v(LOG_TAG, msg)

fun loge(msg: String) = Log.e(LOG_TAG, msg)

fun Activity.createLoadingDialog(): AlertDialog {
    val builder = AlertDialog.Builder(this)
    builder.setCancelable(false)
    builder.setView(R.layout.layout_loading)
    return builder.create()
}