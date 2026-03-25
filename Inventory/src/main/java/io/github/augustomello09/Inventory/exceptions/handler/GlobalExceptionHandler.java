package io.github.augustomello09.Inventory.exceptions.handler;

import io.github.augustomello09.Inventory.dtos.ErroResponseDTO;
import io.github.augustomello09.Inventory.exceptions.BusinessException;
import io.github.augustomello09.Inventory.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErroResponseDTO> business(BusinessException exception, HttpServletRequest httpServletRequest) {
        ErroResponseDTO erroResponseDTO = ErroResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .code(HttpStatus.UNPROCESSABLE_ENTITY.name())
                .message(exception.getMessage())
                .details("Violações de regras de negócio")
                .path(httpServletRequest.getRequestURI())
                .build();

        return ResponseEntity.status(erroResponseDTO.getStatus()).body(erroResponseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErroResponseDTO> resourceNotFound(ResourceNotFoundException exception, HttpServletRequest httpServletRequest) {

        ErroResponseDTO erroResponseDTO = ErroResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .code(HttpStatus.NOT_FOUND.name())
                .message(exception.getMessage())
                .details("Não foi possível localizar o ID fornecido. Por favor, verifique o ID e tente novamente.")
                .path(httpServletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(erroResponseDTO.getStatus()).body(erroResponseDTO);
    }
}
