package uz.qodirov.group;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.constant.PathNames;
import uz.qodirov.generic.SearchSpecification;
import uz.qodirov.payload.PageableRequest;
import uz.qodirov.util.PageableUtil;
import uz.qodirov.util.SecurityContextUtil;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(PathNames.API + "group")
@Tag(name = "Group controller")
public class GroupController {
    private final GroupService service;
    private final GroupConvertor convertor;

    public GroupController(GroupService service, GroupConvertor convertor) {
        this.service = service;
        this.convertor = convertor;
    }

    @PostMapping
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<GroupResponseDto> createTeacher(@RequestBody @Valid GroupRequest request) {
        return ResponseEntity.ok(convertor.convertFromEntity(service.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<GroupResponseDto> update(@PathVariable String id, @RequestBody @Valid GroupRequest request) {
        return ResponseEntity.ok(convertor.convertFromEntity(service.update(id, request)));
    }

    @GetMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<GroupResponseDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(convertor.convertFromEntity(service.getOne(id)));
    }

    @PostMapping("/pageable")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<Page<GroupResponseDto>> getAll(@RequestBody @Valid PageableRequest pageable) {
        PageRequest pageRequest = PageableUtil.pageRequest(pageable);
        String teacherId = SecurityContextUtil.getId();
        GroupSpecification groupSpecification = new GroupSpecification(teacherId);
        return ResponseEntity.ok(
                convertor.createFromEntities(service.findAll(groupSpecification.and(new SearchSpecification<>(pageable.getSearch())), pageRequest)));
    }

    @PostMapping("/attach-student")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<GroupResponseDto> attachStudent(@RequestBody @Valid GroupStudentRequest request) {
        return ResponseEntity.ok(convertor.convertFromEntity(service.attachStudent(request)));
    }

    @PostMapping("/detach-student")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<GroupResponseDto> detachStudent(@RequestBody @Valid GroupStudentRequest request) {
        return ResponseEntity.ok(convertor.convertFromEntity(service.detachStudent(request)));
    }

}
