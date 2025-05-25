package com.course.plazoleta.infraestructure.input.res;


import com.course.plazoleta.application.dto.request.order.OrderCreateRequest;
import com.course.plazoleta.application.handler.IOrderHandler;
import com.course.plazoleta.domain.utils.constants.OpenApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
