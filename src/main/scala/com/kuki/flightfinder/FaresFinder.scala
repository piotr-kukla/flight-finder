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
    } yield ()
  }

  def oneWayReport(from: City, to: City): ZIO[SttpClient with Console, Throwable, Unit] = {
    for {
      fares <- FaresService.loadFares(from.iata, to.iata)
      _ <- putStrLn(s"Direction: $from -> $to")
      _ <- ZIO.foreach(fares)(fare => putStrLn(s"Departure: ${fare.departureDate.get}, Price: ${fare.price.get}"))
      _ <- putStrLn("**************************************************************************************************")
    } yield ()
  }

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    val program = for {

//      fares <- FaresService.loadFares(City.Wroclaw.iata, City.Chania.iata)
//      _ <- putStrLn(fares(0).price.get.value + "")
      _ <- destinationReport(City.Wroclaw, City.Chania)
    } yield ()

    program
      .provideCustomLayer(AsyncHttpClientZioBackend.layer())
      .exitCode
  }
}
