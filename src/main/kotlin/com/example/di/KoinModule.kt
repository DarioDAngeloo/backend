package com.example.di

import com.example.repository.InfoKotlinImpl
import com.example.repository.InfoKotlinRepository
import org.koin.dsl.module

val koinModule = module {
    single<InfoKotlinRepository> {
        InfoKotlinImpl()
    }
}