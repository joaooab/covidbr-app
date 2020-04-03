package br.com.covidbr.di

import br.com.covidbr.data.contry.CountryService
import br.com.covidbr.data.news.NewsService
import br.com.covidbr.data.region.RegionService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val serviceModule = module {
    single {
        createWebService<RegionService>("https://api.apify.com/v2/key-value-stores/TyToNta7jGKkpszMZ/records/")
    }

    single {
        createWebService<CountryService>("https://covidapi.info/api/v1/global/")
    }
    single { createWebService<NewsService>("http://newsapi.org/v2/") }
}

inline fun <reified T> createWebService(url: String): T {

    val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    return retrofit.create(T::class.java)
}
