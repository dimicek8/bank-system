package com.dnp.bank.controllers;

import com.dnp.bank.entities.Client;
import com.dnp.bank.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
@Tag(name = "Klient", description = "Informace o klientovi")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    @Operation(summary = "Seznam klientů", description = "Seznam všech klientů")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }
    @GetMapping("/{id}")
    @Operation(summary = "ID klientů", description = "Hledání klientů podle ID")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> client = clientService.getClientById(id);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    @Operation(summary = "Nový klient", description = "Přidání nového klienta")
    public ResponseEntity<Client> createNewClient(@RequestBody Client client) {
        Client savedClient = clientService.saveClient(client);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Aktualizace klienta", description = "Změna informací klienta")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client clientDetails) {
        Optional<Client> client = clientService.getClientById(id);
        if (client.isPresent()) {
            Client updateClient = client.get();
            updateClient.setName(clientDetails.getName());
            updateClient.setEmail(clientDetails.getEmail());
            clientService.saveClient(updateClient);
            return ResponseEntity.ok(updateClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Odstranění klienta", description = "Odstranění klienta ze seznamu")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
