package com.dnp.bank.repositories;

import com.dnp.bank.entities.ProductDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Optional;

public interface ProductDefinitionRepository extends JpaRepository<ProductDefinition, String> {
    Optional<ProductDefinition> findByProductKey(String productKey);
    void deleteByProductKey(String productKey);
}
