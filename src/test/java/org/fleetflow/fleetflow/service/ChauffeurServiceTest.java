package org.fleetflow.fleetflow.service;

import org.fleetflow.fleetflow.dto.ChauffeurDTO;
import org.fleetflow.fleetflow.entity.Chauffeur;
import org.fleetflow.fleetflow.mapper.ChauffeurMapper;
import org.fleetflow.fleetflow.repository.ChauffeurRepository;
import org.fleetflow.fleetflow.service.impl.ChauffeurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChauffeurServiceTest {

    @Mock
    private ChauffeurRepository chauffeurRepository;

    @Mock
    private ChauffeurMapper chauffeurMapper;

    @InjectMocks
    private ChauffeurService chauffeurService;

    private Chauffeur chauffeur;
    private ChauffeurDTO chauffeurDTO;

    @BeforeEach
    void setUp() {
        chauffeur = Chauffeur.builder()
                .id(1L)
                .nom("Ali")
                .telephone("0600000000")
                .licenseType("B")
                .available(true)
                .build();

        chauffeurDTO = ChauffeurDTO.builder()
                .nom("Ali")
                .telephone("0600000000")
                .licenseType("B")
                .available(true)
                .build();
    }

    @Test
    void testAjouterChauffeur(){

        when(chauffeurMapper.toEntity(chauffeurDTO)).thenReturn(chauffeur);
        when(chauffeurRepository.save(chauffeur)).thenReturn(chauffeur);
        when(chauffeurMapper.toDTO(chauffeur)).thenReturn(chauffeurDTO);

        ChauffeurDTO result = chauffeurService.ajouterChauffeur(chauffeurDTO);

        assertNotNull(result);

        verify(chauffeurMapper).toEntity(chauffeurDTO);
        verify(chauffeurRepository).save(chauffeur);
        verify(chauffeurMapper).toDTO(chauffeur);
    }

    @Test
    void testListerChauffeursDisponibles() {

        List<Chauffeur> chauffeurs = List.of(chauffeur);
        List<ChauffeurDTO> dtos = List.of(chauffeurDTO);

        when(chauffeurRepository.findByAvailableTrue()).thenReturn(chauffeurs);
        when(chauffeurMapper.toDtoList(chauffeurs)).thenReturn(dtos);

        List<ChauffeurDTO> result = chauffeurService.listerChauffeursDisponibles();


        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getAvailable());

        verify(chauffeurRepository, times(1)).findByAvailableTrue();
        verify(chauffeurMapper, times(1)).toDtoList(chauffeurs);
    }
}