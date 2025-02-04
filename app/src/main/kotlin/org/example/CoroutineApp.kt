package org.example

import kotlinx.coroutines.*

fun main() = runBlocking {
  launch {
    delay(1000)
    println("Coroutine!")
  }
  println("Hello ")
}
