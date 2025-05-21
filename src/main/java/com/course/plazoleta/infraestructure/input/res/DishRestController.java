package com.course.plazoleta.infraestructure.input.res;

import com.course.plazoleta.application.dto.request.DishRequest;
import com.course.plazoleta.application.dto.request.DishUpdateRequest;
import com.course.plazoleta.application.dto.response.DishResponse;
import com.course.plazoleta.application.dto.response.RestaurantResponse;
import com.course.plazoleta.application.handler.IDishHandler;
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
@RequestMapping("/dish/")
@Tag(name = "Dish", description = "Endpoints for dishes")
@RequiredArgsConstructor
public class DishRestController {
    private final IDishHandler dishHandler;
    private static final String ROLE_OWNER      = "hasRole('2')";

    @Operation(summary = "Add a new dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation errors", content = @Content)
    })
    @PostMapping()
    @PreAuthorize(ROLE_OWNER)
    public ResponseEntity<Void> saveDish (@Valid @RequestBody DishRequest dishRequest) {
        dishHandler.saveDish(dishRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get dish by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation errors", content = @Content)
    })
    @GetMapping("{id}")
    @PreAuthorize(ROLE_OWNER)
    public ResponseEntity<DishResponse> getDishById(@PathVariable(value = "id") Long id) {
        return  ResponseEntity.ok(dishHandler.getDishById(id));
    }

    @Operation(summary = "Get all dishes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All dishes returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RestaurantResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping()
    @PreAuthorize(ROLE_OWNER)
    public ResponseEntity<List<DishResponse>> getAllDishes(){
        return ResponseEntity.ok(dishHandler.getAllDishes());
    }


    @Operation(summary = "Update dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modified dish", content = @Content),
            @ApiResponse(responseCode = "409", description = "The dish does not exist", content = @Content)
    })
    @PutMapping("update")
    @PreAuthorize(ROLE_OWNER)
    public ResponseEntity<Void> updateDish(@Valid @RequestBody DishUpdateRequest dishUpdateRequest){
        dishHandler.updateDish(dishUpdateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
