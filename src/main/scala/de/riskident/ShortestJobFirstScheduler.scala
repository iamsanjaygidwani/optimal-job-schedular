package de.riskident

import de.riskident.OrderProcessor.Customer
import scala.collection.mutable

object ShortestJobFirstScheduler extends Scheduler {
  override def computeMinAverageWaitingTime(customers: Seq[Customer]): Long = {
    val sortedCustomers = customers.sortBy(_.arrival)
    val pq = mutable.PriorityQueue.empty[Customer](Ordering.by(-_.duration))

    var currentTime = 0L
    var totalWaitTime = 0L
    var index = 0
    val totalCustomers = customers.length

    while (index < totalCustomers || pq.nonEmpty) {
      while (index < totalCustomers && sortedCustomers(index).arrival <= currentTime) {
        pq.enqueue(sortedCustomers(index))
        index += 1
      }

      if (pq.nonEmpty) {
        val priorityCustomer = pq.dequeue()
        currentTime += priorityCustomer.duration
        totalWaitTime += (currentTime - priorityCustomer.arrival)
      } else if (index < totalCustomers) {
        currentTime = sortedCustomers(index).arrival
      }
    }

    totalWaitTime / totalCustomers
  }
}
