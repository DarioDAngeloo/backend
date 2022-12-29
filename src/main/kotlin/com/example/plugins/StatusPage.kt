package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*


fun Application.configureStatusPage(){
    install(StatusPages){
        status(HttpStatusCode.NotFound) {
            call , status -> call.respondText(text = "Data not found", status = status)
        }
    }

}