package com.example.springplayjson.play

import org.springframework.http.{HttpStatus, MediaType}
import org.springframework.web.bind.annotation.{GetMapping, ResponseStatus, RestController}

@RestController
class UserControllerPlay {

  @GetMapping(value = Array("/play"),  produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseStatus(HttpStatus.OK)
  def getUserPlay: User = {
    User(1, "John Doe", Status.Active, FreeUser(100))
  }

}
