package com.example.springplayjson.circe

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

sealed trait UserType
object UserType {
  implicit val userTypeEncoder: Encoder[UserType] = deriveEncoder
  implicit val userTypeDecoder: Decoder[UserType] = deriveDecoder
}

case class FreeUser(limit: Int) extends UserType
case class PremiumUser(subscriptionEndDate: java.time.LocalDate) extends UserType

object Status extends Enumeration {
  val Active, Inactive = Value

  implicit val statusEncoder: Encoder[Status.Value] = Encoder.encodeString.contramap[Status.Value](_.toString)
  implicit val statusDecoder: Decoder[Status.Value] = Decoder.decodeString.map(Status.withName)
}

case class User(id: Int, name: String, status: Status.Value, userType: UserType)
object User {
  implicit val userEncoder: Encoder[User] = deriveEncoder
  implicit val userDecoder: Decoder[User] = deriveDecoder
}