package com.escobar.itx_2.product.domain;

import java.util.HashMap;
import java.util.Map;

public record ProductSortingWeights(
        Double salesUnits,
        Double stockRatio
) {
    public Map<String, Double> asMap() {
        var map = new HashMap<String, Double>();
        if (salesUnits != null)  map.put("sales_units", salesUnits);
        if (stockRatio != null)  map.put("stock_ratio", stockRatio);
        return Map.copyOf(map);
    }
}
