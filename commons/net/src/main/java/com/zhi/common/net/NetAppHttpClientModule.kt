package com.zhi.common.net

import com.google.common.base.Optional
import dagger.BindsOptionalOf
import dagger.Module
import dagger.Provides
import dagger.multibindings.Multibinds
import okhttp3.Dns
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Named

@Module
abstract class NetAppHttpClientModule private constructor() {

    @Multibinds
    @NetAppContext
    internal abstract fun interceptors(): Set<Interceptor>

    @Multibinds
    @Named("network")
    internal abstract fun networkInterceptors(): Set<Interceptor>

    @BindsOptionalOf
    @NetAppContext
    internal abstract fun dns(): Dns

    @Module
    companion object {
        @JvmStatic
        @Provides
        @NetScope
        @NetAppContext
        fun httpClient(
            @NetAppContext dns: Optional<Dns>,
            @NetAppContext interceptors: @JvmSuppressWildcards(true) Set<Interceptor>,
            @Named("network") netInterceptors: @JvmSuppressWildcards(true) Set<Interceptor>
        ): OkHttpClient = OkHttpClient.Builder().apply {
            if (dns.isPresent) {
                dns(dns.get())
            }
            interceptors.forEach {
                addInterceptor(it)
            }
            netInterceptors.forEach {
                addNetworkInterceptor(it)
            }
        }.build()
    }
}