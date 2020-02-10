package com.example.githubsearchrepoapp.common.di

import android.content.Context
import androidx.room.Room
import com.example.githubsearchrepoapp.common.ApplicationDatabase
import com.example.githubsearchrepoapp.common.Mapper
import com.example.githubsearchrepoapp.data.search.SearchDataRepository
import com.example.githubsearchrepoapp.data.search.api.SearchApi
import com.example.githubsearchrepoapp.data.search.datasource.SearchLocalDataSource
import com.example.githubsearchrepoapp.data.search.datasource.SearchRemoteDataSource
import com.example.githubsearchrepoapp.data.search.dto.SearchResultDto
import com.example.githubsearchrepoapp.data.search.mapper.SearchedRepoMapper
import com.example.githubsearchrepoapp.domain.search.interactor.DeleteStoredRepoUseCase
import com.example.githubsearchrepoapp.domain.search.interactor.SearchUseCase
import com.example.githubsearchrepoapp.domain.search.model.SearchResult
import com.example.githubsearchrepoapp.domain.search.repository.SearchRepository
import com.example.githubsearchrepoapp.presentation.search.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext


val appModule = module {

    // Network
    single { provideRetrofit() }
    single { provideMoviesApi(get()) }

    // Database
    single { provideApplicationDatabase(get()) }
    single { (get() as ApplicationDatabase).searchDao() }

    // DataSource
    single { SearchRemoteDataSource(get()) }
    single { SearchLocalDataSource(get()) }

    // Repository
    single<SearchRepository> { SearchDataRepository(get(), get(), get()) }

    //Mapper
    single<Mapper<SearchResultDto, SearchResult>> { SearchedRepoMapper() }

    // Interactor
    single { SearchUseCase(get()) }
    single { DeleteStoredRepoUseCase(get()) }

    // ViewModel
    viewModel { SearchViewModel(get()) }
}

fun provideRetrofit(): Retrofit =
    Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun provideMoviesApi(retrofit: Retrofit): SearchApi = retrofit.create(SearchApi::class.java)

fun provideApplicationDatabase(context: Context): ApplicationDatabase =
    Room.databaseBuilder(context.applicationContext, ApplicationDatabase::class.java, "app.db")
        .fallbackToDestructiveMigration()
        .build()