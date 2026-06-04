package org.fleetflow.fleetflow.service;


import org.fleetflow.fleetflow.dto.LivraisonDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface LivraisonService{
    LivraisonDTO creerLivraison(LivraisonDTO dto);
    LivraisonDTO assignerChauffeurEtVehicule(Long id, Long chauffeurId, Long vehiculeId);
    LivraisonDTO changerStatut(Long id, String statut);
    Page<LivraisonDTO> listerLivraisons(int page, int size, String sortBy, String sortDir);
    List<LivraisonDTO> listerParClient(Long clientId);
    List<LivraisonDTO> listerEntreDeuxDates(LocalDate debut, LocalDate fin);
    List<LivraisonDTO> listerParVilleDestination(String ville);
    List<LivraisonDTO> listerParStatut(String statut);
}