package io.github.ramon.ReadFlow.infrastructure.exceptions.handler;

import io.github.ramon.ReadFlow.infrastructure.exceptions.exception.BadRequestException;
import io.github.ramon.ReadFlow.infrastructure.exceptions.exception.ConflictException;
import io.github.ramon.ReadFlow.infrastructure.exceptions.exception.ResourceNotFoundException;
import io.github.ramon.ReadFlow.infrastructure.exceptions.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice()
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(ResourceNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                404,
                "Not Found",
                exception.getMessage(),
                LocalDateTime.now());

        return ResponseEntity.status(errorResponse.status()).body(errorResponse);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> conflict(ConflictException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                409,
                "Conflict",
                exception.getMessage(),
                LocalDateTime.now());

        return ResponseEntity.status(errorResponse.status()).body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequest(BadRequestException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                400,
                "Bad Request",
                exception.getMessage(),
                LocalDateTime.now());

        return ResponseEntity.status(errorResponse.status()).body(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> badCredentials() {
        ErrorResponse errorResponse = new ErrorResponse(
                401,
                "Unauthorized",
                "E-mail ou Senha inválidos",
                LocalDateTime.now());

        return ResponseEntity.status(errorResponse.status()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> argumentNotValid() {
        ErrorResponse errorResponse = new ErrorResponse(
                400,
                "Bad Request",
                "Dados inválidos",
                LocalDateTime.now());

        return ResponseEntity.status(errorResponse.status()).body(errorResponse);
    }
}
