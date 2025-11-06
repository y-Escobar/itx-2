package com.escobar.itx_2.product.integration.repository;

import com.escobar.itx_2.product.domain.Product;
import com.escobar.itx_2.product.domain.ProductRead;
import com.escobar.itx_2.product.domain.ProductSize;
import com.escobar.itx_2.product.domain.primitives.ProductPrimitive;
import com.escobar.itx_2.product.infrastructure.entity.ProductEntity;
import com.escobar.itx_2.product.infrastructure.repository.MongoProductRepository;
import com.escobar.itx_2.product.infrastructure.repository.ProductDomainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataMongoTest
@Import(ProductDomainRepository.class)
class ProductDomainRepositoryTest {

    @Container
    static MongoDBContainer mongo = new MongoDBContainer("mongo:7.0.12");

    @DynamicPropertySource
    static void mongoProps(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", () -> mongo.getConnectionString() + "/itx2_test");
    }

    @Autowired
    MongoProductRepository mongoProductRepository;
    @Autowired
    ProductDomainRepository productDomainRepository;

    private static List<ProductEntity> sampleProducts() {
        return List.of(
                ProductEntity.fromDomain(new ProductPrimitive("1","V-NECH BASIC SHIRT", 100, Map.of(ProductSize.SMALL, 4, ProductSize.MEDIUM, 9, ProductSize.LARGE, 0))),
                ProductEntity.fromDomain(new ProductPrimitive("2","CONTRASTING FABRIC T-SHIRT", 50, Map.of(ProductSize.SMALL, 35, ProductSize.MEDIUM, 9, ProductSize.LARGE, 9))),
                ProductEntity.fromDomain(new ProductPrimitive("3","RAISED PRINT T-SHIRT", 80, Map.of(ProductSize.SMALL, 20, ProductSize.MEDIUM, 2, ProductSize.LARGE, 20))),
                ProductEntity.fromDomain(new ProductPrimitive("4","PLEATED T-SHIRT", 3, Map.of(ProductSize.SMALL, 25, ProductSize.MEDIUM, 30, ProductSize.LARGE, 10))),
                ProductEntity.fromDomain(new ProductPrimitive("5","CONTRASTING LACE T-SHIRT", 650, Map.of(ProductSize.SMALL, 0, ProductSize.MEDIUM, 1, ProductSize.LARGE, 0))),
                ProductEntity.fromDomain(new ProductPrimitive("6","SLOGAN T-SHIRT", 20, Map.of(ProductSize.SMALL, 9, ProductSize.MEDIUM, 2, ProductSize.LARGE, 5)))
        );
    }

    @BeforeEach
    void clean() {
        mongoProductRepository.deleteAll();
    }

    @Test
    @DisplayName("listProducts returns the products mapped to domain, and we check the first one mapping to ProductRead.")
    void listProducts_returns_domain_models() {
        mongoProductRepository.saveAll(sampleProducts());

        List<Product> products = productDomainRepository.listProducts();
        assertThat(products).hasSize(6);

        ProductRead first = products.getFirst().toProductRead();
        assertThat(first.id()).isEqualTo("1");
        assertThat(first.name()).isEqualTo("V-NECH BASIC SHIRT");
        assertThat(first.salesUnits()).isEqualTo(100);
        assertThat(first.stock().get(ProductSize.SMALL)).isEqualTo(4);
        assertThat(first.stock().get(ProductSize.MEDIUM)).isEqualTo(9);
        assertThat(first.stock().get(ProductSize.LARGE)).isEqualTo(0);
    }
}
