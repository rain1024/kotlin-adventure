package org.example

import kotlinx.coroutines.*

fun mainCoroutine3() = runBlocking {
  launch {
    println("Coroutine 1")
    delay(1000)
    println("End Coroutine 1")
  }
}
