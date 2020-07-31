package ru.virarnd.dogopedia.repository.network.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.virarnd.dogopedia.BuildConfig
import ru.virarnd.dogopedia.repository.network.*
import java.util.concurrent.TimeUnit

val networkKoinModule: Module = module {
    single {
        BreedsNetwork(
            breedsApi = get(),
            networkDataConverter = get(),
            responseHandler = get()
        )
    }

    single { ResponseHandler() }
    single { NetworkDataConverter() }

    single { provideHttpClient() }
    single { provideMoshiConverterFactory() }

    single {
        provideRetrofit(
            httpClient = get(),
            converterFactory = get()
        )
    }

    single {
        provideBreedsApi(
            retrofit = get()
        )
    } bind BreedsApi::class
}

fun provideBreedsApi(retrofit: Retrofit): BreedsApi {
    return retrofit.create(BreedsApi::class.java)
}

fun provideRetrofit(
    httpClient: OkHttpClient,
    converterFactory: Converter.Factory
): Retrofit {
    return Retrofit.Builder()
        .client(httpClient)
        .baseUrl(breedsBaseUrl)
        .addConverterFactory(converterFactory)
        .build()
}

fun provideHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
    }

    return OkHttpClient()
        .newBuilder()
        .addNetworkInterceptor(httpLoggingInterceptor)
        .connectTimeout(20, TimeUnit.SECONDS) // If backend is really slow
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()
}

fun provideMoshiConverterFactory(): Converter.Factory {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    return MoshiConverterFactory.create(moshi)
}