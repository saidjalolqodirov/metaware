package uz.qodirov.file;

import org.springframework.stereotype.Component;
import uz.qodirov.generic.Converter;


@Component
public class FileMinConverter extends Converter<FileMinDto, FileEntity> {
    public FileMinConverter() {
        super(fileMinDto -> new FileEntity(), fileEntity -> new FileMinDto(
                        fileEntity.getId(),
                        fileEntity.getFileUrl()
                )
        );
    }
}
