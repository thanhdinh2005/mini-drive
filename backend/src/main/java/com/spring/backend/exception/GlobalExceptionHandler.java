package com.spring.backend.exception;

import com.spring.backend.common.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @ExceptionHandler(AppException.class)
  public ResponseEntity<AppResponse<Void>> handleAppException(AppException ex) {
    ErrorCode code = ex.getErrorCode();
    String traceId = generateTraceId();

    log.warn("[{}] {} | code={} | context={}",
      traceId, code.getMessage(), code.getCode(), ex.getContext());

    return ResponseEntity.status(code.getHttpStatus())
      .body(AppResponse.error(code, traceId));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<AppResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
    String traceId = generateTraceId();
    String detail = ex.getBindingResult().getFieldErrors().stream()
      .map(err -> err.getField() + ": " + err.getDefaultMessage())
      .collect(Collectors.joining("; "));

    log.warn("[{}] Validation failed: {}", traceId, detail);

    return ResponseEntity.badRequest()
      .body(AppResponse.error(HttpStatus.BAD_REQUEST, ErrorCode.VALIDATION_ERROR.getCode(), detail, traceId));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<AppResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
    String traceId = generateTraceId();
    log.warn("[{}] Access denied: {}", traceId, ex.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
      .body(AppResponse.error(ErrorCode.FOLDER_ACCESS_DENIED, traceId));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<AppResponse<Void>> handleUnexpected(Exception ex) {
    String traceId = generateTraceId();

    log.error("[{}] Unexpected error: {}", traceId, ex.getMessage(), ex);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(AppResponse.error(ErrorCode.INTERNAL_SERVER_ERROR, traceId));
  }

  private String generateTraceId() {
    return UUID.randomUUID().toString().substring(0, 8);
  }
}
