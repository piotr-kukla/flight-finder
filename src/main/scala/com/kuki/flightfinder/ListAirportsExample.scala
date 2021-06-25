package com.kuki.flightfinder

import zio._
import zio.console._


object ListAirportsExample extends App {

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    (for {
      airports <- AirportsService.loadAirports()
      _  <- ZIO.foreach(airports.toList){ case(ita, cityName) => putStrLn(s"$ita: $cityName")}
    } yield 0).exitCode
}
