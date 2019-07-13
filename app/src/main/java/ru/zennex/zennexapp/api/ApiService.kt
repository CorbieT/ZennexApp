package ru.zennex.zennexapp.api

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import ru.zennex.zennexapp.common.BASE_URL
import ru.zennex.zennexapp.common.QUOTE_URL
import ru.zennex.zennexapp.model.Quotes

interface ApiService {
    @GET(QUOTE_URL)
    fun getQuotes(): Observable<Quotes>

    companion object {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}