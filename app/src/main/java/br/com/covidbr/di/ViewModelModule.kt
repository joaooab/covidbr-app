package br.com.covidbr.di

import br.com.covidbr.ui.country.CountryViewModel
import br.com.covidbr.ui.home.HomeViewModel
import br.com.covidbr.ui.slideshow.NewsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        CountryViewModel(get())
    }
    viewModel {
        NewsViewModel(get())
    }
}