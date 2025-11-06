package com.escobar.itx_2.product.application;

import com.escobar.itx_2.product.domain.Product;
import com.escobar.itx_2.product.domain.ProductRead;
import com.escobar.itx_2.product.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class ListProducts {

    private final ProductRepository productRepository;

    public ListProducts(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public final List<ProductRead> execute(Map<String, Double> weights){

        List<Product> products = productRepository.listProducts().stream().sorted(
                Comparator.comparingDouble((Product p) -> -p.scoreOf(weights)).thenComparing(Product::getNameValue)
        ).toList();

        return products.stream().map(Product::toProductRead).toList();
    }

}
