package com.isima.F2.Tp2_java_pro.services;

import org.springframework.stereotype.Service;

@Service
public interface NutritionScoreService {
    String getClasse(int score);
    String getColor(int score);
}
