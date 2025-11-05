package com.escobar.itx_2.product.domain.primitives;

import com.escobar.itx_2.product.domain.ProductSize;

import java.util.Map;

public record ProductPrimitive(String id, String name, Integer salesUnits, Map<ProductSize, Integer> stock) {}
