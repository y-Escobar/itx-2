package com.escobar.itx_2.product.unit.domain;

import com.escobar.itx_2.product.domain.Product;
import com.escobar.itx_2.product.domain.ProductSize;
import com.escobar.itx_2.product.domain.ProductSortingCriteria;
import com.escobar.itx_2.product.domain.primitives.ProductPrimitive;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class ProductSortingCriteriaTest {

    @Test
    void sales_units_returns_sales_as_score() {
        Product p = Product.fromPrimitive(
                new ProductPrimitive("1","A", 123, Map.of(ProductSize.SMALL, 0, ProductSize.MEDIUM, 0, ProductSize.LARGE, 0))
        );
        double score = ProductSortingCriteria.SALES_UNITS.calculateScore(p);
        assertEquals(123.0, score);
    }

    @Test
    void stock_ratio_is_fraction_of_sizes_with_stock() {
        Product p = Product.fromPrimitive(
                new ProductPrimitive("1","A", 0, Map.of(ProductSize.SMALL, 4, ProductSize.MEDIUM, 0, ProductSize.LARGE, 1))
        );
        double score = ProductSortingCriteria.STOCK_RATIO.calculateScore(p);
        assertEquals(2.0/3.0, score, 1e-9);
    }

    @Test
    void stock_ratio_when_no_sizes_returns_zero() {
        Product p = Product.fromPrimitive(
                new ProductPrimitive("1","A", 0, Map.of(ProductSize.SMALL, 0, ProductSize.MEDIUM, 0, ProductSize.LARGE, 0))
        );
        double score = ProductSortingCriteria.STOCK_RATIO.calculateScore(p);
        assertEquals(0.0, score, 1e-9);
    }

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
