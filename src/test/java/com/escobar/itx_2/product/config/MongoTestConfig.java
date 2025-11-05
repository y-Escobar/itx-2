package com.escobar.itx_2.product.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.testcontainers.containers.MongoDBContainer;

@TestConfiguration
public class MongoTestConfig {

    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

    static {
        mongoDBContainer.start();
        System.setProperty("MONGO_URI", mongoDBContainer.getReplicaSetUrl());
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(System.getProperty("MONGO_URI")));
    }
}

