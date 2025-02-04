package org.example

import kotlinx.coroutines.*

suspend fun networkRequest(): String {
  println("Network request...")
  delay(1000)
  println("Network request finished")
  return "My Data"
}

suspend fun showData(data: String) {
  println("Data loaded: $data")
}

suspend fun loadData() {
  val data = networkRequest()
  showData(data)
}

fun main() = runBlocking {
  println("Starting...")
  loadData()
  println("Finished!")
}
