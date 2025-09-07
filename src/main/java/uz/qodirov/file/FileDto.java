package uz.qodirov.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileDto {
    private String id;
    private String guid;
    private String name;
    private String extension;
    private Long size;
    private String type;
    private String url;
    private Long createdDate;
    private Long modifiedDate;
}
