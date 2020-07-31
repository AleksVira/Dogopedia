package ru.virarnd.dogopedia.repository.network

const val breedsBaseUrl: String = "https://dog.ceo/api/"

const val ALL_BREEDS: String = "breeds/list/all"

const val SINGLE_BREED_BY_NAME: String = "breed/{name}/images"

const val SUB_BREED_BY_NAME: String = "breed/{parentName}/{subBreedName}/images"