package org.fleetflow.fleetflow.service;

import jakarta.persistence.EntityNotFoundException;
import org.fleetflow.fleetflow.dto.VehiculeDTO;
import org.fleetflow.fleetflow.entity.Vehicule;
import org.fleetflow.fleetflow.mapper.VehiculeMapper;
import org.fleetflow.fleetflow.repository.VehiculeRepository;
import org.fleetflow.fleetflow.service.impl.VehiculeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculeServiceTest {

    @Mock
    private VehiculeRepository vehiculeRepository;

    @Mock
    private VehiculeMapper vehiculeMapper;

    @InjectMocks
    private VehiculeService vehiculeService;

    private Vehicule vehicule;
    private VehiculeDTO vehiculeDTO;

    @BeforeEach
    void setUp() {
        vehicule = Vehicule.builder()
                .id(1L)
                .matricule("ABC123")
                .typeVehicule("Camion")
                .capacite(500)
                .statutVehicule("DISPONIBLE")
                .permisType("C")
                .disponible(true)
                .build();

        vehiculeDTO = VehiculeDTO.builder()
                .matricule("ABC123")
                .typeVehicule("Camion")
                .capacite(500)
                .statutVehicule("DISPONIBLE")
                .permisType("C")
                .disponible(true)
                .build();
    }

    @Test
    void testAjouterVehicule() {
        when(vehiculeMapper.toEntity(vehiculeDTO)).thenReturn(vehicule);
        when(vehiculeRepository.save(vehicule)).thenReturn(vehicule);
        when(vehiculeMapper.toDTO(vehicule)).thenReturn(vehiculeDTO);

        VehiculeDTO result = vehiculeService.ajouterVehicule(vehiculeDTO);

        assertNotNull(result);
        assertEquals("ABC123", result.getMatricule());

        verify(vehiculeMapper).toEntity(vehiculeDTO);
        verify(vehiculeRepository).save(vehicule);
        verify(vehiculeMapper).toDTO(vehicule);
    }

    @Test
    void testModifierVehicule() {
        when(vehiculeRepository.findById(1L)).thenReturn(Optional.of(vehicule));
        doNothing().when(vehiculeMapper).updateEntityFromDTO(vehiculeDTO, vehicule);
        when(vehiculeRepository.save(vehicule)).thenReturn(vehicule);
        when(vehiculeMapper.toDTO(vehicule)).thenReturn(vehiculeDTO);

        VehiculeDTO result = vehiculeService.modifierVehicule(1L, vehiculeDTO);

        assertNotNull(result);
        assertEquals("ABC123", result.getMatricule());

        verify(vehiculeRepository).findById(1L);
        verify(vehiculeMapper).updateEntityFromDTO(vehiculeDTO, vehicule);
        verify(vehiculeRepository).save(vehicule);
    }

    @Test
    void testModifierVehicule_NotFound() {
        when(vehiculeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            vehiculeService.modifierVehicule(1L, vehiculeDTO);
        });
    }

    @Test
    void testSupprimerVehicule() {
        when(vehiculeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(vehiculeRepository).deleteById(1L);

        vehiculeService.supprimerVehicule(1L);

        verify(vehiculeRepository).existsById(1L);
        verify(vehiculeRepository).deleteById(1L);
    }

    @Test
    void testSupprimerVehicule_NotFound() {
        when(vehiculeRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            vehiculeService.supprimerVehicule(1L);
        });

        verify(vehiculeRepository).existsById(1L);
        verify(vehiculeRepository, never()).deleteById(any());
    }

    @Test
    void testListerVehicules() {

        List<Vehicule> vehicules = List.of(vehicule);
        List<VehiculeDTO> dtos = List.of(vehiculeDTO);

        Page<Vehicule> page = new PageImpl<>(vehicules);

        when(vehiculeRepository.findAll(any(Pageable.class)))
                .thenReturn(page);

        when(vehiculeMapper.toDtoList(vehicules))
                .thenReturn(dtos);

        Page<VehiculeDTO> result = vehiculeService.listerVehicules(
                0,
                10,
                "id",
                "asc"
        );

        assertEquals(1, result.getContent().size());

        verify(vehiculeRepository).findAll(any(Pageable.class));
        verify(vehiculeMapper).toDtoList(vehicules);
    }

    @Test
    void testListerParStatut() {
        List<Vehicule> vehicules = List.of(vehicule);
        List<VehiculeDTO> dtos = List.of(vehiculeDTO);

        when(vehiculeRepository.findByStatutVehicule("DISPONIBLE"))
                .thenReturn(vehicules);
        when(vehiculeMapper.toDtoList(vehicules)).thenReturn(dtos);

        List<VehiculeDTO> result =
                vehiculeService.listerParStatut("DISPONIBLE");

        assertEquals(1, result.size());
        assertEquals("DISPONIBLE", result.get(0).getStatutVehicule());

        verify(vehiculeRepository).findByStatutVehicule("DISPONIBLE");
    }

    @Test
    void testListerParCapaciteSuperieure() {
        List<Vehicule> vehicules = List.of(vehicule);
        List<VehiculeDTO> dtos = List.of(vehiculeDTO);

        when(vehiculeRepository.findByCapaciteGreaterThan(300))
                .thenReturn(vehicules);
        when(vehiculeMapper.toDtoList(vehicules)).thenReturn(dtos);

        List<VehiculeDTO> result =
                vehiculeService.listerParCapaciteSuperieure(300);

        assertEquals(1, result.size());
        assertTrue(result.get(0).getCapacite() > 300);

        verify(vehiculeRepository).findByCapaciteGreaterThan(300);
    }

    @Test
    void testListerParCapaciteSuperieure_Vide() {
        when(vehiculeRepository.findByCapaciteGreaterThan(1000))
                .thenReturn(List.of());
        when(vehiculeMapper.toDtoList(List.of()))
                .thenReturn(List.of());

        List<VehiculeDTO> result =
                vehiculeService.listerParCapaciteSuperieure(1000);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(vehiculeRepository).findByCapaciteGreaterThan(1000);
    }
}