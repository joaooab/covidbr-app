package br.com.covidbr.di

import br.com.covidbr.data.contry.CountryRepository
import br.com.covidbr.data.contry.CountryRepositoryImpl
import br.com.covidbr.data.dashboard.DashboardRepository
import br.com.covidbr.data.dashboard.DashboardRepositoryImpl
import br.com.covidbr.data.region.RegionRepository
import br.com.covidbr.data.region.RegionRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<RegionRepository> { RegionRepositoryImpl(get()) }
    single<CountryRepository> { CountryRepositoryImpl(get()) }
    single<DashboardRepository> { DashboardRepositoryImpl(get()) }
}