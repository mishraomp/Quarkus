package com.om.example.repository;

import io.quarkus.mongodb.ReactiveMongoClient;
import io.quarkus.mongodb.ReactiveMongoCollection;
import lombok.Getter;
import org.bson.Document;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EmployeeRepo {
    @Inject
    @Getter
    ReactiveMongoClient mongoClient;
    @ConfigProperty(name = "mongodb.databaseName")
    String databaseName;
    @ConfigProperty(name = "mongodb.collectionName")
    String collectionName;

    public ReactiveMongoCollection<Document> getCollection() {
        return mongoClient.getDatabase(databaseName).getCollection(collectionName);
    }
}
