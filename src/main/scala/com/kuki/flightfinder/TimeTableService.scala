package com.kuki.flightfinder

import com.kuki.flightfinder.model.City._
import zio._
import sttp.client3._
import sttp.client3.circe._
import sttp.client3.asynchttpclient.zio._
import io.circe.generic.auto._

object TimeTableService {

  val destinations = Set(
      Chania,
      Zadar)
    .map(_.iata)

  case class FlightPeriod(firstFlightDate: String, lastFlightDate: String, months: Int, monthsFromToday: Int)

  def loadDestinations(from: IATA): ZIO[SttpClient, Throwable, List[IATA]] = {
    val request = basicRequest
      .get(uri"https://services-api.ryanair.com/timtbl/3/schedules/$from/periods")
      .response(asJson[Map[IATA, FlightPeriod]].getRight)
    for {
      response <- send(request)
    } yield response.body.keys.toList.filter(destinations.contains(_))
  }

}
