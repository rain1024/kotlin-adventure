package com.vuanhshow.coroutines.examples.using_coroutines

import kotlinx.coroutines.*

suspend fun fetchUserName(): String{
  delay(1000)
  return "John Doe"
}

fun main() = runBlocking {
    println("Start fetching user name...")
    val userName = fetchUserName()
    println("User name: $userName")
}

