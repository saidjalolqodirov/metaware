package uz.qodirov.file;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.qodirov.constant.PathNames;
import uz.qodirov.exception.DataNotFoundException;
import uz.qodirov.generic.SearchSpecification;
import uz.qodirov.payload.PageableRequest;
import uz.qodirov.util.PageableUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(PathNames.API + "file")
@Tag(name = "File Controller")
public class FileController {
    private final FileService fileService;
    private final FileConverter fileConverter;

    public FileController(FileService fileService, FileConverter fileConverter) {
        this.fileService = fileService;
        this.fileConverter = fileConverter;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileDto> create(MultipartFile file) throws IOException {
        return ResponseEntity.ok(fileConverter.convertFromEntity(fileService.create(file)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDto> get(@PathVariable("id") String id) throws DataNotFoundException {
        return ResponseEntity.ok(fileConverter.convertFromEntity(fileService.findById(id)));
    }

    @PostMapping("/pageable")
    public ResponseEntity<Page<FileDto>> pagination(@RequestBody PageableRequest pageable) {
        PageRequest pageRequest = PageableUtil.pageRequest(pageable);
        return ResponseEntity.ok(
                fileConverter.createFromEntities(fileService.findAll(new SearchSpecification<>(pageable.getSearch()), pageRequest)));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") String id, HttpServletRequest request) throws DataNotFoundException {
        return fileService.download(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) throws DataNotFoundException, IOException {
        fileService.deleteFile(id);
        return ResponseEntity.ok(null);
    }

}
