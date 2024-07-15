package com.dnp.bank.controllers;


import com.dnp.bank.dto.ProductDefinitionWrapper;
import com.dnp.bank.entities.ProductDefinition;
import com.dnp.bank.services.ProductDefinitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product-definition")
@Tag(name = "", description = "Specifikace produktu")
public class ProductDefinitionController {

    @Autowired
    private ProductDefinitionService productDefinitionService;

    @GetMapping
    @Operation(summary = "Všechny specifikace", description = "Seznam všech dostupných specifikací produktu")
    public List<ProductDefinition> getAllProductDefinitions() {
        return productDefinitionService.getAllProductDefinitions();
    }

    @GetMapping("/{productKey}")
    @Operation(summary = "Specifikace podle klíče", description = "Získání specifikace produktu podle jeho klíče")
    public ResponseEntity<ProductDefinition> getProductDefinitionByProductKey(@PathVariable String productKey) {
        Optional<ProductDefinition> productDefinition = productDefinitionService.getProductDefinitionByProductKey(productKey);
        return productDefinition.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Zpracování obalovací třídy", description = "Příjme JSON, uloží změny a vrátí HTTP odpověď")
    public ResponseEntity<List<ProductDefinition>> handleProductDefinition(@RequestBody ProductDefinitionWrapper wrapper) {
        List<ProductDefinition> savedDefinitions = productDefinitionService.ratesUpdates(wrapper.getDefinitions());
        return new ResponseEntity<>(savedDefinitions, HttpStatus.CREATED);
    }

    @PutMapping("/{productKey}")
    @Operation(summary = "Úprava podle klíče", description = "Úprava specifikací produktu podle klíče")
    public ResponseEntity<ProductDefinition> updateProductDefinition(@PathVariable String productKey, @RequestBody ProductDefinition productDefinitionDetails) {
        Optional<ProductDefinition> productDefinition = productDefinitionService.getProductDefinitionByProductKey(productKey);
        if (productDefinition.isPresent()) {
            ProductDefinition updateProductDefinition = productDefinition.get();
            BigDecimal originalRate = updateProductDefinition.getRate();
            updateProductDefinition.setDescription(productDefinitionDetails.getDescription());
            updateProductDefinition.setType(productDefinitionDetails.getType());
            updateProductDefinition.setRate(productDefinitionDetails.getRate());
            updateProductDefinition.setPayRate(productDefinitionDetails.getPayRate());
            if (productDefinitionService.rateValidation(updateProductDefinition, originalRate)) {
            productDefinitionService.saveProductDefinition(updateProductDefinition);
            return ResponseEntity.ok(updateProductDefinition);
            } else {
                return ResponseEntity.badRequest().body(updateProductDefinition);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{productKey}")
    @Operation(summary = "Smazání podle klíče", description = "Smaže celý definovaný produkt na základě klíče")
    public ResponseEntity<String> deleteProductDefinition(@PathVariable String productKey) {
        productDefinitionService.deleteProductDefinitionByProductKey(productKey);
        return ResponseEntity.ok("Úspěšně smazáno!");
    }
}
