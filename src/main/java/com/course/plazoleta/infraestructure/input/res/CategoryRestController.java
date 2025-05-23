package com.course.plazoleta.infraestructure.input.res;

import com.course.plazoleta.application.dto.request.CategoryRequest;
import com.course.plazoleta.application.dto.request.DishRequest;
import com.course.plazoleta.application.dto.response.CategoryResponse;
import com.course.plazoleta.application.dto.response.DishResponse;
import com.course.plazoleta.application.dto.response.RestaurantResponse;
import com.course.plazoleta.application.handler.ICategoryHandler;
import com.course.plazoleta.domain.utils.constants.OpenApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category/")
@Tag(name = OpenApiConstants.TITLE_CATEGORY_REST, description = OpenApiConstants.TITLE_DESCRIPTION_CATEGORY_REST)
@RequiredArgsConstructor
public class CategoryRestController {
    private final ICategoryHandler categoryHandler;
    private static final String ROLES_ADMIN_OWNER = "hasAnyRole('1','2')";

    @Operation(summary = OpenApiConstants.NEW_CATEGORY_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = OpenApiConstants.NEW_CATEGORY_CREATED_MESSAGE, content = @Content),
            @ApiResponse(responseCode = "400", description = OpenApiConstants.VALIDATIONS_ERRORS_MESSAGE , content = @Content)
    })
    @PostMapping()
    @PreAuthorize("@permissionService.isAdminOrOwner(authentication)")
    public ResponseEntity<Void> saveCategory (@Valid @RequestBody CategoryRequest categoryRequest) {
        categoryHandler.saveCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = OpenApiConstants.GET_CATEGORY_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OpenApiConstants.GET_CATEGORY_MESSAGE, content = @Content),
            @ApiResponse(responseCode = "400", description = OpenApiConstants.VALIDATIONS_ERRORS_MESSAGE, content = @Content)
    })
    @GetMapping("{id}")
    @PreAuthorize("@permissionService.isAdminOrOwner(authentication)")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable(value = "id") long id) {
        return  ResponseEntity.ok(categoryHandler.getCategoryById(id));
    }

    @Operation(summary = OpenApiConstants.GET_ALL_CATEGORY_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OpenApiConstants.GET_ALL_CATEGORY_MESSAGE,
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RestaurantResponse.class)))),
            @ApiResponse(responseCode = "404", description = OpenApiConstants.NO_DATA_MESSAGE,  content = @Content)
    })
    @GetMapping()
    @PreAuthorize("@permissionService.isAdminOrOwner(authentication)")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        return ResponseEntity.ok(categoryHandler.getAllCategories());
    }

}
