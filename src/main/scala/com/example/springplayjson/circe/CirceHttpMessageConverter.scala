package com.example.springplayjson.circe

import io.circe.parser._
import io.circe.syntax._
import io.circe.{Decoder, Encoder}
import org.springframework.http.converter.{HttpMessageConverter, HttpMessageNotReadableException}
import org.springframework.http.{HttpInputMessage, HttpOutputMessage, MediaType}

import scala.reflect.ClassTag

class CirceHttpMessageConverter[T: Encoder: Decoder: ClassTag] extends HttpMessageConverter[T] {
  private val clazz = implicitly[ClassTag[T]].runtimeClass

  override def canRead(clazz: Class[_], mediaType: MediaType): Boolean =
    this.clazz.isAssignableFrom(clazz) && MediaType.APPLICATION_JSON.includes(mediaType)

  override def canWrite(clazz: Class[_], mediaType: MediaType): Boolean =
    this.clazz.isAssignableFrom(clazz) && MediaType.APPLICATION_JSON.includes(mediaType)

  override def getSupportedMediaTypes: java.util.List[MediaType] =
    java.util.Collections.singletonList(MediaType.APPLICATION_JSON)

  override def read(clazz: Class[_ <: T], inputMessage: HttpInputMessage): T = {
    val json = scala.io.Source.fromInputStream(inputMessage.getBody).mkString
    decode[T](json) match {
      case Right(value) => value
      case Left(error) => throw new HttpMessageNotReadableException(error.getMessage, inputMessage)
    }
  }

  override def write(t: T, contentType: MediaType, outputMessage: HttpOutputMessage): Unit = {
    val json = t.asJson.noSpaces
    outputMessage.getBody.write(json.getBytes("UTF-8"))
  }
}
