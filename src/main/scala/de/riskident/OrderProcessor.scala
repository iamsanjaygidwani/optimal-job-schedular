package de.riskident

import java.io.InputStream
import scala.io.Source

object OrderProcessor {

  /**
    * @param    An InputStream, which contains the following input:
    *           A line containing a single number: The number of guests N,
    *           Followed by N lines containing two numbers Ti and Li separated by space.
    *           There may be a trailing newline.
    *           Ti is the ordering time for Ni, Li is the time it takes to bake Ni's pizza.
    *           0 <= N  <= 100000
    *           0 <= Ti <= 1000000000
    *           1 <= Li <= 1000000000
    * @return   A Right containing the integer part of the average waiting time if the input is valid.
    *           A Left containing a syntax error description otherwise.
    */

  case class Customer(arrival: Int, duration: Int)

  def process(in: InputStream): Either[String, Long] = {
    try {
      val lines = Source.fromInputStream(in).getLines().filter(_.nonEmpty).toList
      if (lines.isEmpty) return Right(0L)

      val n = lines.head.toIntOption.getOrElse(return Left("Invalid number of customers"))
      if (n == 0) return Right(0L)

      val customers = lines.tail.take(n).map { line =>
        val parts = line.split(" ")
        if (parts.length != 2) return Left(s"Invalid customer entry: $line")
        val t = parts(0).toIntOption.getOrElse(return Left(s"Invalid arrival time: ${parts(0)}"))
        val l = parts(1).toIntOption.getOrElse(return Left(s"Invalid duration: ${parts(1)}"))
        Customer(t, l)
      }

      val result = ShortestJobFirstScheduler.computeMinAverageWaitingTime(customers)
      Right(result)
    } catch {
      case e: Exception => Left(s"Unhandled exception: ${e.getMessage}")
    }
  }
}
