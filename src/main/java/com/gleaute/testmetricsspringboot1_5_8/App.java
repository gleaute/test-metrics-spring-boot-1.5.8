package com.gleaute.testmetricsspringboot1_5_8;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class App {

	@RequestMapping("/user/{userId}")
	public ResponseEntity<String> user(@PathVariable Long userId, HttpServletRequest request) {
		return new ResponseEntity<>(request.getMethod() + " user " + userId, HttpStatus.PARTIAL_CONTENT);
	}

	@RequestMapping("/users")
	public ResponseEntity<String> users(HttpServletRequest request) {
		return new ResponseEntity<>(request.getMethod() + " users", HttpStatus.OK);
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}