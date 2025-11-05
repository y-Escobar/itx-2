package com.escobar.itx_2.product.infrastructure.entity;

import com.escobar.itx_2.product.domain.Product;
import com.escobar.itx_2.product.domain.ProductSize;
import com.escobar.itx_2.product.domain.primitives.ProductPrimitive;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document("products")
public class ProductEntity {

    @Id
    private final String id;
    private final String name;
    private final Integer salesUnits;
    private final Map<ProductSize, Integer> stock;

    private ProductEntity(String id, String name, Integer salesUnits, Map<ProductSize, Integer> stock) {
        this.id = id;
        this.name = name;
        this.salesUnits = salesUnits;
        this.stock = stock;
    }

    public static ProductEntity fromDomain(ProductPrimitive productPrimitive){
        return new ProductEntity(productPrimitive.id(), productPrimitive.name(), productPrimitive.salesUnits(), productPrimitive.stock());
    }

    public Product toDomain(){
        return Product.fromPrimitive(new ProductPrimitive(this.id, this.name, this.salesUnits, this.stock));
    }
}
