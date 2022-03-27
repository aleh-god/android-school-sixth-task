package by.godevelopment.sixthtask.di

import android.content.Context
import by.godevelopment.sixthtask.MainActivity
import by.godevelopment.sixthtask.presentation.ListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: ListFragment)
}