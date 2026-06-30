package com.expense_tracker.exceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ExceptionMessage {

    private String message;
    private String description;
    private String path;
    private LocalDateTime timestamp;

}
