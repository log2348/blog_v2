package com.example.jpa_basic_crud.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

// Controller + Advice

@ControllerAdvice
@RestController
public class GlobalControllerAdvice {
	
	@ExceptionHandler(value = Exception.class)
	public String exception(Exception e) {
		String errorMessage = e.getMessage();
		System.out.println("모든 오류 메서드 실행 발생 !");
		return errorMessage;
	}

}
