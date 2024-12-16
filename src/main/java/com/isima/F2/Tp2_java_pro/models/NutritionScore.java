package com.isima.F2.Tp2_java_pro.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class NutritionScore {
    @Id
    private int Id;
    private String Classe;
    private double Lower_bound;
    private double Upper_bound;
    private String Color;
}
