package com.escobar.itx_2.product.infrastructure.controller;

import com.escobar.itx_2.product.application.ListProducts;
import com.escobar.itx_2.product.infrastructure.dto.ProductSortingWeights;
import com.escobar.itx_2.product.infrastructure.dto.ListProductsResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ListProducts listProducts;

    public ProductController(ListProducts sortProducts){
        this.listProducts = sortProducts;
    }

    @Operation(
            summary = "Listar productos ordenados",
            description = "Ordena por pesos de ventas y ratio de stock"
    )
    @GetMapping
    public ListProductsResponseDTO list(@ParameterObject @ModelAttribute ProductSortingWeights weights) {
        return new ListProductsResponseDTO(listProducts.execute(weights.asMap()));
    }

}