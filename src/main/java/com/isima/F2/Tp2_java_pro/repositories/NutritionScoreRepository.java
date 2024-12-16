package com.isima.F2.Tp2_java_pro.repositories;

import com.isima.F2.Tp2_java_pro.models.NutritionScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NutritionScoreRepository extends JpaRepository<NutritionScore, Integer> {
    @Query("SELECT n FROM NutritionScore n WHERE ?1 BETWEEN n.Lower_bound AND n.Upper_bound")
    NutritionScore findByScore(int score);
}
