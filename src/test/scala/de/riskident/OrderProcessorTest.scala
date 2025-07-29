package de.riskident

import java.io.{ByteArrayInputStream, InputStream}
import java.nio.charset.StandardCharsets
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.{FunSuite, MustMatchers}

class OrderProcessorTest extends FunSuite with MustMatchers with TypeCheckedTripleEquals {

  def asInput(str: String): InputStream =
    new ByteArrayInputStream(str.stripMargin.getBytes(StandardCharsets.UTF_8))

  test("provided test data 1") {
    val input =
      """3
        |0 3
        |1 9
        |2 6
        |""".stripMargin
    OrderProcessor.process(asInput(input)) must === (Right(9L))
  }

  test("provided test data 2 (with trailing empty line)") {
    val input =
      """3
        |0 3
        |1 9
        |2 5
        |
        |""".stripMargin
    OrderProcessor.process(asInput(input)) must === (Right(8L))
  }

  test("zero customers") {
    val input = "0\n"
    OrderProcessor.process(asInput(input)) must === (Right(0L))
  }

  test("single customer") {
    val input = "1\n5 10\n"
    OrderProcessor.process(asInput(input)) must === (Right(10L))
  }

  test("invalid number of customers") {
    val input = "x\n0 1\n"
    OrderProcessor.process(asInput(input)).left.get.toLowerCase must include ("invalid")
  }

  test("incomplete customer line") {
    val input = "2\n0 3\n1\n"
    OrderProcessor.process(asInput(input)).left.get.toLowerCase must include ("invalid")
  }

  test("sparse arrival times with reordering") {
    val input =
      """3
        |0 3
        |5 3
        |6 1
        |""".stripMargin
    OrderProcessor.process(asInput(input)) must === (Right(3L))
  }

  test("long cooking job comes first (cook doesn't know about future orders)") {
    val input =
      """3
        |0 10
        |1 1
        |2 1
        |""".stripMargin
    OrderProcessor.process(asInput(input)) must === (Right(10L))
  }

  test("all customers arrive at the same time") {
    val input =
      """4
        |0 3
        |0 2
        |0 1
        |0 4
        |""".stripMargin
    OrderProcessor.process(asInput(input)) must === (Right(5L))
  }
}
