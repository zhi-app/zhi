package com.zhi.common.net

import dagger.Module
import dagger.Provides
import dagger.multibindings.Multibinds
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
abstract class NetAppRetrofitModule private constructor() {

    @Multibinds
    abstract fun converters(): Set<Converter.Factory>

    @Multibinds
    abstract fun adapters(): Set<CallAdapter.Factory>

    @Module
    companion object {
        @JvmStatic
        @Provides
        @NetScope
        @NetAppContext
        fun retrofit(@NetAppContext httpClient: OkHttpClient): Retrofit = Retrofit.Builder().apply {
            client(httpClient)
            baseUrl("http://localhost:8888/")
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        }.build()
    }
}
