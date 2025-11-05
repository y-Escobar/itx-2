package com.escobar.itx_2.product.infrastructure.repository;

import com.escobar.itx_2.product.domain.Product;
import com.escobar.itx_2.product.domain.ProductRepository;
import com.escobar.itx_2.product.infrastructure.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDomainRepository implements ProductRepository {

    private final MongoProductRepository mongoProductRepository;

    public ProductDomainRepository(MongoProductRepository mongoProductRepository) {
        this.mongoProductRepository = mongoProductRepository;
    }

    @Override
    public List<Product> listProducts() {
        return mongoProductRepository.findAll().stream().map(ProductEntity::toDomain).toList();
    }
}