package com.escobar.itx_2.product.config;

import com.escobar.itx_2.product.domain.Product;
import com.escobar.itx_2.product.domain.ProductRepository;

import java.util.List;

public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> data;
    public InMemoryProductRepository(List<Product> data) { this.data = data; }
    @Override public List<Product> listProducts() { return data; }
}