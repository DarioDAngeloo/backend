package com.example.routes

import com.example.repository.InfoKotlinRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.searchKotlinInfo(){
    val kotlinInfoRepository: InfoKotlinRepository by inject()

    get("/learn/kotlin/search"){
        val title = call.request.queryParameters["title"]

        val apiResponse = kotlinInfoRepository.searchKotlinInfo(title = title)
        call.respond(
            message = apiResponse,
            status = HttpStatusCode.OK
        )
    }
}