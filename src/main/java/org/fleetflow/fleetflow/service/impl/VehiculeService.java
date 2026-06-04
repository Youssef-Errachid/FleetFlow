package org.fleetflow.fleetflow.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.fleetflow.fleetflow.dto.VehiculeDTO;
import org.fleetflow.fleetflow.entity.Vehicule;
import org.fleetflow.fleetflow.mapper.VehiculeMapper;
import org.fleetflow.fleetflow.repository.VehiculeRepository;
import org.fleetflow.fleetflow.service.VehicleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeService implements VehicleService {

    private final VehiculeRepository vehiculeRepository;
    private final VehiculeMapper vehiculeMapper;

    public VehiculeDTO ajouterVehicule(VehiculeDTO dto) {
        Vehicule vehicule = vehiculeMapper.toEntity(dto);
        return vehiculeMapper.toDTO(vehiculeRepository.save(vehicule));
    }

    public VehiculeDTO modifierVehicule(Long id, VehiculeDTO dto) {
        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Véhicule introuvable avec l'id : " + id)
                );
        vehiculeMapper.updateEntityFromDTO(dto, vehicule);
        return vehiculeMapper.toDTO(vehiculeRepository.save(vehicule));
    }


    public void supprimerVehicule(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new EntityNotFoundException("Véhicule introuvable avec l'id : " + id);
        }
        vehiculeRepository.deleteById(id);
    }

    public Page<VehiculeDTO> listerVehicules(int page, int size, String sortBy, String sortDir){
        List<String> allowedSortField = List.of("id","disponible","matricule");
        if(!allowedSortField.contains(sortBy)){
            sortBy = "id";
        }

        Boolean isAsc  = "asc".equalsIgnoreCase(sortDir);

        Sort.Direction sortDirection =  isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page,size,Sort.by(sortDirection,sortBy));
        return vehiculeRepository.findAll(pageable).map(vehiculeMapper::toDTO);
    }

public List<VehiculeDTO> listerParStatut(String statut){
        return vehiculeMapper.toDtoList(vehiculeRepository.findByStatutVehicule(statut));
}

public List<VehiculeDTO> listerParCapaciteSuperieure(int capacite){
        return vehiculeMapper.toDtoList(vehiculeRepository.findByCapaciteGreaterThan(capacite));
}
}