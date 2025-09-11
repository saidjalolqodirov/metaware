package uz.qodirov.file;

import org.springframework.stereotype.Component;
import uz.qodirov.generic.Converter;

@Component
public class FileConverter extends Converter<FileDto, FileEntity> {
    public FileConverter() {
        super(fileDto -> new FileEntity(), fileEntity -> new FileDto(
                        fileEntity.getId(),
                        fileEntity.getName(),
                        fileEntity.getExtension(),
                        fileEntity.getSize(),
                        fileEntity.getType(),
                        fileEntity.getCreatedDate(),
                        fileEntity.getModifiedDate()
                )
        );
    }
}
