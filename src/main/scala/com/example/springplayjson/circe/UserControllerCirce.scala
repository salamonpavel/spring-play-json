package com.example.springplayjson.circe

import org.springframework.http.{HttpStatus, MediaType}
import org.springframework.web.bind.annotation.{GetMapping, ResponseStatus, RestController}

@RestController
class UserControllerCirce {

  @GetMapping(value = Array("/circe"),  produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseStatus(HttpStatus.OK)
  def getUserCirce: User = {
    User(1, "John Doe", Status.Active, FreeUser(100))
  }

}
