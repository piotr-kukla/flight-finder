package com.kuki.flightfinder

import com.kuki.flightfinder.model.City
import sttp.client3.asynchttpclient.zio.AsyncHttpClientZioBackend
import zio._
import zio.console._


object ListAirportsExample extends App {

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    val program = for {
      airports <- AirportsService.loadAirports()
      //_  <- ZIO.foreach(airports.toList){ case(iata, cityName) => putStrLn(s"$iata: $cityName")}
      response <- TimeTableService.loadDestinations(City.Wroclaw.iata)
      _ <- putStrLn(response)
    } yield ()

    program
      .provideCustomLayer(AsyncHttpClientZioBackend.layer())
      .exitCode
  }
}
