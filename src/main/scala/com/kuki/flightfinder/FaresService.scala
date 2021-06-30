package com.kuki.flightfinder

import sttp.client3._
import sttp.client3.circe._
import sttp.client3.asynchttpclient.zio._
import io.circe.generic.auto._
import zio.ZIO

object FaresService {

  case class Price(value: Float, currencyCode: String)
  case class Fare(day: String, arrivalDate: Option[String], departureDate: Option[String], price: Option[Price])
  case class Outbound(fares: List[Fare])
  case class HttpResponse(outbound: Outbound)

  def loadFares(from: IATA, to: IATA): ZIO[SttpClient, Throwable, List[Fare]] = {
    val request = basicRequest
      .get(uri"https://www.ryanair.com/api/farfnd/3/oneWayFares/$from/$to/cheapestPerDay?outboundDateFrom=2021-07-21&outboundDateTo=2021-08-10")
      .response(asJson[HttpResponse].getRight)
    for {
      response <- send(request)
    } yield response.body.outbound.fares
  }
}
