package com.example.plugins

import com.example.routes.getAllKotlinInfo
import com.example.routes.root
import com.example.routes.searchKotlinInfo
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*

fun Application.configureRouting() {

    routing {
        root()
        getAllKotlinInfo()
        searchKotlinInfo()

        static("/images") {
            resources("/images")
        }
    }
}
