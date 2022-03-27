package by.godevelopment.sixthtask.di

import dagger.Module

@Module(includes = [DataModule::class, BindModule::class, ViewModelModule::class])
class AppModule