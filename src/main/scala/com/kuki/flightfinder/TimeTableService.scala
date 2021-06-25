package com.kuki.flightfinder

import zio._
import sttp.client3._
import sttp.client3.circe._
import sttp.client3.asynchttpclient.zio._
import io.circe.generic.auto._

object TimeTableService {

  case class HttpBinResponse(origin: String, headers: Map[String, String])

  def loadDestinations(from: IATA): ZIO[SttpClient, Throwable, String] = {
    val request = basicRequest
      .get(uri"https://services-api.ryanair.com/timtbl/3/schedules/$from/periods")
      //.response(asJson[HttpBinResponse])
    for {
      response <- send(request)
    } yield response.body.toString
  }

}
