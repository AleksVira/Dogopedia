package ru.virarnd.dogopedia.repository.database.di

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.virarnd.dogopedia.repository.database.BreedsDatabase
import ru.virarnd.dogopedia.repository.database.BreedsDatabaseConverter

val databaseKoinModule: Module = module {

    fun provideDatabase(application: Application): BreedsDatabase {
        return Room.databaseBuilder(application, BreedsDatabase::class.java, "breeds.db")
            .fallbackToDestructiveMigration()
            .build()
    }

//    fun provideBreedsDatabaseConverter() =
//        BreedsDatabaseConverter()

    single { provideDatabase(application = androidApplication()) }
}


