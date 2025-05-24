package com.course.plazoleta.infraestructure.input.res;

import com.course.plazoleta.application.dto.request.RestaurantRequest;
import com.course.plazoleta.application.dto.response.RestaurantResponse;
import com.course.plazoleta.application.handler.IRestaurantHandler;
import com.course.plazoleta.domain.model.PageModel;
import com.course.plazoleta.domain.utils.constants.OpenApiConstants;
import com.course.plazoleta.domain.utils.constants.ValuesConstants;
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

@RestController
@RequestMapping("/restaurant/")
@Tag(name = OpenApiConstants.TITLE_RESTAURANT_REST, description = OpenApiConstants.TITLE_DESCRIPTION_RESTAURANT_REST)
@RequiredArgsConstructor
public class RestaurantRestController {


    private final IRestaurantHandler restaurantHandler;


    @Operation(summary = OpenApiConstants.NEW_RESTAURANT_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = OpenApiConstants.NEW_RESTAURANT_CREATED_MESSAGE, content = @Content),
            @ApiResponse(responseCode = "400", description = OpenApiConstants.VALIDATIONS_ERRORS_MESSAGE, content = @Content)
    })
    @PostMapping()
    @PreAuthorize("@permissionService.isAdmin(authentication)")
    public ResponseEntity<Void> saveRestaurant (@Valid @RequestBody RestaurantRequest restaurantRequest) {
        restaurantHandler.saveRestaurant(restaurantRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = OpenApiConstants.GET_RESTAURANT_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OpenApiConstants.GET_RESTAURANT_MESSAGE, content = @Content),
            @ApiResponse(responseCode = "400", description =OpenApiConstants.VALIDATIONS_ERRORS_MESSAGE, content = @Content)
    })
    @GetMapping("{id}")
    @PreAuthorize("@permissionService.isAdminOrOwner(authentication)")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable(value = "id") long id) {
        return  ResponseEntity.ok(restaurantHandler.getRestaurantById(id));
    }

    @Operation(summary = OpenApiConstants.GET_ALL_RESTAURANT_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OpenApiConstants.GET_ALL_RESTAURANT_MESSAGE,
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RestaurantResponse.class)))),
            @ApiResponse(responseCode = "404", description = OpenApiConstants.NO_DATA_MESSAGE , content = @Content)
    })
    @GetMapping()
    @PreAuthorize("@permissionService.isAdminOrOwner(authentication)")
    public ResponseEntity<PageModel<RestaurantResponse>> getAllRestaurants(
    @RequestParam(name = "page", defaultValue = ValuesConstants.MIN_VALUE_PAGE_PAGINATION) int page,
    @RequestParam(name = "pageSize", defaultValue =  ValuesConstants.MIN_VALUE_PAGE_SIZE_PAGINATION) int pageSize,
    @RequestParam(name = "field", defaultValue =  ValuesConstants.DEFAULT_FIELD_ORDER_RESTAURANT_PAGINATION) String field
    ) {
        return ResponseEntity.ok(restaurantHandler.getAllRestaurants(page,pageSize,field));
    }



    @Operation(summary = OpenApiConstants.DELETE_RESTAURANT_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OpenApiConstants.DELETE_RESTAURANT_MESSAGE, content = @Content),
            @ApiResponse(responseCode = "404", description =  OpenApiConstants.NO_DATA_MESSAGE , content = @Content)
    })
    @DeleteMapping("{id}")
    @PreAuthorize("@permissionService.isAdmin(authentication)")
    public ResponseEntity<Void> deleteRestaurantById(@PathVariable(value = "id")long id){
        restaurantHandler.deleteRestaurantById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
