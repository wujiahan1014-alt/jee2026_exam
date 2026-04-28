package cn.jee.exception;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IOException.class)
  public Map<String, String> handle(IOException e) {

    Map<String, String> map = new HashMap<>();
    map.put("error", e.getMessage());

    return map;
  }
}