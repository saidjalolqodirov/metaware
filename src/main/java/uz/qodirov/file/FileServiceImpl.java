package uz.qodirov.file;

import lombok.Getter;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.qodirov.constant.StatusEnum;
import uz.qodirov.exception.DataNotFoundException;
import uz.qodirov.generic.JpaGenericRepository;
import uz.qodirov.generic.JpaGenericServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl extends JpaGenericServiceImpl<FileEntity, String> implements FileService {

    private final String uploadDir = "uploads";

    private final FileRepository fileRepository;

    private final Path fileStorageLocation;

    @Getter
    private final String STATIC_FILE_NAME = "file_for_check_speed";

    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("could_not_create_the_directory_where_the_uploaded_files_will_be_stored", ex);
        }
    }

    @Override
    public FileEntity create(MultipartFile file) throws IOException {
        return save(saveFile(file));
    }

    @Override
    public Resource loadFileAsResource(String fileName) throws DataNotFoundException {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new DataNotFoundException(StatusEnum.DATA_NOT_FOUND, "file_not_found: " + fileName);
            }
        } catch (MalformedURLException | DataNotFoundException e) {
            throw new DataNotFoundException(StatusEnum.DATA_NOT_FOUND, "file_not_found: " + fileName);
        }
    }

    @Override
    public ResponseEntity<Resource> download(String id, HttpServletRequest request) throws DataNotFoundException {
        FileEntity file = findById(id);
        Resource resource = loadFileAsResource(file.getFileName());
        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            if (contentType == null) {
                String[] parts = file.getFileName().split("\\.");
                FileEntity fileEntity = fileRepository.findByGuidAndDeletedFalse(parts[0]);
                contentType = fileEntity.getType();
            }
        } catch (IOException ex) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=31536000")
                .body(resource);
    }

    @Override
    public void deleteFile(String id) throws DataNotFoundException, IOException {
        FileEntity model = findById(id);
        Path path = Paths.get(uploadDir + File.separator + model.getFileName());
        Files.delete(path);
        delete(model.getId());
    }

    @Override
    protected JpaGenericRepository<FileEntity, String> getRepository() {
        return fileRepository;
    }

    private FileEntity saveFile(MultipartFile file) throws IOException {
        String guid = generateGuid();
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        Path uploadPath = Paths.get(uploadDir + File.separator + guid + "." + extension);
        Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
        return new FileEntity(guid, file.getOriginalFilename(), extension, file.getSize(), file.getContentType());
    }

    public String generateGuid() {
        return RandomString.make(20) + System.currentTimeMillis() + RandomString.make(20);
    }
}
