package com.escobar.itx_2.product.application;

import com.escobar.itx_2.product.config.InMemoryProductRepository;
import com.escobar.itx_2.product.domain.Product;
import com.escobar.itx_2.product.domain.ProductSize;
import com.escobar.itx_2.product.infrastructure.dto.ProductSortingWeights;
import com.escobar.itx_2.product.domain.primitives.ProductPrimitive;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortProductTest {
    private static List<Product> sampleProducts() {
        return List.of(
            Product.fromPrimitive(new ProductPrimitive("1","V-NECH BASIC SHIRT", 100, Map.of(ProductSize.SMALL, 4, ProductSize.MEDIUM, 9, ProductSize.LARGE, 0))),
            Product.fromPrimitive(new ProductPrimitive("2","CONTRASTING FABRIC T-SHIRT", 50, Map.of(ProductSize.SMALL, 35, ProductSize.MEDIUM, 9, ProductSize.LARGE, 9))),
            Product.fromPrimitive(new ProductPrimitive("3","RAISED PRINT T-SHIRT", 80, Map.of(ProductSize.SMALL, 20, ProductSize.MEDIUM, 2, ProductSize.LARGE, 20))),
            Product.fromPrimitive(new ProductPrimitive("4","PLEATED T-SHIRT", 3, Map.of(ProductSize.SMALL, 25, ProductSize.MEDIUM, 30, ProductSize.LARGE, 10))),
            Product.fromPrimitive(new ProductPrimitive("5","CONTRASTING LACE T-SHIRT", 650, Map.of(ProductSize.SMALL, 0, ProductSize.MEDIUM, 1, ProductSize.LARGE, 0))),
            Product.fromPrimitive(new ProductPrimitive("6","SLOGAN T-SHIRT", 20, Map.of(ProductSize.SMALL, 9, ProductSize.MEDIUM, 2, ProductSize.LARGE, 5)))
        );
    }

    @Test
    void sorts_products_by_weighted_score_desc() {
        var repo = new InMemoryProductRepository(sampleProducts());
        var service = new ListProducts(repo);

        var weights = new ProductSortingWeights(1.0, 1.0); // sales + stock ratio

        var result = service.execute(weights.asMap());
        assertFalse(result.isEmpty());

        var byId = sampleProducts().stream().collect(java.util.stream.Collectors.toMap(
                p -> p.toProductRead().id(), p -> p
        ));

        double prev = Double.POSITIVE_INFINITY;
        for (var read : result) {
            var p = byId.get(read.id());
            double score = p.scoreOf(weights.asMap());
            assertTrue(score <= prev + 1e-9, "sequence must be non-increasing");
            prev = score;
        }
    }
}
