package com.vuanhshow.coroutines.examples.example3

import kotlinx.coroutines.*
import java.time.LocalDateTime
fun main() {
  runBlocking {
    launch {
      println("Start Coroutine 1")
      delay(5000)
      println("End Coroutine 1")
    }

    launch {
      println("Start Coroutine 2")
      delay(3000)
      println("End Coroutine 2")
    }
  }
}

