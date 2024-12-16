package com.isima.F2.Tp2_java_pro.services;

import com.isima.F2.Tp2_java_pro.models.Panier;
import com.isima.F2.Tp2_java_pro.models.Produit;
import com.isima.F2.Tp2_java_pro.productExceptions.ProductNotFoundException;
import com.isima.F2.Tp2_java_pro.repositories.PanierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PanierServiceImpl implements PanierService{

    private final PanierRepository panierRepository;
    private final ProduitService produitService;
    private final NutritionScoreService nutritionScoreService;


    @Autowired
    public PanierServiceImpl(PanierRepository panierRepository, ProduitService produitService, NutritionScoreService nutritionScoreService) {
        this.panierRepository = panierRepository;
        this.produitService = produitService;
        this.nutritionScoreService = nutritionScoreService;
    }

    @Override
    public Panier getPanier(String email) throws Exception {
        Panier panier = panierRepository.findByEmail(email);

        if( panier == null) throw new Exception("Panier non existant");

        return panier;
    }

    @Override
    public Panier createPanier(Panier panier)
    {
        return panierRepository.save(panier);
    }

    @Override
    public Panier ajouterProduit(String email, String barCode) throws Exception {
        Produit produit = produitService.getProduit(barCode);

        Panier panier = panierRepository.findByEmail(email);

        if(panier == null) throw new Exception("Panier non existant");

        List<Produit> produits = panier.getProduits();
        produits.add(produit);

        panier.setScore(calculScoreProduits(produits));
        panier.setClasse(calculClasseProduits(panier.getScore()));
        panier.setCouleur(calculCouleurProduits(panier.getScore()));

        panier.setProduits(produits);

        panierRepository.save(panier);

        return panier;
    }

    @Override
    public Panier supprimerProduit(String email, String barCode) throws Exception {

        Panier panier = panierRepository.findByEmail(email);

        if(panier == null) throw new Exception("Panier non existant");

        List<Produit> produits = panier.getProduits();

        Produit produitTrouve = produits.stream()
                .filter(p -> p.getBarCode().equals(barCode))
                .findFirst()
                .orElse(null);

        if(produitTrouve == null) throw new ProductNotFoundException(barCode);

        produits.remove(produitTrouve);

        panier.setProduits(produits);

        panier.setScore(calculScoreProduits(produits));
        panier.setClasse(calculClasseProduits(panier.getScore()));
        panier.setCouleur(calculCouleurProduits(panier.getScore()));


        panierRepository.save(panier);

        return panier;
    }

    @Override
    public void deletePanier(String email) throws Exception {
        Panier panier = panierRepository.findByEmail(email);

        if(panier == null) throw new Exception("Panier non existant");

        panierRepository.delete(panier);
    }

    private int calculScoreProduits(List<Produit> produits)
    {
        if (produits == null || produits.isEmpty()) {
            return 0;
        }

        int sommeScores = 0;

        for (Produit produit : produits) {
            sommeScores += produit.getNutritionScore();
        }

        return sommeScores / produits.size();
    }

    private String calculClasseProduits(int score)
    {
        return nutritionScoreService.getClasse(score);
    }

    private String calculCouleurProduits(int score)
    {
        return nutritionScoreService.getColor(score);
    }
}
