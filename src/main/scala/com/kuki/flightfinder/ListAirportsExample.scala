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
      //destinations <- TimeTableService.loadDestinations(City.Wroclaw.iata)
      //_ <- putStrLn(destinations.map(destIATA => destIATA + ":" + airports.get(destIATA).getOrElse(destIATA)).mkString("\n"))
      fares <- FaresService.loadFares(City.Wroclaw.iata, City.Chania.iata)
      _ <- putStrLn(fares(2).price.get.value + "")
    } yield ()

    program
      .provideCustomLayer(AsyncHttpClientZioBackend.layer())
      .exitCode
  }
}
