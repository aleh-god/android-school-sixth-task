package by.godevelopment.sixthtask

import android.app.Application
import android.content.Context
import by.godevelopment.sixthtask.di.AppComponent
import by.godevelopment.sixthtask.di.DaggerAppComponent

class TaskApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is TaskApp -> appComponent
        else -> applicationContext.appComponent
    }