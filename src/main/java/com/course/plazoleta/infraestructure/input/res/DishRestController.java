package com.course.plazoleta.infraestructure.input.res;

import com.course.plazoleta.application.dto.request.DishRequest;
import com.course.plazoleta.application.dto.request.DishUpdateRequest;
import com.course.plazoleta.application.dto.response.DishListResponse;
import com.course.plazoleta.application.dto.response.DishResponse;
import com.course.plazoleta.application.dto.response.RestaurantResponse;
import com.course.plazoleta.application.handler.IDishHandler;
import com.course.plazoleta.domain.model.Dish;
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

import java.util.List;

@RestController
@RequestMapping("/dish/")
@Tag(name = OpenApiConstants.TITLE_DISH_REST, description = OpenApiConstants.TITLE_DESCRIPTION_DISH_REST)
@RequiredArgsConstructor
public class DishRestController {
    private final IDishHandler dishHandler;

    @Operation(summary = OpenApiConstants.NEW_DISH_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = OpenApiConstants.NEW_DISH_CREATED_MESSAGE, content = @Content),
            @ApiResponse(responseCode = "400", description = OpenApiConstants.VALIDATIONS_ERRORS_MESSAGE, content = @Content)
    })
    @PostMapping()
    @PreAuthorize("@permissionService.isOwnerOfRestaurant(authentication, #dishRequest.idRestaurant)")
    public ResponseEntity<Void> saveDish ( @RequestBody DishRequest dishRequest) {
        dishHandler.saveDish(dishRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = OpenApiConstants.GET_DISH_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OpenApiConstants.GET_DISH_MESSAGE, content = @Content),
            @ApiResponse(responseCode = "400", description = OpenApiConstants.VALIDATIONS_ERRORS_MESSAGE, content = @Content)
    })
    @GetMapping("{id}")
    @PreAuthorize("@permissionService.isOwner(authentication)")
    public ResponseEntity<DishResponse> getDishById(@PathVariable(value = "id") Long id) {
        return  ResponseEntity.ok(dishHandler.getDishById(id));
    }

    @Operation(summary = OpenApiConstants.GET_ALL_DISH_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OpenApiConstants.GET_ALL_DISH_MESSAGE,
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DishResponse.class)))),
            @ApiResponse(responseCode = "404", description =OpenApiConstants.NO_DATA_MESSAGE, content = @Content)
    })
    @GetMapping()
    @PreAuthorize("@permissionService.isOwner(authentication)")
    public ResponseEntity<List<DishResponse>> getAllDishes(){
        return ResponseEntity.ok(dishHandler.getAllDishes());
    }


    @Operation(summary = OpenApiConstants.UPDATE_DISH_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OpenApiConstants.UPDATE_DISH_MESSAGE, content = @Content),
            @ApiResponse(responseCode = "409", description = OpenApiConstants.UPDATE_NOT_EXIST_DISH_MESSAGE, content = @Content)
    })
    @PutMapping("update")
    @PreAuthorize("@permissionService.isOwnerOfDish(authentication,#dishUpdateRequest.id)")
    public ResponseEntity<Void> updateDish(@Valid @RequestBody DishUpdateRequest dishUpdateRequest){
        dishHandler.updateDish(dishUpdateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = OpenApiConstants.CHANGE_STATE_DISH_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OpenApiConstants.CHANGE_STATE_DISH_MESSAGE, content = @Content),
    })
    @PutMapping("changeState/{id}")
    @PreAuthorize("@permissionService.isOwnerOfDish(authentication, #id)")
    public ResponseEntity<Void> changeStateDish(@PathVariable Dish id){

        dishHandler.changeStateDish(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = OpenApiConstants.GET_ALL_DISH_BY_RESTAURANT_CATEGORY_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OpenApiConstants.GET_ALL_DISH_BY_RESTAURANT_CATEGORY_MESSAGE,
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DishListResponse.class)))),
            @ApiResponse(responseCode = "404", description = OpenApiConstants.NO_DATA_MESSAGE , content = @Content)
    })
    @GetMapping("filter")
    public ResponseEntity<PageModel<DishListResponse>> getAllDishesByRestaurantByCategory(
            @RequestParam(name = "page", defaultValue = ValuesConstants.MIN_VALUE_PAGE_PAGINATION) int page,
            @RequestParam(name = "pageSize", defaultValue =  ValuesConstants.MIN_VALUE_PAGE_SIZE_PAGINATION) int pageSize,
            @RequestParam(name = "idRestaurant", defaultValue =  ValuesConstants.DEFAULT_FIELD_ID_RESTAURANT_PAGINATION) int idRestaurant,
            @RequestParam(name = "idCategory", defaultValue =  ValuesConstants.DEFAULT_FIELD_ID_CATEGORY_PAGINATION) int idCategory
    ) {
        return ResponseEntity.ok(dishHandler.getAllDishesByRestaurantByCategory(page,pageSize,idRestaurant,idCategory));
    }

}
