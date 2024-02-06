package com.example.springplayjson

import com.example.springplayjson.circe.{CirceHttpMessageConverter, User => CUser}
import com.example.springplayjson.play.{PlayJsonHttpMessageConverter, User => PUser}
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig extends WebMvcConfigurer {

  override def configureMessageConverters(converters: java.util.List[HttpMessageConverter[_]]): Unit = {
    // remove default jackson converter
    converters.removeIf(converter => converter.isInstanceOf[MappingJackson2HttpMessageConverter])
    // add custom converters
    converters.add(new CirceHttpMessageConverter[CUser])
    converters.add(new PlayJsonHttpMessageConverter[PUser])
  }

}
