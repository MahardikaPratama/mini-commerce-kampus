package com.mahardikapratama.catalog.dto.request;

import com.mahardikapratama.catalog.enums.ProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusRequest {

    @NotNull(message = "Status is required")
    private ProductStatus status;

}