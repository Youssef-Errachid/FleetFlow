package org.fleetflow.fleetflow.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.fleetflow.fleetflow.dto.ChauffeurDTO;
import org.fleetflow.fleetflow.entity.Chauffeur;
import org.fleetflow.fleetflow.mapper.ChauffeurMapper;
import org.fleetflow.fleetflow.repository.ChauffeurRepository;
import org.fleetflow.fleetflow.repository.LivraisonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChauffeurService implements org.fleetflow.fleetflow.service.ChauffeurService {

    private final ChauffeurRepository chauffeurRepository;
    private final ChauffeurMapper chauffeurMapper;
    private final LivraisonRepository livraisonRepository;

    public ChauffeurDTO ajouterChauffeur(ChauffeurDTO dto) {
        Chauffeur chauffeur = chauffeurMapper.toEntity(dto);
        return chauffeurMapper.toDTO(chauffeurRepository.save(chauffeur));
    }


    public ChauffeurDTO modifierChauffeur(Long id, ChauffeurDTO dto) {
        Chauffeur chauffeur = chauffeurRepository.findById(id).orElse(null);
        chauffeurMapper.updateEntityFromDTO(dto, chauffeur);
        return chauffeurMapper.toDTO(chauffeurRepository.save(chauffeur));
    }

    public void supprimerChauffeur(Long id) {
        if (!chauffeurRepository.existsById(id)) {
            throw new EntityNotFoundException("Chauffeur introuvable avec l'id : " + id);
        }
        chauffeurRepository.deleteById(id);
    }

    public Page<ChauffeurDTO> listerChauffeurs(int page , int size ,String sortBy,String sortDir){

        List<String> allowedSortField = List.of("id","nom","telephone","ville");

        if(!allowedSortField.contains(sortBy)){
            sortBy = "id";
        }
        boolean isAsc = "asc".equalsIgnoreCase(sortDir);

        Sort sort = isAsc
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return chauffeurRepository.findAll(pageable).map(chauffeurMapper::toDTO);
    }

public List<ChauffeurDTO> listerChauffeursDisponibles(){
        return chauffeurMapper.toDtoList(chauffeurRepository.findByAvailableTrue());
}

}