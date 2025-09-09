package uz.qodirov.user.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.qodirov.constant.PathNames;
import uz.qodirov.exception.DataNotFoundException;
import uz.qodirov.user.UserConverter;
import uz.qodirov.user.UserService;
import uz.qodirov.user.dto.UserDto;

@RestController
@RequestMapping(PathNames.API + "profile")
@Tag(name = "Profile controller")
public class ProfileControllerCpV1 {
    private final UserService userService;
    private final UserConverter userConverter;

    public ProfileControllerCpV1(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<UserDto> profile() throws DataNotFoundException {
        return ResponseEntity.ok(userConverter.convertFromEntity(userService.getMe()));
    }
}
