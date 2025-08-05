
// package com.profect.delivery.global.advice;

// import com.profect.delivery.common.repository.ErrorLogRepository;
// import com.profect.delivery.global.dto.ApiResponse;
// import com.profect.delivery.global.dto.ErrorResponse;
// import com.profect.delivery.global.entity.ErrorEntity;
// import com.profect.delivery.global.exception.*;
// import jakarta.servlet.http.HttpServletRequest;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.MethodArgumentNotValidException;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice
// @RequiredArgsConstructor
// @Slf4j
// public class GlobalExceptionalHandler {
//     private final ErrorLogRepository errorLogRepository;

//     private void saveErrorLog(Exception ex, HttpServletRequest request, int errorCode){
//         ErrorEntity savedEntity = errorLogRepository.save(ErrorEntity.builder()
//                 .userId("user001") // Jwt
//                 .requestUrl(request.getRequestURI())
//                 .httpMethod(request.getMethod())
//                 .errorCode(String.valueOf(errorCode))
//                 .errorMessage(ex.getMessage())
//                 .clientIp(request.getRemoteAddr())
//                 .userAgent(request.getHeader("User-Agent"))
//                 .build()
//         );

//         log.error("Error errorEntity={}", savedEntity.toString());

//     }

//     @ExceptionHandler(ConflictException.class)
//     public ResponseEntity<ApiResponse<?>> handleConflictException(ConflictException e,
//                                                                   HttpServletRequest request) {
//         ErrorResponse err = ErrorResponse.of(40901, e.getMessage(), request.getRequestURI());

// //        saveErrorLog(e, request, 40901);

//         return ResponseEntity
//                 .status(HttpStatus.CONFLICT)
//                 .body(ApiResponse.failure(err));
//     }

//     @ExceptionHandler(NotFoundException.class)
//     public ResponseEntity<ApiResponse<?>> handleNotFoundException(NotFoundException e,
//                                                                   HttpServletRequest request) {
//         ErrorResponse err = ErrorResponse.of(40401, e.getMessage(), request.getRequestURI());
// //        saveErrorLog(e, request, 40401);

//         return ResponseEntity
//                 .status(HttpStatus.NOT_FOUND)
//                 .body(ApiResponse.failure(err));
//     }

//     @ExceptionHandler(UserNotFoundException.class)
//     public ResponseEntity<ApiResponse<?>> handleUserNotFoundException(UserNotFoundException e,
//                                                                       HttpServletRequest request) {
//         ErrorResponse err = ErrorResponse.of(40400, e.getMessage(), request.getRequestURI());

// //        saveErrorLog(e, request, 40400);

//         return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                 .body(ApiResponse.failure(err));
//     }

//     @ExceptionHandler(UserAddressNotFoundException.class)
//     public ResponseEntity<ApiResponse<?>> handleUserAddressNotFoundException(UserAddressNotFoundException e,
//                                                                              HttpServletRequest request) {
//         ErrorResponse err = ErrorResponse.of(40402, e.getMessage(), request.getRequestURI());

// //        saveErrorLog(e, request, 40402);

//         return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                 .body(ApiResponse.failure(err));
//     }

//     @ExceptionHandler(InvalidInputException.class)
//     public ResponseEntity<ApiResponse<?>> handleInvalidInputException(InvalidInputException e,
//                                                                       HttpServletRequest request) {
//         ErrorResponse err = ErrorResponse.of(40001, e.getMessage(), request.getRequestURI());
// //        saveErrorLog(e, request, 40001);

//         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                 .body(ApiResponse.failure(err));
//     }

//     @ExceptionHandler(MethodArgumentNotValidException.class)
//     public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException e,
//                                                                      HttpServletRequest request) {
//         String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
//         ErrorResponse err = ErrorResponse.of(40002, "유효성 검사 실패: " + errorMessage, request.getRequestURI());
// //        saveErrorLog(e, request, 40002);

//         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                 .body(ApiResponse.failure(err));
//     }

//     @ExceptionHandler(Exception.class)
//     public ResponseEntity<ApiResponse<?>> handleAllUncaughtException(Exception e,
//                                                                      HttpServletRequest request) {
//         ErrorResponse err = ErrorResponse.of(50000, "서버 내부 오류가 발생했습니다.", request.getRequestURI());
// //        saveErrorLog(e, request, 50000);

//         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                 .body(ApiResponse.failure(err));
//     }
// }

//package com.profect.delivery.global.advice;
//
//import com.profect.delivery.common.repository.ErrorLogRepository;
//import com.profect.delivery.global.dto.ApiResponse;
//import com.profect.delivery.global.dto.ErrorResponse;
//import com.profect.delivery.global.entity.ErrorEntity;
//import com.profect.delivery.global.exception.*;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//@RequiredArgsConstructor
//@Slf4j
//public class GlobalExceptionalHandler {
//    private final ErrorLogRepository errorLogRepository;
//
//    private void saveErrorLog(Exception ex, HttpServletRequest request, int errorCode){
//        ErrorEntity savedEntity = errorLogRepository.save(ErrorEntity.builder()
//                .userId("user001") // Jwt
//                .requestUrl(request.getRequestURI())
//                .httpMethod(request.getMethod())
//                .errorCode(String.valueOf(errorCode))
//                .errorMessage(ex.getMessage())
//                .clientIp(request.getRemoteAddr())
//                .userAgent(request.getHeader("User-Agent"))
//                .build()
//        );
//
//        log.error("Error errorEntity={}", savedEntity.toString());
//
//    }
//
//    @ExceptionHandler(ConflictException.class)
//    public ResponseEntity<ApiResponse<?>> handleConflictException(ConflictException e,
//                                                                  HttpServletRequest request) {
//        ErrorResponse err = ErrorResponse.of(40901, e.getMessage(), request.getRequestURI());
//
//        saveErrorLog(e, request, 40901);
//
//        return ResponseEntity
//                .status(HttpStatus.CONFLICT)
//                .body(ApiResponse.failure(err));
//    }
//
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ApiResponse<?>> handleNotFoundException(NotFoundException e,
//                                                                  HttpServletRequest request) {
//        ErrorResponse err = ErrorResponse.of(40401, e.getMessage(), request.getRequestURI());
//        saveErrorLog(e, request, 40401);
//
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(ApiResponse.failure(err));
//    }
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<ApiResponse<?>> handleUserNotFoundException(UserNotFoundException e,
//                                                                      HttpServletRequest request) {
//        ErrorResponse err = ErrorResponse.of(40400, e.getMessage(), request.getRequestURI());
//
//        saveErrorLog(e, request, 40400);
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(ApiResponse.failure(err));
//    }
//
//    @ExceptionHandler(UserAddressNotFoundException.class)
//    public ResponseEntity<ApiResponse<?>> handleUserAddressNotFoundException(UserAddressNotFoundException e,
//                                                                             HttpServletRequest request) {
//        ErrorResponse err = ErrorResponse.of(40402, e.getMessage(), request.getRequestURI());
//
//        saveErrorLog(e, request, 40402);
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(ApiResponse.failure(err));
//    }
//
//    @ExceptionHandler(InvalidInputException.class)
//    public ResponseEntity<ApiResponse<?>> handleInvalidInputException(InvalidInputException e,
//                                                                      HttpServletRequest request) {
//        ErrorResponse err = ErrorResponse.of(40001, e.getMessage(), request.getRequestURI());
//        saveErrorLog(e, request, 40001);
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(ApiResponse.failure(err));
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException e,
//                                                                     HttpServletRequest request) {
//        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
//        ErrorResponse err = ErrorResponse.of(40002, "유효성 검사 실패: " + errorMessage, request.getRequestURI());
//        saveErrorLog(e, request, 40002);
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(ApiResponse.failure(err));
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<?>> handleAllUncaughtException(Exception e,
//                                                                     HttpServletRequest request) {
//
//        // Swagger 요청이면 예외를 다시 던져 Spring 기본 처리로 넘김
//        String path = request.getRequestURI();
//        if (path.contains("/v3/api-docs") || path.contains("/swagger-ui")) {
//            throw new RuntimeException(e);
//        }
//
//        ErrorResponse err = ErrorResponse.of(50000, "서버 내부 오류가 발생했습니다.", path);
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(ApiResponse.failure(err));
//    }
//}

