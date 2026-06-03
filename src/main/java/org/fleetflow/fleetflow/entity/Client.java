package org.fleetflow.fleetflow.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
