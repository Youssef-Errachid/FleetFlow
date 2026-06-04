package org.fleetflow.fleetflow.service;

import org.fleetflow.fleetflow.dto.LivraisonDTO;
import org.fleetflow.fleetflow.entity.Chauffeur;
import org.fleetflow.fleetflow.entity.Livraison;
import org.fleetflow.fleetflow.entity.Vehicule;
import org.fleetflow.fleetflow.mapper.LivraisonMapper;
import org.fleetflow.fleetflow.repository.ChauffeurRepository;
import org.fleetflow.fleetflow.repository.LivraisonRepository;
import org.fleetflow.fleetflow.repository.VehiculeRepository;
import org.fleetflow.fleetflow.service.impl.LivraisonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class LivraisonServiceTest {

    @Mock
    LivraisonRepository livraisonRepository;
    @Mock
    LivraisonMapper livraisonMapper;
    @Mock
    ChauffeurRepository chauffeurRepository;
    @Mock
    VehiculeRepository vehiculeRepository;
    @InjectMocks
    LivraisonService livraisonService;
    @Test
//    void creerLivraison() {
//        LivraisonDTO livraisonDTOS=new LivraisonDTO( LocalDate.of(2026, 4, 15),"casa","oudeZem","EN_ATTENTE",1L,2L,3L);
//        Livraison livraisons=new Livraison();
//        livraisons.setId(1L);
//        livraisons.setDateLivraison(LocalDate.of(2026, 4, 15));
//        livraisons.setStatut("EN_ATTENTE");
//        Mockito.when(livraisonRepository.save(Mockito.any())).thenReturn(livraisons);
//        Mockito.when(livraisonMapper.toDTO(livraisons)).thenReturn(livraisonDTOS);
//        Mockito.when(livraisonMapper.toEntity(livraisonDTOS)).thenReturn(livraisons);
//
//        LivraisonDTO resultDto=livraisonService.creerLivraison(livraisonDTOS);
//        assertEquals("EN_ATTENTE",resultDto.getStatut());
//        Mockito.verify(livraisonRepository).save(Mockito.any());
//    }
    void creerLivraison(){
        LivraisonDTO dtos=new LivraisonDTO();
        dtos.setStatut("EN_ATTENTE");

        Livraison entity=new Livraison();
        entity.setStatut("EN_ATTENTE");

        Mockito.when(livraisonRepository.save(entity)).thenReturn(entity);
        Mockito.when(livraisonMapper.toDTO(entity)).thenReturn(dtos);
        Mockito.when(livraisonMapper.toEntity(dtos)).thenReturn(entity);

        LivraisonDTO result=livraisonService.creerLivraison(dtos);

        assertEquals("EN_ATTENTE",result.getStatut());
    }


    @Test
    void testAssignation() {

        Livraison livraison = new Livraison();
        Chauffeur chauffeur = new Chauffeur();
        Vehicule vehicule = new Vehicule();

        Mockito.when(livraisonRepository.findById(1L)).thenReturn(Optional.of(livraison));
        Mockito.when(chauffeurRepository.findById(2L)).thenReturn(Optional.of(chauffeur));
        Mockito.when(vehiculeRepository.findById(3L)).thenReturn(Optional.of(vehicule));
        Mockito.when(livraisonRepository.save(livraison)).thenReturn(livraison);

        LivraisonDTO dto = new LivraisonDTO();
        dto.setChauffeurId(2L);
        dto.setVehiculeId(3L);

        Mockito.when(livraisonMapper.toDTO(livraison)).thenReturn(dto);

        LivraisonDTO result = livraisonService.assignerChauffeurEtVehicule(1L, 2L, 3L);

        assertEquals(2L, result.getChauffeurId());
        assertEquals(3L, result.getVehiculeId());
    }

    @Test
    void testChangerStatut() {

        Livraison livraison = new Livraison();
        livraison.setStatut("EN_ATTENTE");

        Mockito.when(livraisonRepository.findById(1L)).thenReturn(Optional.of(livraison));
        Mockito.when(livraisonRepository.save(livraison)).thenReturn(livraison);

        LivraisonDTO dto = new LivraisonDTO();
        dto.setStatut("LIVREE");

        Mockito.when(livraisonMapper.toDTO(livraison)).thenReturn(dto);

        LivraisonDTO result = livraisonService.changerStatut(1L, "LIVREE");

        assertEquals("LIVREE", result.getStatut());
    }
}