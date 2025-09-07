package uz.qodirov.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.constant.PathNames;
import uz.qodirov.constant.Privilege;
import uz.qodirov.generic.SearchSpecification;
import uz.qodirov.payload.PageableRequest;
import uz.qodirov.user.dto.ChangePasswordRequest;
import uz.qodirov.user.dto.UserDto;
import uz.qodirov.user.dto.UserRequest;
import uz.qodirov.user.dto.UserUpdateRequest;
import uz.qodirov.util.PageableUtil;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(PathNames.API + "user")
@Tag(name = "User controller (CP)")
public class UserControllerCpV1 {
    private final UserService userService;
    private final UserConverter userConverter;

    public UserControllerCpV1(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @PostMapping
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userConverter.convertFromEntity(userService.create(request)));
    }

    @PutMapping
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userConverter.convertFromEntity(userService.update(request)));
    }

    @PutMapping("change-password/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<UserDto> changePassword(@PathVariable String id,
                                                  @Valid @RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(userConverter.convertFromEntity(userService.changePassword(id, request)));
    }

    @PostMapping("pageable")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<Page<UserDto>> pageable(@Valid @RequestBody PageableRequest request) {
        PageRequest pageRequest = PageableUtil.pageRequest(request);
        return ResponseEntity.ok(userConverter.createFromEntities(userService.findAll(new SearchSpecification<>(request.getSearch()), pageRequest)));
    }

    @GetMapping("{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<UserDto> getOne(@PathVariable String id) {
        return ResponseEntity.ok(userConverter.convertFromEntity(userService.findById(id)));
    }

    @DeleteMapping("{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/privileges")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")}, summary = "Получить все привилегии")
    public ResponseEntity<Privilege[]> allPrivileges() {
        return ResponseEntity.ok(Privilege.values());
    }

}
