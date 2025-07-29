package de.riskident

import de.riskident.OrderProcessor.Customer

trait Scheduler {
  def computeMinAverageWaitingTime(customers: Seq[Customer]): Long
}
