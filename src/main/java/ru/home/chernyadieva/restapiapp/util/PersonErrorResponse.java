package ru.home.chernyadieva.restapiapp.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Класс для отправки данных в json при возникновении ошибки
 */
@AllArgsConstructor
@Getter
@Setter
public class PersonErrorResponse {
    private String msg;
    private LocalDateTime timestamp;
}
