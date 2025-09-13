package uz.qodirov.student;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.constant.PathNames;
import uz.qodirov.constant.Role;
import uz.qodirov.generic.SearchSpecification;
import uz.qodirov.payload.PageableRequest;
import uz.qodirov.teacher.RoleSpecification;
import uz.qodirov.util.PageableUtil;

import javax.validation.Valid;

@RestController
@RequestMapping(PathNames.API + "student")
public class StudentController {
    private final StudentService studentService;
    private final StudentConvertor convertor;

    public StudentController(StudentService studentService, StudentConvertor convertor) {
        this.studentService = studentService;
        this.convertor = convertor;
    }

    @GetMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<StudentDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(convertor.convertFromEntity(studentService.getOne(id)));
    }

    @PostMapping("/pageable")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<Page<StudentDto>> getAll(@RequestBody @Valid PageableRequest pageable) {
        PageRequest pageRequest = PageableUtil.pageRequest(pageable);
        RoleSpecification roleSpecification = new RoleSpecification(Role.STUDENT);
        return ResponseEntity.ok(
                convertor.createFromEntities(studentService.findAll(roleSpecification.and(new SearchSpecification<>(pageable.getSearch())), pageRequest)));
    }

}
