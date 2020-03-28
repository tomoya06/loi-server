package com.tomoya06.loiserver;

import com.tomoya06.loiserver.common.model.FailureResponse;
import java.security.InvalidParameterException;
import javax.management.InstanceAlreadyExistsException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(InstanceAlreadyExistsException.class)
  @ResponseBody
  ResponseEntity<?> instanceAlreadyExistsExceptionHandler(HttpServletRequest request, Exception e) {
    return new ResponseEntity<>(new FailureResponse(HttpStatus.BAD_REQUEST, "Existed"), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NullPointerException.class)
  @ResponseBody
  ResponseEntity<?> nullPointerExceptionHandler(HttpServletRequest request, Exception e) {
    return new ResponseEntity<>(new FailureResponse(HttpStatus.NOT_FOUND, "Not Found"), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({InvalidParameterException.class, MethodArgumentNotValidException.class})
  @ResponseBody
  ResponseEntity<?> invalidParameterExceptionHandler(HttpServletRequest request, Exception e) {
    return new ResponseEntity<>(new FailureResponse(HttpStatus.BAD_REQUEST, "invalid param"), HttpStatus.BAD_REQUEST);
  }
}
