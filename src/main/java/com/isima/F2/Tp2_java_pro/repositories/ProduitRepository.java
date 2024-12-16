package com.isima.F2.Tp2_java_pro.repositories;

import com.isima.F2.Tp2_java_pro.models.Produit;
import com.isima.F2.Tp2_java_pro.models.ProduitApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Integer> {

    @Query("SELECT COUNT(p) > 0 FROM Produit p WHERE ?1 = p.BarCode")
    boolean existsByBarCode(String barCode);

    @Query("SELECT p FROM Produit p WHERE ?1 = p.BarCode")
    Produit findByBarCode(String barCode);
}
