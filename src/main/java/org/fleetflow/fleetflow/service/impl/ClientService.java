package org.fleetflow.fleetflow.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.fleetflow.fleetflow.dto.ClientDTO;
import org.fleetflow.fleetflow.entity.Client;
import org.fleetflow.fleetflow.entity.Role;
import org.fleetflow.fleetflow.mapper.ClientMapper;
import org.fleetflow.fleetflow.repository.ClientRepository;
import org.fleetflow.fleetflow.repository.LivraisonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService implements org.fleetflow.fleetflow.service.ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private  final LivraisonRepository livraisonRepository;

    public ClientDTO ajouterClient(ClientDTO dto) {

        if (clientRepository.existsByEmail(dto.getEmail())){
            throw new RuntimeException("email deja existe");
        }

        Client client = clientMapper.toEntity(dto);
        client.setUsername(dto.getUsername());
        client.setRole(Role.CLIENT);

        client.setHashedPassword(dto.getPassword());
        return clientMapper.toDTO(clientRepository.save(client));
    }

    public ClientDTO modifierClient(Long id, ClientDTO dto) {
        Client client = clientRepository.findById(id).orElse(null);
        clientMapper.updateEntityFromDTO(dto, client);
        return clientMapper.toDTO(clientRepository.save(client));
    }

    public void supprimerClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Client introuvable avec l'id : " + id);
        }
        clientRepository.deleteById(id);
    }

public Page<ClientDTO> listerClients(int page , int size,String sortBy,String sortDir){
        List<String> allowedSortField = List.of("id","nom","telephone","ville");

        if(!allowedSortField.contains(sortBy)){
            sortBy = "id";
        }
    boolean isAsc = "asc".equalsIgnoreCase(sortDir);

    Sort sort = isAsc
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

    Pageable pageable = PageRequest.of(page, size, sort);

  return  clientRepository.findAll(pageable).map(clientMapper::toDTO);
}

    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        return clientMapper.toDTO(client);
    }




}