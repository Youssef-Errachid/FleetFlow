package org.fleetflow.fleetflow.service;


import org.fleetflow.fleetflow.dto.ChauffeurDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChauffeurService{
    ChauffeurDTO ajouterChauffeur(ChauffeurDTO dto);
    ChauffeurDTO modifierChauffeur(Long id, ChauffeurDTO dto);
    void supprimerChauffeur(Long id);
    Page<ChauffeurDTO> listerChauffeurs(int page , int size , String sortBy, String sortDir);
    List<ChauffeurDTO> listerChauffeursDisponibles();
}