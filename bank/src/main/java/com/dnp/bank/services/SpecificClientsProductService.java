package com.dnp.bank.services;

import com.dnp.bank.entities.SpecificClientsProduct;
import com.dnp.bank.repositories.SpecificClientsProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecificClientsProductService {
    @Autowired
    private SpecificClientsProductRepository specificClientsProductRepository;

    public List<SpecificClientsProduct> getAllSpecificClientsProducts() {
        return specificClientsProductRepository.findAll();
    }
    public Optional<SpecificClientsProduct> getSpecificClientsProduct(Long id) {
        return specificClientsProductRepository.findById(id);
    }
    public SpecificClientsProduct saveSpecificClientsProduct(SpecificClientsProduct specificClientsProduct) {
        return specificClientsProductRepository.save(specificClientsProduct);
    }
    public void deleteSpecificClientsProduct(Long id) {
        specificClientsProductRepository.deleteById(id);
    }
}
