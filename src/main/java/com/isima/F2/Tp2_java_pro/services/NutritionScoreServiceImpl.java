package com.isima.F2.Tp2_java_pro.services;

import com.isima.F2.Tp2_java_pro.models.NutritionScore;
import com.isima.F2.Tp2_java_pro.repositories.NutritionScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NutritionScoreServiceImpl implements NutritionScoreService {

    private final NutritionScoreRepository nutritionScoreRepository;

    @Autowired
    public NutritionScoreServiceImpl(NutritionScoreRepository nutritionScoreRepository) {
        this.nutritionScoreRepository = nutritionScoreRepository;
    }

    @Override
    public String getClasse(int score) {
        NutritionScore nutritionScore = nutritionScoreRepository.findByScore(score);

        return nutritionScore.getClasse();
    }

    @Override
    public String getColor(int score) {
        NutritionScore nutritionScore = nutritionScoreRepository.findByScore(score);

        return nutritionScore.getColor();
    }
}