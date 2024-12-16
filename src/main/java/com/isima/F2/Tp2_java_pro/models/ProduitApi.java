package com.isima.F2.Tp2_java_pro.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProduitApi {
    @Id
    private String Id;
    private String Name;
    @ElementCollection
    @CollectionTable(name = "produit_nutrition")
    @MapKeyColumn(name = "nutrition_name")
    @Column(name = "nutrition_value")
    private Map<String, Double> nutritions = new HashMap<>();
}
