package org.fleetflow.fleetflow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "client")
public class Client extends User {

    private String nom;
    private String telephone;
    private String ville;

    @OneToMany(mappedBy = "client")
    private List<Livraison> livraisons;
}
