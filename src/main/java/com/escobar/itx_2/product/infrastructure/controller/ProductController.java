package com.escobar.itx_2.product.infrastructure.controller;

import com.escobar.itx_2.product.application.ListProducts;
import com.escobar.itx_2.product.infrastructure.dto.ProductSortingWeights;
import com.escobar.itx_2.product.infrastructure.dto.ListProductsResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ListProducts sortProducts;

    public ProductController(ListProducts sortProducts){
        this.sortProducts = sortProducts;
    }

    @GetMapping
    public ListProductsResponseDTO list(@ModelAttribute ProductSortingWeights weights) {
        return new ListProductsResponseDTO(sortProducts.execute(weights.asMap()));
    }

}