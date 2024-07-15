package com.dnp.bank.dto;

import com.dnp.bank.entities.ProductDefinition;

import java.util.List;

public class ProductDefinitionWrapper {

    private List<ProductDefinition> definitions;

    public List<ProductDefinition> getDefinitions() {
        return definitions;
    }
    public void setDefinitions(List<ProductDefinition> definitions) {
        this.definitions = definitions;
    }
}
