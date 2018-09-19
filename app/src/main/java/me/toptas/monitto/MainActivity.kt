package me.toptas.monitto

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.app.AlertDialog
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RequestManager.OnResponseCodeListener {

    lateinit var loading: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.prefStorage = PrefStorageImpl(this)

        viewModel.urlLive.observe(this, Observer {
            etUrl.setText(it)
        })

        viewModel.codeLive.observe(this, Observer {
            tvCode.text = "$it"
        })

        viewModel.loadingLive.observe(this, Observer {
            it?.apply {
                if (this) {
                    loading.show()
                } else {
                    loading.dismiss()
                }
            }
        })

        btnSave.setOnClickListener {
            viewModel.save(etUrl.text.toString())
        }

        loading = createLoadingDialog()
    }

    override fun onResponseCode(url: String, code: Int) {
        Log.v("asd", "url: $url code:$code")
    }
}
