package com.example.routes

import com.example.models.ApiResponse
import com.example.repository.InfoKotlinRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.getAllKotlinInfo() {
    val kotlinInfoRepository : InfoKotlinRepository by inject()


    get("/learn/kotlin") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            require(page in 1..3)
            val apiResponse = kotlinInfoRepository.getAllKotlinInfo(page = page)
            call.respond(message = apiResponse, status = HttpStatusCode.OK)

        } catch (e: NumberFormatException) {
            call.respond(
                message = ApiResponse(success = false, message = "Please enter only numbers"),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: IllegalArgumentException) {
            call.respond(
                message = ApiResponse(success = false, message = "Data not found"),
                status = HttpStatusCode.NotFound
            )
        }
    }
}