package de.riskident

/**
  * Simple entry point to manually run OrderProcessor
  * Can be used to validate output from command line.
  */

object Main {
  def main(args: Array[String]): Unit = {
    println("Paste input (Command+D to finish):")
    val result = OrderProcessor.process(System.in)
    println(s"Result: $result")
  }
}
