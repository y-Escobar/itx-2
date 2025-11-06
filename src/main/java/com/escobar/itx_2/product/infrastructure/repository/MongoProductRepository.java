package com.escobar.itx_2.product.infrastructure.repository;

import com.escobar.itx_2.product.infrastructure.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoProductRepository extends MongoRepository<ProductEntity, String> {}