package com.kuki.flightfinder

import zio._
import zio.blocking._
import zio.console._

import scala.io.{Codec, Source}

object ListAirportsExample extends App {

  def getResource(path: String): ZIO[Blocking, Throwable, Iterator[String]] = effectBlocking {
    Source.fromResource(path)(Codec.UTF8).getLines
  }




  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    (for {
      airports <- AirportsService.loadAirports()
      _  <- ZIO.foreach(airports.toList){ case(ita, cityName) => putStrLn(s"$ita: $cityName")}
    } yield 0).exitCode
}
