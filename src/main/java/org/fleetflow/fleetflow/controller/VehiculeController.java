package org.fleetflow.fleetflow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fleetflow.fleetflow.dto.VehiculeDTO;
import org.fleetflow.fleetflow.service.VehiculeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
@Tag(name = "Gestion des Véhicules")
public class VehiculeController {

    private final VehiculeService vehiculeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Ajouter un véhicule")
    public ResponseEntity<VehiculeDTO> ajouter(@Valid @RequestBody VehiculeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculeService.ajouterVehicule(dto));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un véhicule")
    public ResponseEntity<VehiculeDTO> modifier(@Valid  @PathVariable Long id,@RequestBody VehiculeDTO dto) {
        return ResponseEntity.ok(vehiculeService.modifierVehicule(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un véhicule")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        vehiculeService.supprimerVehicule(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping
    @Operation(summary = "Lister tous les véhicules")
    public ResponseEntity<Page<VehiculeDTO>> lister(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy , @RequestParam String sortDir) {
        return ResponseEntity.ok(vehiculeService.listerVehicules(page,size,sortBy,sortDir));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/statut/{statut}")
    @Operation(summary = "Lister par statut")
    public ResponseEntity<List<VehiculeDTO>> parStatut(@PathVariable String statut) {
        return ResponseEntity.ok(vehiculeService.listerParStatut(statut));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/capacite")
    @Operation(summary = "Lister par capacité minimale")
    public ResponseEntity<List<VehiculeDTO>> parCapacite( @RequestParam int min) {
        return ResponseEntity.ok(vehiculeService.listerParCapaciteSuperieure(min));
    }
}