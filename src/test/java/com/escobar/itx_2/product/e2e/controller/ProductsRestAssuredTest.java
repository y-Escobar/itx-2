package com.escobar.itx_2.product.e2e.controller;

import com.escobar.itx_2.product.config.InMemoryProductRepository;
import com.escobar.itx_2.product.application.ListProducts;
import com.escobar.itx_2.product.domain.Product;
import com.escobar.itx_2.product.domain.ProductSize;
import com.escobar.itx_2.product.domain.primitives.ProductPrimitive;
import com.escobar.itx_2.product.infrastructure.controller.ProductController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;

public class ProductsRestAssuredTest {
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

    @BeforeEach
    void setup() {
        var repo = new InMemoryProductRepository(sampleProducts());
        var service = new ListProducts(repo);
        RestAssuredMockMvc.standaloneSetup(new ProductController(service));
    }

    @Test
    @DisplayName("listProducts returns products according to sorting criteria in which both have the same weight.")
    void e2e_get_products_sorted_by_sales_and_stock() {
        given()
                .queryParam("salesUnits", 1.0)
                .queryParam("stockRatio", 1.0)
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("products", hasSize(6))
                .body("products.id", contains("5","1","3","2","6","4"));
    }

    @Test
    @DisplayName("listProducts returns products according to sorting criteria where StockRatio has the most weight.")
    void e2e_get_products_sorted_by_sales_and_stock2() {
        given()
                .queryParam("salesUnits", 0.01)
                .queryParam("stockRatio", 100.0)
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("products", hasSize(6))
                .body("products.id", contains("3","2","6","4","1","5"));
    }

    @Test
    @DisplayName("listProducts returns products according to sorting criteria, ignoring unknown criteria.")
    void e2e_get_products_sorted_by_sales_and_stock3() {
        given()
                .queryParam("salesUnits", 1.0)
                .queryParam("stockRatio", 1.0)
                .queryParam("randomCriteria", 1.0)
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("products", hasSize(6))
                .body("products.id", contains("5","1","3","2","6","4"));
    }

}
