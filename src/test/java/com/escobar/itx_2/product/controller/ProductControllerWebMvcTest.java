package com.escobar.itx_2.product.infrastructure.controller;

import com.escobar.itx_2.product.application.SortProduct;
import com.escobar.itx_2.product.domain.ProductRead;
import com.escobar.itx_2.product.domain.ProductSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerWebMvcTest {
    @Autowired MockMvc mvc;
    @MockitoBean SortProduct sortProduct;

    @Test
    @DisplayName("GET /products maps weights from query params and returns list")
    void list_returns_sorted_products() throws Exception {
        var p = new ProductRead("1","A", 10, Map.of(ProductSize.SMALL, 1));

        Mockito.when(sortProduct.execute(Mockito.any())).thenReturn(List.of(p));

        mvc.perform(get("/products")
                        .queryParam("salesUnits", "1.0")
                        .queryParam("stockRatio", "2.0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products", hasSize(1)))
                .andExpect(jsonPath("$.products[0].id", is("1")))
                .andExpect(jsonPath("$.products[0].name", is("A")));
    }
}
