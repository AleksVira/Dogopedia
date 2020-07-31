package ru.virarnd.dogopedia.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.virarnd.dogopedia.BuildConfig
import ru.virarnd.dogopedia.repository.database.di.databaseKoinModule
import ru.virarnd.dogopedia.di.appKoinModule
import ru.virarnd.dogopedia.repository.network.di.networkKoinModule
import timber.log.Timber
import timber.log.Timber.DebugTree


class BreedsApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@BreedsApp)
            modules(
                listOf(
                    databaseKoinModule,
                    networkKoinModule,
                    appKoinModule
                )
            )

        }
    }
}