package com.course.plazoleta.infraestructure.input.res;


import com.course.plazoleta.application.dto.request.order.OrderCreateRequest;
import com.course.plazoleta.application.dto.response.RestaurantResponse;
import com.course.plazoleta.application.dto.response.order.OrderResponse;
import com.course.plazoleta.application.handler.IOrderHandler;
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
@RequestMapping("/order/")
@Tag(name = OpenApiConstants.TITLE_ORDER_REST, description = OpenApiConstants.TITLE_DESCRIPTION_ORDER_REST)
@RequiredArgsConstructor
public class OrderRestController {

    private final IOrderHandler orderHandler;

    @Operation(summary = OpenApiConstants.NEW_ORDER_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = OpenApiConstants.NEW_ORDER_CREATED_MESSAGE, content = @Content),
            @ApiResponse(responseCode = "400", description = OpenApiConstants.VALIDATIONS_ERRORS_MESSAGE, content = @Content)
    })
    @PostMapping()
    @PreAuthorize("@permissionService.isClient(authentication)")
    public ResponseEntity<Void> createOrder (@Valid @RequestBody OrderCreateRequest orderCreateRequest) {
        orderHandler.createOrder(orderCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = OpenApiConstants.GET_FILTER_ORDERS_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OpenApiConstants.GET_FILTER_ORDERS_MESSAGE,
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RestaurantResponse.class)))),
            @ApiResponse(responseCode = "404", description = OpenApiConstants.NO_DATA_MESSAGE , content = @Content)
    })
    @GetMapping()
    @PreAuthorize("@permissionService.isEmployee(authentication)")
    public ResponseEntity<PageModel<OrderResponse>> getOrdersFilterByState(
            @RequestParam(name = "page", defaultValue = ValuesConstants.MIN_VALUE_PAGE_PAGINATION) int page,
            @RequestParam(name = "pageSize", defaultValue =  ValuesConstants.MIN_VALUE_PAGE_SIZE_PAGINATION) int pageSize,
            @RequestParam(name = "state", defaultValue =  ValuesConstants.DEFAULT_FIELD_ORDER_STATE_PAGINATION) String state
    ) {
        return ResponseEntity.ok(orderHandler.getOrdersFilterByState(page,pageSize,state));
    }

    @Operation(summary = OpenApiConstants.TAKE_ORDER_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OpenApiConstants.TAKE_ORDER_MESSAGE,
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RestaurantResponse.class)))),
            @ApiResponse(responseCode = "404", description = OpenApiConstants.NO_DATA_MESSAGE , content = @Content)
    })
    @PutMapping("takeOrder")
    @PreAuthorize("@permissionService.isEmployee(authentication)")
    public ResponseEntity<OrderResponse> takeOrder(
            @RequestParam(name = "idOrder", defaultValue = ValuesConstants.DEFAULT_ID_ORDER_TAKE) Long idOrder
    ) {
        return ResponseEntity.ok(orderHandler.takeOrder(idOrder));
    }

    @Operation(summary = OpenApiConstants.COMPLETE_ORDER_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OpenApiConstants.COMPLETE_ORDER_MESSAGE,
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RestaurantResponse.class)))),
            @ApiResponse(responseCode = "404", description = OpenApiConstants.NO_DATA_MESSAGE , content = @Content)
    })
    @PutMapping("completeOrder")
    @PreAuthorize("@permissionService.isEmployee(authentication)")
    public ResponseEntity<OrderResponse> completeOrderAndNotify(
            @RequestParam(name = "idOrder", defaultValue = ValuesConstants.DEFAULT_ID_ORDER_TAKE) Long idOrder
    ) {
        return ResponseEntity.ok(orderHandler.completeOrderAndNotify(idOrder));
    }


    @Operation(summary = OpenApiConstants.DELIVERED_ORDER_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OpenApiConstants.DELIVERED_ORDER_MESSAGE,
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RestaurantResponse.class)))),
            @ApiResponse(responseCode = "404", description = OpenApiConstants.NO_DATA_MESSAGE , content = @Content)
    })
    @PutMapping("deliverOrder")
    @PreAuthorize("@permissionService.isEmployee(authentication)")
    public ResponseEntity<OrderResponse> deliverOrder(
            @RequestParam(name = "idOrder", defaultValue = ValuesConstants.DEFAULT_ID_ORDER_TAKE) Long idOrder,
            @RequestParam(name = "pin", defaultValue = ValuesConstants.DEFAULT_PIN_ORDER) String pin
    ) {
        return ResponseEntity.ok(orderHandler.deliverOrder(idOrder,pin));
    }


    @Operation(summary = OpenApiConstants.CANCEL_ORDER_TITLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OpenApiConstants.CANCEL_ORDER_MESSAGE,
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RestaurantResponse.class)))),
            @ApiResponse(responseCode = "404", description = OpenApiConstants.NO_DATA_MESSAGE , content = @Content)
    })
    @PutMapping("cancelOrder")
    @PreAuthorize("@permissionService.isClient(authentication)")
    public ResponseEntity<OrderResponse> cancelOrder(
            @RequestParam(name = "idOrder", defaultValue = ValuesConstants.DEFAULT_ID_ORDER_TAKE) Long idOrder

    ) {
        return ResponseEntity.ok(orderHandler.cancelOrder(idOrder));
    }

}
