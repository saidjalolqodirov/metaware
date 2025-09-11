package uz.qodirov.teacher;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.constant.PathNames;

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
}
