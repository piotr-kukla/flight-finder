package com.kuki.flightfinder

import com.kuki.flightfinder.model.City
import com.kuki.flightfinder.model.City._
import com.kuki.flightfinder.service.{FaresService}
import sttp.client3.asynchttpclient.zio.{AsyncHttpClientZioBackend, SttpClient}
import zio._
import zio.console._

object FaresFinder extends App {

  def destinationReport(from: City, to: City): ZIO[SttpClient with Console, Throwable, Unit] = {
    for {
      _ <- oneWayReport(from, to)
      _ <- oneWayReport(to, from)
      _ <- putStrLn("**************************************************************************************************")
    } yield ()
  }

  def oneWayReport(from: City, to: City): ZIO[SttpClient with Console, Throwable, Unit] = {
    for {
      fares <- FaresService.loadFares(from.iata, to.iata)
      _ <- putStrLn(s"Direction: $from -> $to")
      _ <- ZIO.foreach(fares)(fare => putStrLn(s"Departure: ${fare.departureDate.get}, ${fare.price.get}"))
    } yield ()
  }

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {

    val destinations = City.values.filter(_ != City.Wroclaw).toList
    val destinations2 = List(City.Chania, City.Zadar, City.Alicante)

    val program = for {

      //_ <- destinationReport(City.Wroclaw, City.Chania)
      _ <- ZIO.foreach(destinations)(destinationReport(City.Wroclaw, _))
    } yield ()

    program
      .provideCustomLayer(AsyncHttpClientZioBackend.layer())
      .exitCode
  }
}
