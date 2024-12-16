package com.isima.F2.Tp2_java_pro.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Panier {
    @Id
    private String Email;
    @ManyToMany
    @JoinTable(
            name = "panier_produits",
            joinColumns = @JoinColumn(name = "panier_email"),
            inverseJoinColumns = @JoinColumn(name = "produit_id")
    )
    private List<Produit> Produits = new ArrayList<Produit>();
    private int Score;
    private String Couleur;
    private String Classe;
}