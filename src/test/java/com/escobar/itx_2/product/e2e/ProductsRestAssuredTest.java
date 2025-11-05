package com.escobar.itx_2.product.e2e;

import com.escobar.itx_2.product.config.InMemoryProductRepository;
import com.escobar.itx_2.product.application.SortProduct;
import com.escobar.itx_2.product.domain.Product;
import com.escobar.itx_2.product.domain.ProductSize;
import com.escobar.itx_2.product.infrastructure.controller.ProductController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class ProductsRestAssuredTest {

    private static Product product(String id, String name, int sales, int s, int m, int l) {
        java.util.Map<ProductSize, Integer> stock = new java.util.EnumMap<>(ProductSize.class);
        stock.put(ProductSize.SMALL, s);
        stock.put(ProductSize.MEDIUM, m);
        stock.put(ProductSize.LARGE, l);
        return Product.fromPrimitive(new com.escobar.itx_2.product.domain.primitives.ProductPrimitive(
                id, name, sales, stock
        ));
    }

    private static java.util.List<Product> sampleProducts() {
        return java.util.List.of(
                product("1","V-NECH BASIC SHIRT",100,4,9,0),
                product("2","CONTRASTING FABRIC T-SHIRT",50,35,9,9),
                product("3","RAISED PRINT T-SHIRT",80,20,2,20),
                product("4","PLEATED T-SHIRT",3,25,30,10),
                product("5","CONTRASTING LACE T-SHIRT",650,0,1,0),
                product("6","SLOGAN T-SHIRT",20,9,2,5)
        );
    }

    @BeforeEach
    void setup() {
        var repo = new InMemoryProductRepository(sampleProducts());
        var service = new SortProduct(repo);
        RestAssuredMockMvc.standaloneSetup(new ProductController(service));
    }

    @Test
    void e2e_get_products_sorted_by_sales_and_stock() {
        given()
                .queryParam("salesUnits", 1.0)
                .queryParam("stockRatio", 1.0)
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("products.size()", greaterThan(0))
                .body("products[0].id", notNullValue());
    }

}
