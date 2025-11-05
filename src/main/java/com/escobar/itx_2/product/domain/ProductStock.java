package com.escobar.itx_2.product.domain;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductStock {
    Map<ProductSize, ProductStockAmount> stock;

    private ProductStock(Map<ProductSize, ProductStockAmount> productStock) {this.stock = productStock;}

    public static ProductStock zero(){
        return new ProductStock(new HashMap<>());
    }

    public static ProductStock fromPrimitive(Map<ProductSize, Integer> stockPrimitive){
        if (stockPrimitive == null) return (ProductStock) Map.of();

        Map<ProductSize, ProductStockAmount> stock = stockPrimitive.entrySet().stream()
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> ProductStockAmount.fromPrimitive(e.getValue()),
                    (firstEntry, secondEntry) -> firstEntry,
                    () -> new EnumMap<>(ProductSize.class)
            ));

        return new ProductStock(stock);
    }

    public Map<ProductSize, Integer> toPrimitive(){
        return stock.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().getValue(),
                (firstEntry, secondEntry) -> firstEntry,
                () -> new EnumMap<>(ProductSize.class)
        ));
    }
}
