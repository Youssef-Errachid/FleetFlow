package org.fleetflow.fleetflow.service;


import org.fleetflow.fleetflow.dto.ClientDTO;
import org.springframework.data.domain.Page;

public interface ClientService{
    ClientDTO ajouterClient(ClientDTO dto);
    ClientDTO modifierClient(Long id, ClientDTO dto);
    void supprimerClient(Long id);
    Page<ClientDTO> listerClients(int page , int size, String sortBy, String sortDir);
    ClientDTO getClientById(Long id);

    }