package uz.qodirov.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ErrorMessage {
    private String uz;
    private String ru;
    private String en;
}