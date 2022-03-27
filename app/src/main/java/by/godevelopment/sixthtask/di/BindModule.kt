package by.godevelopment.sixthtask.di

import by.godevelopment.sixthtask.data.DataRepositoryImpl
import by.godevelopment.sixthtask.domain.DataRepository
import dagger.Binds
import dagger.Module

@Module
abstract class BindModule {

    @Binds
    abstract fun bind_DataRepositoryImpl_to_DataRepository(
        dataRepository: DataRepositoryImpl
    ): DataRepository
}