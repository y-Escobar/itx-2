package com.escobar.itx_2.product.application;

import com.escobar.itx_2.product.domain.Product;
import com.escobar.itx_2.product.domain.ProductRead;
import com.escobar.itx_2.product.domain.ProductRepository;
import com.escobar.itx_2.product.domain.ProductSortingWeights;
import com.escobar.itx_2.product.infrastructure.repository.ProductDomainRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class SortProduct {

    private final ProductRepository productRepository;

    public SortProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public final List<ProductRead> execute(ProductSortingWeights weights){
        Map<String, Double> mappedWeights = weights.asMap();

        List<Product> products = productRepository.listProducts().stream().sorted(
                Comparator.comparingDouble((Product p) -> p.scoreOf(mappedWeights)).reversed().thenComparing(Product::getNameValue)
        ).toList();

        return products.stream().map(Product::toProductRead).toList();
    }

}
