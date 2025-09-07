package uz.qodirov.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class FileControllerCpV1 {
    private final FileService fileService;
    private final FileConverter fileConverter;

    public FileControllerCpV1(FileService fileService, FileConverter fileConverter) {
        this.fileService = fileService;
        this.fileConverter = fileConverter;
    }

    @PostMapping
    @PreAuthorize("hasAuthority(T(uz.qodirov.constant.Privilege).CONTROL__FILE__CREATE)")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<FileDto> create(@RequestParam("file") MultipartFile uploadedFile) throws IOException {
        return ResponseEntity.ok(fileConverter.convertFromEntity(fileService.create(uploadedFile)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(uz.qodirov.constant.Privilege).CONTROL__FILE__GET)")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<FileDto> get(@PathVariable("id") String id) throws DataNotFoundException {
        return ResponseEntity.ok(fileConverter.convertFromEntity(fileService.findById(id)));
    }

    @PostMapping("/pageable")
    @PreAuthorize("hasAuthority(T(uz.qodirov.constant.Privilege).CONTROL__FILE__PAGEABLE)")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<Page<FileDto>> pagination(@RequestBody PageableRequest pageable) {
        PageRequest pageRequest = PageableUtil.pageRequest(pageable);
        return ResponseEntity.ok(
                fileConverter.createFromEntities(fileService.findAll(new SearchSpecification<>(pageable.getSearch()), pageRequest)));
    }

    @GetMapping("/download/{fileName}")
    @PreAuthorize("hasAuthority(T(uz.qodirov.constant.Privilege).CONTROL__FILE__DOWNLOAD)")
    public ResponseEntity<Resource> download(@PathVariable("fileName") String fileName, HttpServletRequest request) throws DataNotFoundException {
        return fileService.download(fileName, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(uz.qodirov.constant.Privilege).CONTROL__FILE__DELETE)")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) throws DataNotFoundException, IOException {
        fileService.deleteFile(id);
        return ResponseEntity.ok(null);
    }

}
