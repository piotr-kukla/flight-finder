package com.kuki.flightfinder.model

import com.kuki.flightfinder.IATA

object City extends Enumeration {
  protected case class Val(iata: IATA) extends super.Val

  val Wroclaw = Val("WRO")
}
