package com.spring.backend.common;

import com.spring.backend.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class AppResponse<T> {
  private boolean success;
  private int status;
  private String code;
  private String message;
  private String traceId;
  private T data;

  public static <T> AppResponse<T> success(T data) {
    return AppResponse.<T>builder()
      .success(true)
      .status(200)
      .data(data)
      .build();
  }

  public static AppResponse<Void> error(ErrorCode errorCode, String traceId) {
    return AppResponse.<Void>builder()
      .success(false)
      .status(errorCode.getHttpStatus().value())
      .code(errorCode.getCode())
      .message(errorCode.getMessage())
      .traceId(traceId)
      .build();
  }

  public static AppResponse<Void> error(HttpStatus status, String code, String message, String traceId) {
    return AppResponse.<Void>builder()
      .success(false)
      .status(status.value())
      .code(code)
      .message(message)
      .traceId(traceId)
      .build();
  }
}
