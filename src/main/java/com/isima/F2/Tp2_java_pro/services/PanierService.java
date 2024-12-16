package com.isima.F2.Tp2_java_pro.services;

import com.isima.F2.Tp2_java_pro.models.Panier;
import org.springframework.stereotype.Service;

@Service
public interface PanierService {
    Panier createPanier(Panier panier);

    Panier getPanier(String email) throws Exception;

    Panier ajouterProduit(String email, String barCode) throws Exception;
    Panier supprimerProduit(String email, String barCode) throws Exception;

    void deletePanier(String email) throws Exception;
}
