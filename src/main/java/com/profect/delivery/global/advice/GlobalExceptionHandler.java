package com.profect.delivery.global.advice; // 실제 패키지 경로에 맞게 수정
import com.profect.delivery.global.DTO.ApiResponse;
import com.profect.delivery.global.DTO.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(404); // 커스텀 에러 코드
        error.setMessage(ex.getMessage());
        error.setTimestamp(LocalDateTime.now().toString());
        error.setPath(request.getRequestURI());
        return new ResponseEntity<>(ApiResponse.failure(error), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAddressNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserAddressNotFoundException(UserAddressNotFoundException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(40402);
        error.setMessage(ex.getMessage());
        error.setTimestamp(LocalDateTime.now().toString());
        error.setPath(request.getRequestURI());
        return new ResponseEntity<>(ApiResponse.failure(error), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidInputException(InvalidInputException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(40001);
        error.setMessage(ex.getMessage());
        error.setTimestamp(LocalDateTime.now().toString());
        error.setPath(request.getRequestURI());
        return new ResponseEntity<>(ApiResponse.failure(error), HttpStatus.BAD_REQUEST);
    }

    // 요청 본문의 유효성 검사 실패 시 처리 (예: @Valid 사용 시)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse error = new ErrorResponse();
        error.setCode(40002);
        error.setMessage("유효성 검사 실패: " + errorMessage);
        error.setTimestamp(LocalDateTime.now().toString());
        error.setPath(request.getRequestURI());
        return new ResponseEntity<>(ApiResponse.failure(error), HttpStatus.BAD_REQUEST);
    }

    // 그 외 예상치 못한 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAllUncaughtException(Exception ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(50000); // 일반 서버 오류 코드
        error.setMessage("서버 내부 오류가 발생했습니다.");
        error.setTimestamp(LocalDateTime.now().toString());
        error.setPath(request.getRequestURI());
        // 실제 운영 환경에서는 예외 스택 트레이스를 로그로 남기고 클라이언트에게는 상세 정보를 노출하지 않는 것이 좋습니다.
        return new ResponseEntity<>(ApiResponse.failure(error), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

// 가상의 커스텀 예외 클래스들




