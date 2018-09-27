package me.toptas.monitto.di

import me.toptas.monitto.App
import me.toptas.monitto.data.SitesRepository
import me.toptas.monitto.data.SitesRepositoryImpl
import me.toptas.monitto.data.room.SiteDatabase
import me.toptas.monitto.ui.add.AddViewModel
import me.toptas.monitto.ui.main.MainViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    single { SiteDatabase.getInstance(App.instance)!!.siteDao() }
    single<SitesRepository> { SitesRepositoryImpl(get()) }


    viewModel { MainViewModel(get()) }
    viewModel { AddViewModel(get()) }
}