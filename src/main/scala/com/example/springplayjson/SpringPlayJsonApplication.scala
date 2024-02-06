package com.example.springplayjson;

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
class SpringPlayJsonApplication extends SpringBootServletInitializer {
	override def configure(application: SpringApplicationBuilder): SpringApplicationBuilder =
		application.sources(classOf[SpringPlayJsonApplication])
}

object SpringPlayJsonApplication extends App {
	SpringApplication.run(classOf[SpringPlayJsonApplication], args :_ *)
}
