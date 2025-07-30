package com.profect.delivery.common.error;

import com.profect.delivery.common.error.code.DefaultErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private final DefaultErrorCode errorCode;
}
