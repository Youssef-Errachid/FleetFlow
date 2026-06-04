package org.fleetflow.fleetflow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "chauffeur")
@PrimaryKeyJoinColumn(name = "user_id")
public class Chauffeur extends User {

    private String nom;

    private String telephone;

    private String licenseType;

    @Builder.Default
    private Boolean available = true;

    @OneToMany(mappedBy = "chauffeur")
    private List<Livraison> livraisons;
}