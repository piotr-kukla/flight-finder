package com.kuki.flightfinder.model

import com.kuki.flightfinder.IATA

object City extends Enumeration {
  type City = Value
  protected case class Val(iata: IATA) extends super.Val
  implicit def valueToCityVal(x: Value): Val = x.asInstanceOf[Val]

  val Wroclaw = Val("WRO")

  val Bergamo = Val("BGY")
  val PalmaDeMallorca = Val("PMI")
  val Corfu = Val("CFU")
  val Tenerife = Val("TFS")
  val Bologna = Val("BLQ")
  val Málaga = Val("AGP")
  val Chania = Val("CHQ")
  val Girona = Val("GRO")
  val Rome = Val("CIA")
  val Athens = Val("ATH")
  val Palermo = Val("PMO")
  val Alicante = Val("ALC")
  val Zadar = Val("ZAD")
  val Luqa = Val("MLA")
}
