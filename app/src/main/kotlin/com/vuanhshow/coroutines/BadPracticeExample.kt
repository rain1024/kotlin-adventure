package com.vuanhshow.coroutines

import kotlin.system.measureTimeMillis
import kotlinx.coroutines.*

fun main() {
  println("Benchmark: Regular Map vs Inefficient Coroutines")

  runBlocking {
    // Initialize list with 1 million items
    val itemCount = 1_000_000
    val originalList = List(itemCount) { it.toLong() }

    // Benchmark 1: Regular map
    val regularMapTime = measureTimeMillis {
      val transformedRegular = originalList.map { it * it }
      // Force evaluation of the result
      transformedRegular.size
    }
    println("Benchmark 1: Regular map took: $regularMapTime ms")

    // Benchmark 2: Async coroutines
    val coroutineMapTime = measureTimeMillis {

      val transformedAsync = withContext(Dispatchers.Default) {
        originalList.map { async { it * it } }.awaitAll()
      }
              
      // Force evaluation of the result
      transformedAsync.size
    }
    println("Benchmark 2: Coroutine map took: $coroutineMapTime ms")

    println("Comparison - Coroutines ${if (coroutineMapTime < regularMapTime) "faster" else "slower"} by: ${Math.abs(regularMapTime - coroutineMapTime)} ms"
    )
  }
}
