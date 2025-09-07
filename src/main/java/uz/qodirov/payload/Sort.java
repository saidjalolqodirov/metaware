package uz.qodirov.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import uz.qodirov.constant.Direction;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Sort {
    @Schema(description = "Имя полей", example = "id", required = true)
    private String key = "modifiedDate";

    @Schema(description = "Сортировать по направлению", example = "DESC", required = true)
    private Direction direction;
}
