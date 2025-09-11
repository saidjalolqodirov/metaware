package uz.qodirov.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.constant.PathNames;
import uz.qodirov.user.dto.ChangePasswordRequest;
import uz.qodirov.user.dto.UserDto;
import uz.qodirov.user.dto.UserUpdateRequest;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(PathNames.API + "user")
@Tag(name = "User controller")
public class UserController {
    private final UserService userService;
    private final UserConverter userConverter;

    public UserController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @PutMapping
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userConverter.convertFromEntity(userService.update(request)));
    }

    @PutMapping("/change-password/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<UserDto> changePassword(@PathVariable String id,
                                                  @Valid @RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(userConverter.convertFromEntity(userService.changePassword(id, request)));
    }

    @PutMapping("/change-username/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<UserDto> changeUsername(@PathVariable String id,
                                                  @Valid @RequestBody ChangeUsernameRequest request) {
        return ResponseEntity.ok(userConverter.convertFromEntity(userService.changeUsername(id, request)));
    }

    @GetMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<UserDto> getOne(@PathVariable String id) {
        return ResponseEntity.ok(userConverter.convertFromEntity(userService.findById(id)));
    }

    @DeleteMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.ok(null);
    }
}
