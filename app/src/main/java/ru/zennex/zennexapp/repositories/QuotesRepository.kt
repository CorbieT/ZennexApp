package ru.zennex.zennexapp.repositories

import io.reactivex.Observable
import ru.zennex.zennexapp.api.ApiService
import ru.zennex.zennexapp.model.Quotes

class QuotesRepository (private val apiService: ApiService) {
    fun uploadQuotes(): Observable<Quotes> {
        return apiService.getQuotes()
    }
}

object QuotesRepositoryProvider {
    fun provideQuotesRepository(): QuotesRepository {
        return QuotesRepository(ApiService.create())
    }
}