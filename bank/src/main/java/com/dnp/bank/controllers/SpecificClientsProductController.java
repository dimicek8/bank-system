package com.dnp.bank.controllers;

import com.dnp.bank.entities.SpecificClientsProduct;
import com.dnp.bank.services.SpecificClientsProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client-products")
@Tag(name = "Konkrétní produkt", description = "Popis a specifikace kokrétního produktu")
public class SpecificClientsProductController {

    @Autowired
    private SpecificClientsProductService specificClientsProductService;

    @GetMapping
    @Operation(summary = "Seznam produktů", description = "Seznam všech konkrétních produktů")
    public List<SpecificClientsProduct> getAllSpecificClientsProducts() {
        return specificClientsProductService.getAllSpecificClientsProducts();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Produkt podle ID", description = "Konkrétní produkt podle ID")
    public ResponseEntity<SpecificClientsProduct> getSpecificClientsProductById(@PathVariable Long id) {
        Optional<SpecificClientsProduct> specificClientsProduct = specificClientsProductService.getSpecificClientsProduct(id);
        return specificClientsProduct.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    @Operation(summary = "Nový produkt", description = "Vytvoření a uložení nového produktu")
    public ResponseEntity<SpecificClientsProduct> createSpecificClientsProduct(@RequestBody SpecificClientsProduct specificClientsProduct) {
        SpecificClientsProduct savedSpecificClientsProduct = specificClientsProductService.saveSpecificClientsProduct(specificClientsProduct);
        return new ResponseEntity<>(savedSpecificClientsProduct, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Úprava produktu", description = "Úprava a uložení změn produktu podle ID")
    public ResponseEntity<SpecificClientsProduct> updateSpecificClientsProduct(@PathVariable Long id, @RequestBody SpecificClientsProduct specificClientsProductDetails) {
        Optional<SpecificClientsProduct> specificClientsProduct = specificClientsProductService.getSpecificClientsProduct(id);
        if (specificClientsProduct.isPresent()) {
            SpecificClientsProduct updateSpecificClientsProduct = specificClientsProduct.get();
            updateSpecificClientsProduct.setClientsId(specificClientsProductDetails.getClientsId());
            updateSpecificClientsProduct.setProductKey(specificClientsProductDetails.getProductKey());
            updateSpecificClientsProduct.setClientsRate(specificClientsProductDetails.getClientsRate());
            updateSpecificClientsProduct.setClientsContractDate(specificClientsProductDetails.getClientsContractDate());
            specificClientsProductService.saveSpecificClientsProduct(updateSpecificClientsProduct);
            return ResponseEntity.ok(updateSpecificClientsProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Odstranění produktu", description = "Odstranění produktu podle ID")
    public ResponseEntity<Void> deleteSpecificClientsProduct(@PathVariable Long id) {
        specificClientsProductService.deleteSpecificClientsProduct(id);
        return ResponseEntity.noContent().build();
    }
}
