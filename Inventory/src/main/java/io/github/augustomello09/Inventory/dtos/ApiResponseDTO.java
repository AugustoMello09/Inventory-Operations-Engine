package io.github.augustomello09.Inventory.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponseDTO<T> {

    private final boolean success;
    private final String message;
    private final T data;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
