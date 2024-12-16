package com.isima.F2.Tp2_java_pro.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.isima.F2.Tp2_java_pro.models.Produit;
import com.isima.F2.Tp2_java_pro.models.ProduitApi;
import com.isima.F2.Tp2_java_pro.httpRequest.HttpGet;
import com.isima.F2.Tp2_java_pro.productExceptions.ProductNotFoundException;
import com.isima.F2.Tp2_java_pro.repositories.ProduitRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProduitServiceImpl implements ProduitService{
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public class ClasseCouleur {
        String classe;
        String couleur;
    }

    private String apiUrl = "https://fr.openfoodfacts.org/api/v0/produit/";
    private final RuleService ruleService;

    private final NutritionScoreService nutritionScoreService;
    private final ProduitRepository produitRepository;

    @Autowired
    public ProduitServiceImpl(ProduitRepository produitRepository, RuleService ruleService, NutritionScoreService nutritionScoreService) {
        this.produitRepository = produitRepository;
        this.ruleService = ruleService;
        this.nutritionScoreService = nutritionScoreService;
    }

    @Override
    public Produit getProduit(String id) throws Exception
    {
        String productUrl = apiUrl + id + ".json";
        //if(produitRepository.existsByBarCode(id))

        Produit p = produitRepository.findByBarCode(id);

        if(p != null)
        {
            return p;
        }

        HttpGet httpGet = new HttpGet(productUrl);
        JsonNode jsonNode = httpGet.GetResponse();

        if (jsonNode != null)
        {
            int errorCode = jsonNode.get("status").asInt();

            if (errorCode != 1)
            {
                throw new ProductNotFoundException(id);
            }

            ProduitApi pro = new ProduitApi();
            pro.setName(jsonNode.get("product").get("product_name").asText());
            pro.setId(jsonNode.get("code").asText());

            JsonNode nutriments = jsonNode.get("product").get("nutriments");

            pro.getNutritions().put("energy_100g", nutriments.get("energy_100g").asDouble());
            pro.getNutritions().put("saturated-fat_100g", nutriments.get("saturated-fat_100g").asDouble());
            pro.getNutritions().put("salt_100g", nutriments.get("salt_100g").asDouble());
            pro.getNutritions().put("sugars_100g", nutriments.get("sugars_100g").asDouble());

            pro.getNutritions().put("fiber_100g", nutriments.get("fiber_100g").asDouble());
            pro.getNutritions().put("proteins_100g", nutriments.get("proteins_100g").asDouble());

            Produit produit = new Produit();

            produit.setBarCode(pro.getId());
            produit.setName(pro.getName());
            produit.setNutritionScore(calculScore(pro));

            produit.setClasse(nutritionScoreService.getClasse(produit.getNutritionScore()));
            produit.setColor(nutritionScoreService.getColor(produit.getNutritionScore()));

            produitRepository.save(produit);

            return produit;
        }

        return null;
    }

    @Override
    public int getScore(String id) throws Exception {

        Produit product = getProduit(id);

        return product.getNutritionScore();
    }


    private int calculScore(ProduitApi p) throws Exception {

        int res = 0;

        Map<String, Double> nutritions = p.getNutritions();

        for(Map.Entry<String, Double> x : nutritions.entrySet())
        {
            res += ruleService.getPoint(x.getKey(), x.getValue());
        }

        return res;
    }

    @Override
    public ClasseCouleur getClasseCouleur(String id) throws Exception {
        Produit product = getProduit(id);

        ClasseCouleur classeCouleur = new ClasseCouleur(product.getClasse(), product.getColor());

        return classeCouleur;
    }

}
