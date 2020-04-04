package br.com.covidbr.di

import br.com.covidbr.data.region.RegionRepository
import br.com.covidbr.data.region.RegionRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<RegionRepository> { RegionRepositoryImpl(get()) }
}