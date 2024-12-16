package com.isima.F2.Tp2_java_pro.services;

import com.isima.F2.Tp2_java_pro.models.Produit;
import org.springframework.stereotype.Service;

@Service
public interface ProduitService {

    Produit getProduit(String id) throws Exception;
    int getScore(String id) throws Exception;
    ProduitServiceImpl.ClasseCouleur getClasseCouleur(String id) throws Exception;

}
