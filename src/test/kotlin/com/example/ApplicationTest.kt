package com.example

import com.example.models.ApiResponse
import com.example.plugins.configureRouting
import com.example.repository.InfoKotlinImpl
import com.example.repository.InfoKotlinRepository
import com.example.repository.NEXT_PAGE_KEY
import com.example.repository.PREV_PAGE_KEY
import io.ktor.client.call.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlin.test.*
import io.ktor.server.testing.*
import io.ktor.util.Identity.decode
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull.content
import org.koin.java.KoinJavaComponent.inject
import kotlin.math.exp

class ApplicationTest {


    @Test
    fun accessRootEndPoint_AssertCorrectInformation() = testApplication {
        val response = client.get("/")
        assertEquals(expected = HttpStatusCode.OK, actual = response.status)
        assertEquals(expected = "Learn Android API backend server", actual = response.bodyAsText())
    }


    @Test
    fun `access kotlin endpoint, assert response from default page`() = testApplication {
        val infoKotlinRepository = InfoKotlinImpl()
        val response = client.get("/learn/kotlin")
        assertEquals(expected = HttpStatusCode.OK, actual = response.status)
        //declaring variables to get response
        val expectedResponse = ApiResponse(
            success = true,
            message = "ok",
            prevPage = null,
            nextPage = 2,
            infoKotlin = infoKotlinRepository.page1
        )
        val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText())
        //implementing the variables and verifying the response
        assertEquals(expected = expectedResponse, actual = actual)
    }


    //Testing all the pages at once
    @Test
    fun `access al kotlin pages endpoint, query all pages, assert correct information`() = testApplication {
        val infoKotlinRepository = InfoKotlinImpl()
        val pages = 1..3
        val kotlinInfoPages = listOf(
            infoKotlinRepository.page1,
            infoKotlinRepository.page2,
            infoKotlinRepository.page3,
        )
        pages.forEach { pageNumber ->
            //
            client.get("/learn/kotlin?page=$pageNumber").apply {
                assertEquals(expected = HttpStatusCode.OK, actual = status)
                // creating variables to pass it to assert
                val expectedResponse = ApiResponse(
                    success = true,
                    message = "ok",
                    prevPage = calculatePage(page = pageNumber)["prevPage"],
                    nextPage = calculatePage(page = pageNumber)["nextPage"],
                    infoKotlin = kotlinInfoPages[pageNumber - 1]
                )
                val actualResponse = Json.decodeFromString<ApiResponse>(bodyAsText())
                //
                println("CURRENT PAGE: $pageNumber")
                println("PREV PAGE: ${calculatePage(page = pageNumber)["prevPage"]}")
                println("NEXT PAGE: ${calculatePage(page = pageNumber)["nextPage"]}")
                // confirming the correct results
                assertEquals(expected = expectedResponse, actual = actualResponse)
                //


            }
        }
    }

    //
    @Test
    fun `access kotlin endpoint, query non existing page number , assert error`() = testApplication {
        val response = client.get("/learn/kotlin?page=23")
        assertEquals(expected = HttpStatusCode.NotFound, actual = response.status)
        //declaring variables to get response
        assertEquals(expected = "Data not found", actual = response.bodyAsText())

    }
    //
    //
    @Test
    fun `access kotlin endpoint, query invalid input , assert error`() = testApplication {
        val response = client.get("/learn/kotlin?page=hello")
        assertEquals(expected = HttpStatusCode.BadRequest, actual = response.status)
        //declaring variables to get response
        val expectedResult = ApiResponse(
            success = false,
            message = "Please enter only numbers"
        )
        val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText())
        //
        assertEquals(expected = expectedResult, actual = actual)

    }
    //
    //
    @Test
    fun `access search kotlin info , title query, assert single kotlin info`() = testApplication {
        val response = client.get("/learn/kotlin/search?title=coroutines")
        assertEquals(expected = HttpStatusCode.OK, actual = response.status)
        //declaring variables to get response

        val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText()).infoKotlin.size
        //
        assertEquals(expected = 1, actual = actual)

    }
    //
    //
    @Test
    fun `access search kotlin info , title query, assert multiple kotlin info`() = testApplication {
        val response = client.get("/learn/kotlin/search?title=co")
        assertEquals(expected = HttpStatusCode.OK, actual = response.status)
        //declaring variables to get response

        val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText()).infoKotlin.size
        //
        assertEquals(expected = 2, actual = actual)
    }
    //
    //
    @Test
    fun `access search kotlin info , empty title query, assert empty list as result`() = testApplication {
        val response = client.get("/learn/kotlin/search?title=")
        assertEquals(expected = HttpStatusCode.OK, actual = response.status)
        //declaring variables to get response

        val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText()).infoKotlin
        //
        assertEquals(expected = emptyList(), actual = actual)
    }
    //
    //
    @Test
    fun `access search kotlin info , query non existing title, assert empty list as result`() = testApplication {
        val response = client.get("/learn/kotlin/search?title=unknown")
        assertEquals(expected = HttpStatusCode.OK, actual = response.status)
        //declaring variables to get response

        val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText()).infoKotlin
        //
        assertEquals(expected = emptyList(), actual = actual)
    }
    //
    //
    @Test
    fun `access non existing endpoint , assert not found`() = testApplication {
        val response = client.get("/unknown")
        assertEquals(expected = HttpStatusCode.NotFound, actual = response.status)
        //declaring variables to get response

        //
        assertEquals(expected = "Data not found", actual = response.bodyAsText())
    }

    //
    //
    // Function out of the scope from all test
    //
    //

    private fun calculatePage(page: Int): Map<String, Int?> {
        var prevPage: Int? = page
        var nextPage: Int? = page
        if (page in 1..2) {
            nextPage = nextPage?.plus(1)
        }
        if (page in 2..3) {
            prevPage = prevPage?.minus(1)
        }
        if (page == 1) {
            prevPage = null
        }
        if (page == 3) {
            nextPage = null
        }
        return mapOf(PREV_PAGE_KEY to prevPage, NEXT_PAGE_KEY to nextPage)
    }
    //
}
