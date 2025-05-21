package com.course.plazoleta.infraestructure.input.res;

import com.course.plazoleta.application.dto.request.RestaurantRequest;
import com.course.plazoleta.application.dto.response.RestaurantResponse;
import com.course.plazoleta.application.handler.IRestaurantHandler;
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
@RequestMapping("/restaurant/")
@Tag(name = "RESTAURANT", description = "Endpoints for restaurants")
@RequiredArgsConstructor
public class RestaurantRestController {

    private static final String ROLE_ADMIN     = "hasRole('1')";
    private static final String ROLES_ADMIN_OWNER = "hasAnyRole('1','2')";
    private final IRestaurantHandler restaurantHandler;


    @Operation(summary = "Add a new restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation errors", content = @Content)
    })
    @PostMapping()
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<Void> saveRestaurant (@Valid @RequestBody RestaurantRequest restaurantRequest) {
        restaurantHandler.saveRestaurant(restaurantRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get restaurant by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation errors", content = @Content)
    })
    @GetMapping("{id}")
    @PreAuthorize(ROLES_ADMIN_OWNER)
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable(value = "id") int id) {
        return  ResponseEntity.ok(restaurantHandler.getRestaurantById(id));
    }

    @Operation(summary = "Get all restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All restaurants returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RestaurantResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping()
    @PreAuthorize(ROLES_ADMIN_OWNER)
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants(){
        return ResponseEntity.ok(restaurantHandler.getAllRestaurants());
    }



    @Operation(summary = "Delete restaurant by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content)
    })
    @DeleteMapping("{id}")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<Void> deleteRestaurantById(@PathVariable(value = "id")int id){
        restaurantHandler.deleteRestaurantById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
