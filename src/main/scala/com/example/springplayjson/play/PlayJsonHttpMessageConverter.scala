package com.example.springplayjson.play

import org.springframework.http.converter.{HttpMessageConverter, HttpMessageNotReadableException}
import org.springframework.http.{HttpInputMessage, HttpOutputMessage, MediaType}
import play.api.libs.json.{JsError, JsSuccess, Json, Reads, Writes}

import java.nio.charset.StandardCharsets
import java.util
import scala.reflect.ClassTag

class PlayJsonHttpMessageConverter[T: Reads : Writes: ClassTag] extends HttpMessageConverter[T] {
  private val clazz = implicitly[ClassTag[T]].runtimeClass

  override def canRead(clazz: Class[_], mediaType: MediaType): Boolean =
    this.clazz.isAssignableFrom(clazz) && MediaType.APPLICATION_JSON.includes(mediaType)

  override def canWrite(clazz: Class[_], mediaType: MediaType): Boolean =
    this.clazz.isAssignableFrom(clazz) && MediaType.APPLICATION_JSON.includes(mediaType)

  override def getSupportedMediaTypes: util.List[MediaType] =
    java.util.Collections.singletonList(MediaType.APPLICATION_JSON)

  override def read(clazz: Class[_ <: T], inputMessage: HttpInputMessage): T = {
    val body = scala.io.Source.fromInputStream(inputMessage.getBody).mkString
    Json.parse(body).validate[T] match {
      case JsSuccess(value, _) => value
      case JsError(errors) => throw new HttpMessageNotReadableException(errors.mkString, inputMessage)
    }
  }

  override def write(t: T, contentType: MediaType, outputMessage: HttpOutputMessage): Unit = {
    outputMessage.getBody.write(Json.stringify(Json.toJson(t)).getBytes(StandardCharsets.UTF_8))
  }
}
