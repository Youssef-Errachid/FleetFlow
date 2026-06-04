package org.fleetflow.fleetflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChauffeurDTO {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    @NotBlank(message = "user name est obligatoire")
    private String username;
    @NotBlank(message = "Le téléphone est obligatoire")
    private String telephone;

    @NotBlank(message = "Le type de permis est obligatoire")
    private String licenseType;

    @NotNull(message = "La disponibilité est obligatoire")
    private Boolean available;

    @NotNull
    private String password;

}