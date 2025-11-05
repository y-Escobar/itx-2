package com.escobar.itx_2.product.domain;

import com.escobar.itx_2.product.domain.primitives.ProductPrimitive;

import java.util.Map;

public class Product {

    ProductId id;
    ProductName name;
    ProductSalesUnits productSalesUnits;
    ProductStock productStock;

    private Product(ProductId id, ProductName name, ProductSalesUnits productSalesUnits, ProductStock productStock) {
        this.id = id;
        this.name = name;
        this.productSalesUnits = productSalesUnits;
        this.productStock = productStock;
    }

    public String getNameValue(){return name.getValue();}

    public static Product create(String id, String name){
        return new Product(ProductId.create(id), ProductName.create(name), ProductSalesUnits.zero(), ProductStock.zero());
    }

    public static Product fromPrimitive(ProductPrimitive productPrimitive){
        return new Product(
                ProductId.fromPrimitive(productPrimitive.id()),
                ProductName.fromPrimitive(productPrimitive.name()),
                ProductSalesUnits.fromPrimitive(productPrimitive.salesUnits()),
                ProductStock.fromPrimitive(productPrimitive.stock())
            );
    }

    public ProductRead toProductRead(){
        return new ProductRead(
                id.getValue(),
                name.getValue(),
                productSalesUnits.getValue(),
                this.productStock.toPrimitive()
        );
    }

    public Double scoreOf(Map<String, Double> weights) {
        return weights.entrySet().stream()
        .mapToDouble(entry -> {
            String criterionName = entry.getKey();
            Double weight = entry.getValue();

            if (weight == null) {
                return 0.0;
            }

            try {
                ProductSortingCriteria criterion = ProductSortingCriteria.valueOf(criterionName.toUpperCase());
                double score = criterion.calculateScore(this);
                return score * weight;
            } catch (IllegalArgumentException e) {
                return 0.0;
            }
        })
        .sum();
    }
}