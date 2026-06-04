package org.fleetflow.fleetflow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fleetflow.fleetflow.dto.ClientDTO;
import org.fleetflow.fleetflow.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Gestion des Clients")
public class ClientController {

    private final ClientService clientService;

    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @PostMapping
    @Operation(summary = "Ajouter un client")
    public ResponseEntity<ClientDTO> ajouter(@Valid @RequestBody ClientDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.ajouterClient(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un client")
    public ResponseEntity<ClientDTO> modifier(@Valid @PathVariable Long id, @RequestBody ClientDTO dto) {
        return ResponseEntity.ok(clientService.modifierClient(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un client")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        clientService.supprimerClient(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @GetMapping
    @Operation(summary = "Lister tous les clients")
    public ResponseEntity<Page<ClientDTO>> lister(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy , @RequestParam String sortDir) {
        return ResponseEntity.ok(clientService.listerClients(page,size,sortBy,sortDir));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un client par ID")
    public ResponseEntity<ClientDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }
}