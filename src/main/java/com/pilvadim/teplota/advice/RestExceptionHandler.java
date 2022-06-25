package com.pilvadim.teplota.advice;

import com.pilvadim.teplota.service.exception.WeatherBadRequestException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WeatherBadRequestException.class)
    protected ResponseEntity<Object> handleBadRequest( WeatherBadRequestException ex ) {
        ApiError apiError = new ApiError( HttpStatus.BAD_REQUEST );
        apiError.setMessage( ex.getMessage() );
        return ResponseEntity.badRequest().body( apiError );
    }
}