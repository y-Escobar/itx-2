package com.escobar.itx_2.product.domain;

import com.escobar.itx_2.product.domain.primitives.ProductPrimitive;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class ProductScoreTest {

    @Test
    void score_is_weighted_sum_of_known_criteria_and_ignores_unknowns() {
        Product p = Product.fromPrimitive(
                new ProductPrimitive("1","A", 100, Map.of(ProductSize.SMALL, 1, ProductSize.MEDIUM, 0, ProductSize.LARGE, 0))
        );

        Map<String, Double> weights = Map.of(
                "sales_units", 0.5,
                "stock_ratio", 2.0,
                "unknown_criterion", 999.0
        );

        double expected = 100 * 0.5 + (1.0/3.0) * 2.0;
        assertEquals(expected, p.scoreOf(weights), 1e-9);
    }

    @Test
    void null_weights_are_treated_as_zero() {
        Product p = Product.fromPrimitive(
                new ProductPrimitive("1","A", 10, Map.of(ProductSize.SMALL, 1, ProductSize.MEDIUM, 1, ProductSize.LARGE, 1))
        );

        Map<String, Double> weights = new HashMap<>();
        weights.put("sales_units", null);

        double score = p.scoreOf(weights);
        assertEquals(0.0, score, 1e-9);
    }

}
