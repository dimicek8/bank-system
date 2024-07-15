package com.dnp.bank.services;

import com.dnp.bank.entities.ProductDefinition;
import com.dnp.bank.exceptions.InvalidRateException;
import com.dnp.bank.repositories.ProductDefinitionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class ProductDefinitionService {

    @Autowired
    private ProductDefinitionRepository productDefinitionRepository;

    public List<ProductDefinition> ratesUpdates(List<ProductDefinition> productDefinitions) {
        for (ProductDefinition definition : productDefinitions) {
            if ("N".equals(definition.getOperation())) {
                if (rateValidation(definition, definition.getRate())) {
                productDefinitionRepository.save(definition);
                } else {
                    throw new InvalidRateException("Neplatná sazba pro nový produkt!");
                }
            } else if ("U".equals(definition.getOperation())) {
                Optional<ProductDefinition> existingDefinition = productDefinitionRepository.findByProductKey(definition.getProductKey());
                if (existingDefinition.isPresent()) {
                    ProductDefinition updatedDefinition = existingDefinition.get();
                    BigDecimal originalRate = updatedDefinition.getRate();
                    updatedDefinition.setRate(definition.getRate());
                    updatedDefinition.setPayRate(definition.getPayRate());
                    if (rateValidation(definition, originalRate)) {
                    productDefinitionRepository.save(updatedDefinition);
                    } else {
                        throw new InvalidRateException("Neplatná sazba pro aktualizovaný produkt!");
                    }
                }
            }
        }
        return productDefinitions;
    }

    public boolean rateValidation(ProductDefinition definition, BigDecimal originalRate) {
        BigDecimal rate = definition.getRate();
        if ("ACCOUNT".equals(definition.getType())) {
            return rate.compareTo(originalRate.subtract(new BigDecimal("250"))) >= 0
                    && rate.compareTo(originalRate.add(new BigDecimal("250"))) <= 0
                    && rate.compareTo(BigDecimal.ZERO) >= 0;
        } else if ("LOAN".equals(definition.getType())) {
            BigDecimal maxRate = originalRate.multiply(new BigDecimal("1.2"));
            return rate.compareTo(BigDecimal.ZERO) >= 0 && rate.compareTo(maxRate) <= 0;
        }
        throw new InvalidRateException("Sazba nesmí být vyšší než 250Kč nebo 1/5 původní sazby!");
    }

    public List<ProductDefinition> getAllProductDefinitions() {
        return productDefinitionRepository.findAll();
    }
    public Optional<ProductDefinition> getProductDefinitionByProductKey(String productKey) {
        return productDefinitionRepository.findByProductKey(productKey);
    }
    public ProductDefinition saveProductDefinition(ProductDefinition productDefinition) {
        return productDefinitionRepository.save(productDefinition);
    }

    @Transactional
    public void deleteProductDefinitionByProductKey(String productKey) {
        productDefinitionRepository.deleteByProductKey(productKey);
    }
}