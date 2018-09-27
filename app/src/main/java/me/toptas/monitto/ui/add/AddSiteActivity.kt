package me.toptas.monitto.ui.add

import android.arch.lifecycle.Observer
import me.toptas.monitto.R
import me.toptas.monitto.base.BaseActivity
import me.toptas.monitto.databinding.ActivityAddBinding
import org.koin.android.ext.android.inject

class AddSiteActivity : BaseActivity<ActivityAddBinding>() {

    private val viewModel: AddViewModel by inject()

    override fun layoutRes() = R.layout.activity_add

    override fun initActivity() {
        binding.viewModel = viewModel


        viewModel.addedLive.observe(this, Observer {
            if (it == true) {
                finish()
            }
        })

        viewModel.errorLive.observe(this, Observer {
            it?.message?.apply {
                showToast(this)
            }
        })
    }
}