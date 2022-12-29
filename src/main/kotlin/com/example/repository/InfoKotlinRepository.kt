package com.example.repository

import com.example.models.ApiResponse
import com.example.models.InfoKotlin

interface InfoKotlinRepository {

    val ktInfo: Map<Int, List<InfoKotlin>>

    val page1: List<InfoKotlin>
    val page2: List<InfoKotlin>
    val page3: List<InfoKotlin>


    suspend fun getAllKotlinInfo(page: Int = 1): ApiResponse
    suspend fun searchKotlinInfo(title: String?): ApiResponse

}