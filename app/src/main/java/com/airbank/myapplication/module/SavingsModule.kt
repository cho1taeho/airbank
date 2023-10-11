package com.airbank.myapplication.module

import com.airbank.myapplication.api.ApiService
import com.airbank.myapplication.network.RetrofitBuilder
import com.airbank.myapplication.repository.LoanRepository
import com.airbank.myapplication.repository.SavingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SavingsModule {


    @Provides
    @Singleton
    fun provideSavingsRepository(apiService: ApiService): SavingsRepository = SavingsRepository(apiService)
}
