package me.toptas.monitto.ui.main

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import com.evernote.android.job.JobManager
import kotlinx.android.synthetic.main.activity_main.*
import me.toptas.monitto.R
import me.toptas.monitto.base.BaseActivity
import me.toptas.monitto.databinding.ActivityMainBinding
import me.toptas.monitto.model.Site
import me.toptas.monitto.ui.SwipeToDeleteHandler
import me.toptas.monitto.ui.add.AddSiteActivity
import me.toptas.monitto.util.MyJob
import me.toptas.monitto.util.NotificationTask
import me.toptas.monitto.util.RequestManager
import me.toptas.monitto.util.SiteManager
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding>(), RequestManager.OnResponseCodeListener, OnSiteClickListener {

    private val viewModel: MainViewModel by inject()
    private val manager: SiteManager by inject()
    private lateinit var adapter: SiteListAdapter

    override fun layoutRes() = R.layout.activity_main

    override fun initActivity() {
        adapter = SiteListAdapter(this)
        recyclerView.also {
            it.layoutManager = LinearLayoutManager(this@MainActivity)
            it.adapter = adapter
        }
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteHandler(this) {
            adapter.items?.get(it.adapterPosition)?.apply {
                viewModel.deleteSite(this)
            }
            adapter.notifyItemRemoved(it.adapterPosition)
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)

        viewModel.getSites()
        binding.viewModel = viewModel

        viewModel.siteListLive.observe(this, Observer {
            it?.apply {
                adapter.items = this
            }
        })

        if (JobManager.instance().allJobRequests.isEmpty()) {
            MyJob.scheduleJob()
        }


    }

    override fun onSiteCheck(site: Site) {
        manager.checkSite(this, site)
    }

    override fun onResponseCode(site: Site, code: Int) {
        viewModel.updateStatus(site, code)
        NotificationTask("${site.url} code: $code").showNotification(site._id!!.toInt(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_add -> startActivity(Intent(this, AddSiteActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}
