package ru.virarnd.dogopedia.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.virarnd.dogopedia.breed_list.detail.vm.DetailViewModel
import ru.virarnd.dogopedia.breed_list.main.vm.BreedListViewModel
import ru.virarnd.dogopedia.favourites.detail.vm.FavDetailViewModel
import ru.virarnd.dogopedia.favourites.main.vm.FavouritesListViewModel
import ru.virarnd.dogopedia.repository.database.BreedsDatabaseConverter
import ru.virarnd.dogopedia.repository.BreedsRepository

val appKoinModule: Module = module {

    single {
        BreedsRepository(
            breedsNetwork = get(),
            breedsDatabase = get(),
            breedsDatabaseConverter = provideBreedsDatabaseConverter()
        )
    }

    viewModel {
        BreedListViewModel(repository = get())
    }

    viewModel {
        DetailViewModel(repository = get())
    }

    viewModel {
        FavouritesListViewModel(repository = get())
    }

    viewModel {
        FavDetailViewModel(repository = get())
    }
}

fun provideBreedsDatabaseConverter() =
    BreedsDatabaseConverter()
