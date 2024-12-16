package com.isima.F2.Tp2_java_pro.repositories;

import com.isima.F2.Tp2_java_pro.models.Panier;
import com.isima.F2.Tp2_java_pro.models.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PanierRepository extends JpaRepository<Panier, String> {

    @Query("SELECT p FROM Panier p where ?1 = p.Email")
    Panier findByEmail(String email);
}
