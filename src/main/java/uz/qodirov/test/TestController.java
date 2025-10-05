package uz.qodirov.test;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.constant.PathNames;
import uz.qodirov.generic.SearchSpecification;
import uz.qodirov.payload.PageableRequest;
import uz.qodirov.util.PageableUtil;

import javax.validation.Valid;

@RestController
@RequestMapping(PathNames.API + "test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;
    private final TestConvertor convertor;

    @PostMapping
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<TestResponse> createTest(@RequestBody @Valid TestRequest request) {
        return ResponseEntity.ok(convertor.convertFromEntity(testService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<TestResponse> update(@PathVariable String id, @RequestBody @Valid TestRequest request) {
        return ResponseEntity.ok(convertor.convertFromEntity(testService.update(id, request)));
    }

    @GetMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<TestResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(convertor.convertFromEntity(testService.getOne(id)));
    }

    @DeleteMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<Void> delete(@PathVariable String id) {
        testService.delete(id);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/pageable")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<Page<TestResponse>> getAll(@RequestBody @Valid PageableRequest pageable) {
        PageRequest pageRequest = PageableUtil.pageRequest(pageable);
        return ResponseEntity.ok(
                convertor.createFromEntities(testService.findAll(new SearchSpecification<>(pageable.getSearch()), pageRequest)));
    }

}
