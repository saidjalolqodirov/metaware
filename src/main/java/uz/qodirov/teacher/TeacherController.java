package uz.qodirov.teacher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.constant.PathNames;
import uz.qodirov.constant.Role;
import uz.qodirov.generic.SearchSpecification;
import uz.qodirov.payload.PageableRequest;
import uz.qodirov.util.PageableUtil;

import javax.validation.Valid;

@RestController
@RequestMapping(PathNames.API + "teacher")
public class TeacherController {
    private final TeacherService teacherService;
    private final TeacherConvertor convertor;

    public TeacherController(TeacherService teacherService, TeacherConvertor convertor) {
        this.teacherService = teacherService;
        this.convertor = convertor;
    }

    @PostMapping
    public ResponseEntity<TeacherDto> createTeacher(@RequestBody @Valid TeacherRequest request) {
        return ResponseEntity.ok(convertor.convertFromEntity(teacherService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDto> update(@PathVariable String id, @RequestBody @Valid TeacherRequest request) {
        return ResponseEntity.ok(convertor.convertFromEntity(teacherService.update(id, request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(convertor.convertFromEntity(teacherService.getOne(id)));
    }

    @PostMapping("/pageable")
    public ResponseEntity<Page<TeacherDto>> getAll(@RequestBody @Valid PageableRequest pageable) {
        PageRequest pageRequest = PageableUtil.pageRequest(pageable);
        TeacherSpecification teacherSpecification = new TeacherSpecification(Role.TEACHER);
        return ResponseEntity.ok(
                convertor.createFromEntities(teacherService.findAll(teacherSpecification.and(new SearchSpecification<>(pageable.getSearch())), pageRequest)));
    }


}
