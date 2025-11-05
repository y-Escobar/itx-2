package com.escobar.itx_2.product.domain;

import java.util.Map;

public record ProductRead(String id, String name, Integer salesUnits, Map<ProductSize, Integer> stock) {}