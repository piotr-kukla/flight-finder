package com.kuki.flightfinder.service

import com.kuki.flightfinder.{CityName, IATA}
import zio.ZIO
import zio.blocking.{Blocking, effectBlocking}

import scala.io.{Codec, Source}

object AirportsService {

  def loadAirports(): ZIO[Blocking, Throwable, Map[IATA, CityName]] = effectBlocking {
    Source.fromResource("european_iatas_df.csv")(Codec.UTF8).getLines.toList
      .map(parseAirportLine(_))
      .toMap
  }

  private def parseAirportLine(line: String) = {
    val tokens = line.split(',')
    (tokens(2),tokens(0))
  }
}
