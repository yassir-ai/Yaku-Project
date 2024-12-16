package com.isima.F2.Tp2_java_pro.service;

import com.github.javafaker.Faker;
import com.isima.F2.Tp2_java_pro.models.Panier;
import com.isima.F2.Tp2_java_pro.models.Produit;
import com.isima.F2.Tp2_java_pro.productExceptions.ProductNotFoundException;
import com.isima.F2.Tp2_java_pro.repositories.PanierRepository;
import com.isima.F2.Tp2_java_pro.services.NutritionScoreServiceImpl;
import com.isima.F2.Tp2_java_pro.services.PanierServiceImpl;
import com.isima.F2.Tp2_java_pro.services.ProduitServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PanierServicesTests {
    @Mock
    private PanierRepository panierRepository;
    @Mock
    private ProduitServiceImpl produitService;
    @Mock
    private NutritionScoreServiceImpl nutritionScoreService;

    @InjectMocks
    private PanierServiceImpl panierService;



    @Test
    public void PanierService_GetPanier_ReturnPanier() throws Exception {
        Faker faker = new Faker();

        Panier panier = Panier.builder()
                .Email(faker.internet().emailAddress())
                .Score(faker.number().numberBetween(1, 30))
                .Classe(faker.options().option("Trop Bon", "Bon", "Mangeable", "Mouai", "Degueu"))
                .Couleur(faker.commerce().color())
                .build();

        when(panierRepository.findByEmail(panier.getEmail())).thenReturn(panier);

        Panier panierFound = panierService.getPanier(panier.getEmail());

        Assertions.assertThat(panierFound).isNotNull();
        Assertions.assertThat(panier.getEmail()).isEqualTo(panierFound.getEmail());
        Assertions.assertThat(panier.getScore()).isEqualTo(panierFound.getScore());
        Assertions.assertThat(panier.getCouleur()).isEqualTo(panierFound.getCouleur());
        Assertions.assertThat(panier.getClasse()).isEqualTo(panierFound.getClasse());
        Assertions.assertThatThrownBy(() -> panierService.getPanier(faker.internet().emailAddress()))
                .isInstanceOf(Exception.class);
    }

    @Test
    public void PanierService_CreatePanier_ReturnPanier() throws Exception {
        Faker faker = new Faker();

        Panier panier = Panier.builder()
                .Email(faker.internet().emailAddress())
                .Score(faker.number().numberBetween(1, 30))
                .Classe(faker.options().option("Trop Bon", "Bon", "Mangeable", "Mouai", "Degueu"))
                .Couleur(faker.commerce().color())
                .build();

        when(panierRepository.save(panier)).thenReturn(panier);

        Panier panierFound = panierService.createPanier(panier);

        Assertions.assertThat(panierFound).isNotNull();
        Assertions.assertThat(panier.getEmail()).isEqualTo(panierFound.getEmail());
        Assertions.assertThat(panier.getScore()).isEqualTo(panierFound.getScore());
        Assertions.assertThat(panier.getCouleur()).isEqualTo(panierFound.getCouleur());
        Assertions.assertThat(panier.getClasse()).isEqualTo(panierFound.getClasse());
    }

    @Test
    public void PanierService_AjouterProduit_ReturnPanier() throws Exception {
        Faker faker = new Faker();

        Panier panier = Panier.builder()
                .Email(faker.internet().emailAddress())
                .Score(faker.number().numberBetween(1, 30))
                .Classe(faker.options().option("Trop Bon", "Bon", "Mangeable", "Mouai", "Degueu"))
                .Couleur(faker.commerce().color())
                .build();

        Produit produit = Produit.builder()
                .Name(faker.commerce().productName())
                .BarCode("3608580844136")
                .NutritionScore(faker.number().numberBetween(1, 30))
                .Classe(faker.options().option("Trop Bon", "Bon", "Mangeable", "Mouai", "Degueu"))
                .Color(faker.commerce().color()).build();

        panier.setProduits(new ArrayList<>());

        when(panierRepository.save(panier)).thenReturn(panier);
        when(panierRepository.findByEmail(panier.getEmail())).thenReturn(panier);
        when(produitService.getProduit(produit.getBarCode())).thenReturn(produit);
        when(nutritionScoreService.getClasse(produit.getNutritionScore())).thenReturn(produit.getClasse());
        when(nutritionScoreService.getColor(produit.getNutritionScore())).thenReturn(produit.getColor());

        Panier panierFound = panierService.ajouterProduit(panier.getEmail(), produit.getBarCode());

        Assertions.assertThat(panierFound).isNotNull();
        Assertions.assertThat(panier.getEmail()).isEqualTo(panierFound.getEmail());
        Assertions.assertThat(panier.getScore()).isEqualTo(panierFound.getScore());
        Assertions.assertThat(panier.getCouleur()).isEqualTo(panierFound.getCouleur());
        Assertions.assertThat(panier.getClasse()).isEqualTo(panierFound.getClasse());
        Assertions.assertThat(produit).isIn(panier.getProduits());
    }

    @Test
    public void PanierService_SupprimerProduit_ReturnPanier() throws Exception {
        Faker faker = new Faker();

        Panier panier = Panier.builder()
                .Email(faker.internet().emailAddress())
                .Score(0)
                .Classe(faker.options().option("Trop Bon", "Bon", "Mangeable", "Mouai", "Degueu"))
                .Couleur(faker.commerce().color())
                .build();

        Produit produit = Produit.builder()
                .Name(faker.commerce().productName())
                .BarCode("3608580844136")
                .NutritionScore(faker.number().numberBetween(1, 30))
                .Classe(faker.options().option("Trop Bon", "Bon", "Mangeable", "Mouai", "Degueu"))
                .Color(faker.commerce().color()).build();

        List<Produit> produitList = new ArrayList<>();
        panier.setProduits(produitList);


        when(panierRepository.save(panier)).thenReturn(panier);
        when(panierRepository.findByEmail(panier.getEmail())).thenReturn(panier);
        when(produitService.getProduit(produit.getBarCode())).thenReturn(produit);
        when(nutritionScoreService.getClasse(anyInt())).thenReturn(panier.getClasse());
        when(nutritionScoreService.getColor(anyInt())).thenReturn(panier.getCouleur());

        Panier panierFound = panierService.ajouterProduit(panier.getEmail(), produit.getBarCode());

        Assertions.assertThat(panierFound).isNotNull();
        Assertions.assertThat(produit).isIn(panier.getProduits());

        panierFound = panierService.supprimerProduit(panier.getEmail(), produit.getBarCode());

        Assertions.assertThat(panierFound).isNotNull();
        Assertions.assertThat(panier.getProduits()).doesNotContain(produit);
    }

    @Test
    public void PanierService_DeletetePanier_ReturnVoid() throws Exception {
        Faker faker = new Faker();

        Panier panier = Panier.builder()
                .Email(faker.internet().emailAddress())
                .Score(faker.number().numberBetween(1, 30))
                .Classe(faker.options().option("Trop Bon", "Bon", "Mangeable", "Mouai", "Degueu"))
                .Couleur(faker.commerce().color())
                .build();

        when(panierRepository.findByEmail(panier.getEmail())).thenReturn(panier);

        assertAll(() -> panierService.deletePanier(panier.getEmail()));
    }
}
