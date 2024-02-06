package com.example.springplayjson.play

import play.api.libs.json._

sealed trait UserType
case class FreeUser(limit: Int) extends UserType
case class PremiumUser(subscriptionEndDate: java.time.LocalDate) extends UserType

object UserType {
  implicit val userTypeReads: Reads[UserType] = new Reads[UserType] {
    def reads(json: JsValue): JsResult[UserType] = {
      if ((json \ "limit").isDefined)
        Json.fromJson[FreeUser](json)
      else
        Json.fromJson[PremiumUser](json)
    }
  }

  implicit val userTypeWrites: Writes[UserType] = new Writes[UserType] {
    def writes(userType: UserType): JsValue = userType match {
      case freeUser: FreeUser => Json.toJson(freeUser)
      case premiumUser: PremiumUser => Json.toJson(premiumUser)
    }
  }

  implicit val userTypeFormat: Format[UserType] = Format(userTypeReads, userTypeWrites)
}

object FreeUser {
  implicit val format: Format[FreeUser] = Json.format
}

object PremiumUser {
  implicit val format: Format[PremiumUser] = Json.format
}

object Status extends Enumeration {
  val Active, Inactive = Value

  implicit val statusFormat: Format[Status.Value] = new Format[Status.Value] {
    def reads(json: JsValue): JsResult[Status.Value] = json.validate[String].map(Status.withName)
    def writes(status: Status.Value): JsValue = JsString(status.toString)
  }
}

case class User(id: Int, name: String, status: Status.Value, userType: UserType)
object User {
  implicit val format: Format[User] = Json.format
}
