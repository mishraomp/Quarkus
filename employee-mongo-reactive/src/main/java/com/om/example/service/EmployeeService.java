package com.om.example.service;

import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;
import com.om.example.entity.AddressEntity;
import com.om.example.entity.EmployeeEntity;
import com.om.example.repository.EmployeeRepo;
import io.quarkus.mongodb.FindOptions;
import lombok.val;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@ApplicationScoped
public class EmployeeService {
    @Inject
    EmployeeRepo repo;

    public CompletionStage<Optional<EmployeeEntity>> getEmployee(final Integer employeeId) {
        final Document mongoDocument = new Document();
        mongoDocument.append("employeeId", employeeId);
        return repo.getCollection().find(mongoDocument, EmployeeEntity.class).findFirst().run();
    }

    public CompletionStage<List<EmployeeEntity>> getEmployees() {

        return repo.getCollection().find().map(this::mapEmployeeEntityFromBson).toList().run();
    }

    private EmployeeEntity mapEmployeeEntityFromBson(final Document document) {
        System.out.println(document);
        if (document == null) {
            return null;
        }
        val addresses = document.getList("addressList", Document.class, new ArrayList<>());
        return EmployeeEntity.builder()
                .employeeId(document.getInteger("employeeId"))
                .firstName(document.getString("firstName"))
                .lastName(document.getString("lastName"))
                .age(document.getInteger("age"))
                .salary(document.getInteger("salary"))
                .addressList(buildAddressesFromDocuments(addresses)).build();
    }

    private List<AddressEntity> buildAddressesFromDocuments(final List<Document> addresses) {
        return addresses.stream().map(doc -> AddressEntity.builder()
                .addressType(doc.getString("addressType"))
                .addressLine(doc.getString("addressLine"))
                .city(doc.getString("city"))
                .state(doc.getString("state"))
                .country(doc.getString("country"))
                .zip(doc.getString("zip")).build()).collect(Collectors.toList());
    }

    @Transactional(Transactional.TxType.MANDATORY)
    public CompletionStage<Void> addEmployee(final EmployeeEntity entity) {
        Document document = new Document()
                .append("employeeId", entity.getEmployeeId())
                .append("firstName", entity.getFirstName())
                .append("lastName", entity.getLastName())
                .append("age", entity.getAge())
                .append("salary", entity.getSalary())
                .append("addressList", entity.getAddressList());
        return repo.getCollection().insertOne(document);
    }

    @Transactional(Transactional.TxType.MANDATORY)
    public CompletionStage<EmployeeEntity> updateEmployee(final Integer employeeId, final EmployeeEntity entity) {
        Document document = new Document()
                .append("firstName", entity.getFirstName())
                .append("lastName", entity.getLastName())
                .append("age", entity.getAge())
                .append("salary", entity.getSalary())
                .append("addressList", entity.getAddressList());
        final Document mongoDocument = new Document();
        mongoDocument.append("employeeId", employeeId);
        return repo.getCollection().findOneAndUpdate(mongoDocument, document).thenApplyAsync(this::mapEmployeeEntityFromBson);
    }

    public CompletionStage<EmployeeEntity> deleteEmployee(final Integer employeeId) {
        final Document mongoDocument = new Document();
        mongoDocument.append("employeeId", employeeId);
        return repo.getCollection().findOneAndDelete(mongoDocument).thenApplyAsync(this::mapEmployeeEntityFromBson);
    }
}
