package com.vuanhshow.coroutines

import kotlin.system.measureTimeMillis
import kotlinx.coroutines.*

fun main() {
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

  // Benchmark 2: Coroutine map (original)
  val coroutineMapTime = measureTimeMillis {
    runBlocking {
      val transformedAsync =
              withContext(Dispatchers.Default) { originalList.map { async { it * it } }.awaitAll() }
      // Force evaluation of the result
      transformedAsync.size
    }
  }
  println("Benchmark 2: Coroutine map took: $coroutineMapTime ms")
  println(
          "Comparison - Coroutines ${if (coroutineMapTime < regularMapTime) "faster" else "slower"} by: ${Math.abs(regularMapTime - coroutineMapTime)} ms"
  )

  // Benchmark 3: Chunked coroutine map (improved)
  val chunkSize = 10000 // Process 10,000 items per coroutine
  val chunkedCoroutineMapTime = measureTimeMillis {
    runBlocking {
      val transformedChunked =
              withContext(Dispatchers.Default) {
                originalList
                        .chunked(chunkSize)
                        .map { chunk -> async { chunk.map { it * it } } }
                        .awaitAll()
                        .flatten()
              }
      // Force evaluation of the result
      transformedChunked.size
    }
  }
  println("Benchmark 3: Chunked coroutine map took: $chunkedCoroutineMapTime ms")

  // Benchmark 4: Using built-in parallelStream from Java
  val parallelStreamTime = measureTimeMillis {
    val transformedParallel = originalList.parallelStream().map { it * it }.toList()
    // Force evaluation of the result
    transformedParallel.size
  }
  println("Benchmark 4: Java parallelStream took: $parallelStreamTime ms")

  // Benchmark 5: Optimized for CPU count
  val cpuCount = Runtime.getRuntime().availableProcessors()
  val optimalChunkSize = itemCount / cpuCount + 1

  val optimizedCoroutineTime = measureTimeMillis {
    runBlocking {
      val transformedOptimized =
              withContext(Dispatchers.Default) {
                originalList
                        .chunked(optimalChunkSize)
                        .map { chunk -> async { chunk.map { it * it } } }
                        .awaitAll()
                        .flatten()
              }
      // Force evaluation of the result
      transformedOptimized.size
    }
  }
  println("Benchmark 5: CPU-optimized coroutines took: $optimizedCoroutineTime ms")

  // Benchmark 6: Using a custom coroutine dispatcher with limited parallelism
  val customDispatcher = Dispatchers.Default.limitedParallelism(cpuCount)
  val customDispatcherTime = measureTimeMillis {
    runBlocking {
      val transformedCustom =
              withContext(customDispatcher) {
                originalList
                        .chunked(optimalChunkSize)
                        .map { chunk -> async { chunk.map { it * it } } }
                        .awaitAll()
                        .flatten()
              }
      // Force evaluation of the result
      transformedCustom.size
    }
  }
  println("Benchmark 6: Custom dispatcher coroutines took: $customDispatcherTime ms")

  // Print overall comparison
  println("\nOverall Comparison:")
  val benchmarks =
          listOf(
                  "Regular map" to regularMapTime,
                  "Original coroutine map" to coroutineMapTime,
                  "Chunked coroutine map" to chunkedCoroutineMapTime,
                  "Java parallelStream" to parallelStreamTime,
                  "CPU-optimized coroutines" to optimizedCoroutineTime,
                  "Custom dispatcher coroutines" to customDispatcherTime
          )

  val fastest = benchmarks.minByOrNull { it.second }

  benchmarks.forEach { (name, time) ->
    val comparison =
            if (fastest != null && time != fastest.second) {
              "slower than ${fastest.first} by ${time - fastest.second} ms"
            } else {
              "FASTEST"
            }
    println("$name: $time ms - $comparison")
  }
}
