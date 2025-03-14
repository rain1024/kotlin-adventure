package com.vuanhshow.coroutines.examples.using_coroutines

import kotlinx.coroutines.*

fun main() = runBlocking {
    launch {
        delay(1000)
        println("Coroutine!")
    }
    print("Hello ")
}