package com.vuanhshow.coroutines.examples

import kotlinx.coroutines.*

fun main() = runBlocking {
    launch {
        delay(1000)
        println("Coroutine!")
    }
    print("Hello ")
}