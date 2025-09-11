package uz.qodirov.file;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.qodirov.exception.DataNotFoundException;
import uz.qodirov.generic.JpaGenericService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public interface FileService extends JpaGenericService<FileEntity, String> {
    FileEntity create(MultipartFile file) throws IOException;

    Resource loadFileAsResource(String fileName) throws DataNotFoundException;

    ResponseEntity<Resource> download(String id, HttpServletRequest request) throws DataNotFoundException;

    void deleteFile(String id) throws DataNotFoundException, IOException;
}
