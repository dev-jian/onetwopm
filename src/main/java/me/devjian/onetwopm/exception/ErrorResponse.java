package me.devjian.onetwopm.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {

    private String message;

    private String details;

    private LocalDateTime timestamp;
}
