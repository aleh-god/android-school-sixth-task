package by.godevelopment.sixthtask.di

import by.godevelopment.sixthtask.commons.BASE_URL
import by.godevelopment.sixthtask.data.MetaApi
import by.godevelopment.sixthtask.data.RemoteDataSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    fun provideBaseUrl() : String = BASE_URL

    @Provides
    fun provideMoshi() : Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
        )
        .build()

    @Provides
    fun provideRetrofit(
        moshi: Moshi,
        BASE_URL : String,
        okHttpClient: OkHttpClient
    ) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    fun provideMetaApi(retrofit: Retrofit): MetaApi = retrofit.create(MetaApi::class.java)

    @Provides
    fun provideRemoteMetaDataSource(
        metaApi: MetaApi
    ): RemoteDataSource = RemoteDataSource(metaApi)
}