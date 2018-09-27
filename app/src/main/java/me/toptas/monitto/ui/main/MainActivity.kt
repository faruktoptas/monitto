package me.toptas.monitto.ui.main

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import me.toptas.monitto.R
import me.toptas.monitto.base.BaseActivity
import me.toptas.monitto.databinding.ActivityMainBinding
import me.toptas.monitto.model.Site
import me.toptas.monitto.ui.add.AddSiteActivity
import me.toptas.monitto.util.PrefStorageImpl
import me.toptas.monitto.util.RequestManager
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding>(), RequestManager.OnResponseCodeListener, OnSiteClickListener {

    private val viewModel: MainViewModel by inject()
    private lateinit var adapter: SiteListAdapter

    override fun layoutRes() = R.layout.activity_main

    override fun initActivity() {
        viewModel.prefStorage = PrefStorageImpl(this)

        adapter = SiteListAdapter(this)
        recyclerView.also {
            it.layoutManager = LinearLayoutManager(this@MainActivity)
            it.adapter = adapter
        }

        viewModel.getSites()
        binding.viewModel = viewModel

        viewModel.siteListLive.observe(this, Observer {
            it?.apply {
                adapter.items = this
            }
        })
    }

    override fun onSiteCheck(site: Site) {
        RequestManager.instance.makeGetRequest(site, this)
    }

    override fun onResponseCode(site: Site, code: Int) {
        //Log.v("asd", "url: ${site.url} code:$code")
        viewModel.updateStatus(site, code)
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
