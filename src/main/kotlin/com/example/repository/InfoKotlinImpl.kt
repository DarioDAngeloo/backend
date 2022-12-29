package com.example.repository

import com.example.models.ApiResponse
import com.example.models.InfoKotlin


const val PREV_PAGE_KEY = "prevPage"
const val NEXT_PAGE_KEY = "nextPage"

class InfoKotlinImpl : InfoKotlinRepository {

    override val ktInfo: Map<Int, List<InfoKotlin>> by lazy {
        mapOf(
            1 to page1,
            2 to page2,
            3 to page3,
        )
    }

    override val page1 = listOf(
        InfoKotlin(
            id = 1,
            title = "Variables",
            image = "/images/variables.png",
            about = " They are the names you give to computer memory " +
                    "locations which are used to store values in a computer" +
                    " program and later you use those names to retrieve the" +
                    " stored values and use them in your program.",
            tags = listOf("Google, Future, Development"),
            ranking = 2.0,
            yearRelease = 2019,
            difficulty = "easy"
        ),
        InfoKotlin(
            id = 2,
            title = "Coroutines",
            image = "/images/coroutines.png",
            about = "A coroutine is a concurrency design pattern that you can use on" +
                    "Android to simplify code that executes asynchronously",
            tags = listOf("Google, Future, Development"),
            ranking = 3.0,
            yearRelease = 2020,
            difficulty = "difficulty"
        ),
        InfoKotlin(
            id = 3,
            title = "Flows",
            image = "/images/flows.png",
            about = " In coroutines, a flow is a type that can emit multiple values sequentially," +
                    "as opposed to suspend functions that return only a single value." +
                    "For example, you can use a flow to receive live updates from a database",
            tags = listOf("Google, Future, Development"),
            ranking = 3.5,
            yearRelease = 2008,
            difficulty = "medium"
        )
    )
    override val page2 = listOf(
        InfoKotlin(
            id = 4,
            title = "Sealed Classes",
            image = "/images/sealed.jpg",
            about = " Sealed classes and interfaces represent restricted class hierarchies that provide more control over inheritance. All direct subclasses of a sealed class are known at compile time. No other subclasses may appear outside a module within which the sealed class is defined",
            tags = listOf("Google, Future, Development"),
            ranking = 2.2,
            yearRelease = 2019,
            difficulty = "easy"
        ),
        InfoKotlin(
            id = 5,
            title = "Interfaces",
            image = "/images/interface.jpg",
            about = " Interfaces in Kotlin can contain declarations of abstract methods, as well as method implementations. What makes them different from abstract classes is that interfaces cannot store state. They can have properties, but these need to be abstract or provide accessor implementations.",
            tags = listOf("Google, Future, Development"),
            ranking = 2.2,
            yearRelease = 2019,
            difficulty = "easy"
        ),
        InfoKotlin(
            id = 6,
            title = "Data Class",
            image = "/images/dataclass.jpg",
            about = " It is not unusual to create classes whose main purpose is to hold data. In such classes, some standard functionality and some utility functions are often mechanically derivable from the data. In Kotlin, these are called data classes and are marked with data",
            tags = listOf("Google, Future, Development"),
            ranking = 1.2,
            yearRelease = 2019,
            difficulty = "easy"
        )
    )
    override val page3 = listOf(
        InfoKotlin(
            id = 7,
            title = "Objects",
            image = "/images/objects.jpg",
            about = " They are the names you give to computer memory " +
                    "locations which are used to store values in a computer" +
                    " program and later you use those names to retrieve the" +
                    " stored values and use them in your program.",
            tags = listOf("Google, Future, Development"),
            ranking = 7.2,
            yearRelease = 1998,
            difficulty = "beginner"
        ),
        InfoKotlin(
            id = 8,
            title = "Constructors",
            image = "/images/constuctors.jpg",
            about = " A Kotlin constructor is a special member function in a class that is invoked when an object is instantiated. Whenever an object is created, the defined constructor is called automatically which is used to initialize the properties of the class.",
            tags = listOf("Google, Future, Development"),
            ranking = 4.2,
            yearRelease = 2017,
            difficulty = "mid-level"
        ),
        InfoKotlin(
            id = 9,
            title = "Extensions",
            image = "/images/extensions.jpg",
            about = " Kotlin provides the ability to extend a class or an interface with new functionality without having to inherit from the class or use design patterns such as Decorator. This is done via special declarations called extensions. ",
            tags = listOf("Google, Future, Development"),
            ranking = 6.0,
            yearRelease = 2015,
            difficulty = "Senior"
        ),

        )


    override suspend fun getAllKotlinInfo(page: Int): ApiResponse {
        return ApiResponse(
            success = true,
            message = "ok",
            prevPage = calculatePage(page = page)[PREV_PAGE_KEY],
            nextPage = calculatePage(page = page)[NEXT_PAGE_KEY],
            infoKotlin = ktInfo[page]!!
        )
    }

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


    override suspend fun searchKotlinInfo(title: String?): ApiResponse {
        return ApiResponse(
            success = true,
            message = "ok",
            infoKotlin = findKotlinInfo(query = title)
        )
    }

    private fun findKotlinInfo(query: String?): List<InfoKotlin> {
        val founded = mutableListOf<InfoKotlin>()
        return if (!query.isNullOrEmpty()) {
            ktInfo.forEach { (_, ktInfo) ->
                ktInfo.forEach { infoKotlinItem ->
                    if (infoKotlinItem.title.lowercase().contains(query.lowercase())) {
                        founded.add(infoKotlinItem)
                    }
                }
            }
            return founded
        } else {
            emptyList()
        }
    }
}