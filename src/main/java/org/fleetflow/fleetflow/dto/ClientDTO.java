package org.fleetflow.fleetflow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    @NotBlank(message = "user name est obligatoire")
    private String username;
    @Email(message = "Email invalide")
    private String email;
    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    private String telephone;

    @NotBlank(message = "Ville est obligatoire")
    private String ville;
    @NotNull
    private String password;
}