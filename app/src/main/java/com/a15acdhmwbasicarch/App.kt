package com.a15acdhmwbasicarch

import android.app.Application
import com.a15acdhmwbasicarch.di.*

class App : Application() {

    private lateinit var daggerComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        daggerComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .roomModule(RoomModule(this))
            //.dispatcherModule(DispatcherModule)
            .build()
    }

    fun getComponent(): AppComponent {
        return daggerComponent
    }
}