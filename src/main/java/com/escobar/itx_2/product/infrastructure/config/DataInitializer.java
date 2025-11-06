package com.escobar.itx_2.product.infrastructure.config;

import com.escobar.itx_2.product.domain.ProductSize;
import com.escobar.itx_2.product.domain.primitives.ProductPrimitive;
import com.escobar.itx_2.product.infrastructure.entity.ProductEntity;
import com.escobar.itx_2.product.infrastructure.repository.MongoProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
@Profile({"dev", "test"})
public class DataInitializer implements CommandLineRunner {

    private final MongoProductRepository mongoProductRepository;

    private final List<String> productIds = List.of(
            "4945ee60-ea5d-4466-b9eb-0c97bcf9e959",
            "4bd92219-9f51-4812-8512-ee512003dc70",
            "3caa1263-2d6f-4fc0-99c0-ad213b1e8097",
            "9ce84648-5c5c-45b4-bbda-9a23f03a7df7",
            "3d21ffa2-1abc-4e5a-ba95-515773330cf0",
            "b7d2f0d5-c7aa-4fff-8bfe-c87a2898e4f0"
    );

    public DataInitializer(MongoProductRepository mongoProductRepository) {
        this.mongoProductRepository = mongoProductRepository;
    }

    @Override
    public void run(String... args) {

        //log.info("Executing DataInitializer...");
        mongoProductRepository.deleteAllById(productIds);
        //log.info("Deleting previous entries");

        List<ProductEntity> initialProducts = getProductEntities();
        //log.info("Saving Product list");
        mongoProductRepository.saveAll(initialProducts);
        //log.info("Saved Product list");
    }

    private List<ProductEntity> getProductEntities() {
        int i = 0;
        return List.of(
            ProductEntity.fromDomain(new ProductPrimitive(productIds.get(i++), "V-NECH BASIC SHIRT", 100, Map.of(ProductSize.SMALL, 4, ProductSize.MEDIUM, 9, ProductSize.LARGE, 0))),
            ProductEntity.fromDomain(new ProductPrimitive(productIds.get(i++), "CONTRASTING FABRIC T-SHIRT", 50, Map.of(ProductSize.SMALL, 35, ProductSize.MEDIUM, 9, ProductSize.LARGE, 9))),
            ProductEntity.fromDomain(new ProductPrimitive(productIds.get(i++), "RAISED PRINT T-SHIRT", 80, Map.of(ProductSize.SMALL, 20, ProductSize.MEDIUM, 2, ProductSize.LARGE, 20))),
            ProductEntity.fromDomain(new ProductPrimitive(productIds.get(i++), "PLEATED T-SHIRT", 3, Map.of(ProductSize.SMALL, 25, ProductSize.MEDIUM, 30, ProductSize.LARGE, 10))),
            ProductEntity.fromDomain(new ProductPrimitive(productIds.get(i++), "CONTRASTING LACE T-SHIRT", 650, Map.of(ProductSize.SMALL, 0, ProductSize.MEDIUM, 1, ProductSize.LARGE, 0))),
            ProductEntity.fromDomain(new ProductPrimitive(productIds.get(i), "SLOGAN T-SHIRT", 20, Map.of(ProductSize.SMALL, 9, ProductSize.MEDIUM, 2, ProductSize.LARGE, 5)))
        );
    }
}
