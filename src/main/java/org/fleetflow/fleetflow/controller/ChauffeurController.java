package org.fleetflow.fleetflow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fleetflow.fleetflow.dto.ChauffeurDTO;
import org.fleetflow.fleetflow.service.ChauffeurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chauffeurs")
@RequiredArgsConstructor
@Tag(name = "Gestion des Chauffeurs")
public class ChauffeurController {

    private final ChauffeurService chauffeurService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Ajouter un chauffeur")
    public ResponseEntity<ChauffeurDTO> ajouter( @Valid @RequestBody ChauffeurDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chauffeurService.ajouterChauffeur(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un chauffeur")
    public ResponseEntity<ChauffeurDTO> modifier( @Valid @PathVariable Long id, @RequestBody ChauffeurDTO dto) {
        return ResponseEntity.ok(chauffeurService.modifierChauffeur(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un chauffeur")
    public ResponseEntity<Void> supprimer( @Valid @PathVariable Long id) {
        chauffeurService.supprimerChauffeur(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Lister tous les chauffeurs")
    public ResponseEntity<List<ChauffeurDTO>> lister() {
        return ResponseEntity.ok(chauffeurService.listerChauffeurs());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/disponibles")
    @Operation(summary = "Lister les chauffeurs disponibles")
    public ResponseEntity<List<ChauffeurDTO>> disponibles() {
        return ResponseEntity.ok(chauffeurService.listerChauffeursDisponibles());
    }
}
