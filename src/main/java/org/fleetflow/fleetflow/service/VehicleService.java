package org.fleetflow.fleetflow.service;


import org.fleetflow.fleetflow.dto.VehiculeDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VehicleService{
    VehiculeDTO ajouterVehicule(VehiculeDTO dto);
    VehiculeDTO modifierVehicule(Long id, VehiculeDTO dto);
    void supprimerVehicule(Long id);
    Page<VehiculeDTO> listerVehicules(int page, int size, String sortBy, String sortDir);
    List<VehiculeDTO> listerParStatut(String statut);
    List<VehiculeDTO> listerParCapaciteSuperieure(int capacite);


}