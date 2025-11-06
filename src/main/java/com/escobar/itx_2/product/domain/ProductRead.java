package com.escobar.itx_2.product.domain;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

public record ProductRead(
        String id,
        String name,
        Integer salesUnits,
        @Schema(description = "Mapa de stock por talla; (SMALL, MEDIUM, LARGE).")
        Map<ProductSize, Integer> stock
) {}