package com.buonerba.jobsboard.config

import pureconfig.ConfigSource
import cats.*
import cats.implicits.*
import pureconfig.ConfigReader
import pureconfig.error.ConfigReaderException
import scala.reflect.ClassTag

object syntax {
  extension (source: ConfigSource)
    def loadF[F[_], A](using reader: ConfigReader[A], F: MonadThrow[F], tag: ClassTag[A]): F[A] =
      F.pure(source.load[A])
        .flatMap(value =>
          value match {
            case Left(errors) => F.raiseError[A](ConfigReaderException(errors))
            case Right(value) => F.pure(value)
          })
}
