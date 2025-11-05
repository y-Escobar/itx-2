package com.escobar.itx_2.product.infrastructure.dto;

import com.escobar.itx_2.product.domain.ProductRead;

import java.util.List;

public record ListProductsResponseDTO(List<ProductRead> products) {}
