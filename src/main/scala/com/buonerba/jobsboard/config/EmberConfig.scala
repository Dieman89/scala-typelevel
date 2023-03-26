package com.buonerba.jobsboard.config

import pureconfig.ConfigReader
import pureconfig.generic.derivation.default.*
import com.comcast.ip4s.{Host, Port}
import pureconfig.error.CannotConvert
import com.buonerba.jobsboard.config.EmberConfig

final case class EmberConfig(host: Host, port: Port) derives ConfigReader

object EmberConfig {
  given hostReader: ConfigReader[Host] = ConfigReader[String].emap { hostString =>
    Host
      .fromString(hostString)
      .toRight(
        CannotConvert(hostString, Host.getClass.toString, s"Invalid host string: $hostString"))
  }
}

given portReader: ConfigReader[Port] = ConfigReader[Int].emap { portInt =>
  Port
    .fromInt(portInt)
    .toRight(
      CannotConvert(portInt.toString(), Port.getClass.toString, s"Invalid port number: $portInt"))
}