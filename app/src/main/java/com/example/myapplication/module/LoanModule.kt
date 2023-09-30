//package com.example.myapplication.module
//
//import com.example.myapplication.api.ApiService
//import com.example.myapplication.network.RetrofitBuilder
//import com.example.myapplication.repository.LoanRepository
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//class LoanModule {
//    @Provides
//    @Singleton
//    fun provideApiService(): ApiService = RetrofitBuilder.createApiService()
//
//    @Provides
//    @Singleton
//    fun provideLoanRepository(apiService: ApiService): LoanRepository = LoanRepository(apiService)
//}