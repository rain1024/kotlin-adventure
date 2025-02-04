package com.vuanhshow.coroutines.examples.example4.example41

import java.time.LocalDateTime
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.*

fun log(message: String, context: CoroutineContext) {
    val coroutineName = context[CoroutineName]?.name ?: "unnamed"
    val job = context[Job] // Get the Job from the context
    val coroutineId = job?.toString() ?: "unknown" // Use the Job's toString() as a unique ID
    println("\u001B[36m[${LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss.SSS"))}]\u001B[0m " +
           "\u001B[33m[${Thread.currentThread().name.padEnd(5)}]\u001B[0m " + 
           "\u001B[32m[${coroutineName.padEnd(12)}]\u001B[0m " +
           "\u001B[35m[${coroutineId.takeLast(8).padEnd(8)}]\u001B[0m " +
           "\u001B[37m$message\u001B[0m")
}

fun main() = runBlocking {
    // Launch job1 with a specific name
    val job1 = launch(CoroutineName("Coroutine-1")) {
        log("job1 started", coroutineContext)
        delay(1000) // Simulate some work
        log("job1 finished", coroutineContext)
    }

    // Launch job2 with a different name
    val job2 = launch(CoroutineName("Coroutine-2")) {
        log("job2 started", coroutineContext)
        delay(2000) // Simulate some work
        log("job2 finished", coroutineContext)
    }

    // Don't wait for the jobs to complete
    log("Program finished", coroutineContext)
}