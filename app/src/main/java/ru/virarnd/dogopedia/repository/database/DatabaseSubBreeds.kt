package ru.virarnd.dogopedia.repository.database

class DatabaseSubBreeds(
    private val breedsDatabaseConverter: BreedsDatabaseConverter,
    private val breedsDatabase: BreedsDatabase
) {

//    suspend fun getSubBreedByName(name: String): SubBreed {
//        return breedsDatabaseConverter.toSubBreed(breedsDatabase.subBreedsDao.getSubBreedByName(name))
//    }

}