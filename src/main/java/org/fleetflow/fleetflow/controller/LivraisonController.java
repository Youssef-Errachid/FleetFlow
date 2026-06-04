package org.fleetflow.fleetflow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fleetflow.fleetflow.dto.LivraisonDTO;
import org.fleetflow.fleetflow.service.LivraisonService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/livraisons")
@RequiredArgsConstructor
@Tag(name = "Gestion des Livraisons")
public class LivraisonController {

    private final LivraisonService livraisonService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    @Operation(summary = "Créer une livraison")
    public ResponseEntity<LivraisonDTO> creer(@Valid @RequestBody LivraisonDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livraisonService.creerLivraison(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{id}/assigner")
    @Operation(summary = "Assigner un chauffeur et un véhicule")
    public ResponseEntity<LivraisonDTO> assigner(
            @Valid
            @PathVariable Long id,
            @RequestParam Long chauffeurId,
            @RequestParam Long vehiculeId) {
        return ResponseEntity.ok(livraisonService.assignerChauffeurEtVehicule(id, chauffeurId, vehiculeId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PatchMapping("/{id}/statut")
    @Operation(summary = "Changer le statut d'une livraison")
    public ResponseEntity<LivraisonDTO> changerStatut(
            @Valid
            @PathVariable Long id,
            @RequestParam String statut) {
        return ResponseEntity.ok(livraisonService.changerStatut(id, statut));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping
    @Operation(summary = "Lister toutes les livraisons")
    public ResponseEntity<Page<LivraisonDTO>> lister(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy , @RequestParam String sortDir) {

        return ResponseEntity.ok(livraisonService.listerLivraisons(page,size,sortBy,sortDir));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/statut/{statut}")
    @Operation(summary = "Lister par statut")
    public ResponseEntity<List<LivraisonDTO>> parStatut(@PathVariable String statut) {
        return ResponseEntity.ok(livraisonService.listerParStatut(statut));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/client/{clientId}")
    @Operation(summary = "Lister par client")
    public ResponseEntity<List<LivraisonDTO>> parClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(livraisonService.listerParClient(clientId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/dates")
    @Operation(summary = "Lister entre deux dates")
    public ResponseEntity<List<LivraisonDTO>> entreDeuxDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(livraisonService.listerEntreDeuxDates(debut, fin));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/ville")
    @Operation(summary = "Lister par ville de destination")
    public ResponseEntity<List<LivraisonDTO>> parVille(@RequestParam String ville) {
        return ResponseEntity.ok(livraisonService.listerParVilleDestination(ville));
    }
}