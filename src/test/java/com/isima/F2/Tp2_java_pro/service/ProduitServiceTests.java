package com.isima.F2.Tp2_java_pro.service;

import com.github.javafaker.Faker;
import com.isima.F2.Tp2_java_pro.models.Produit;
import com.isima.F2.Tp2_java_pro.productExceptions.ProductNotFoundException;
import com.isima.F2.Tp2_java_pro.repositories.ProduitRepository;
import com.isima.F2.Tp2_java_pro.services.NutritionScoreService;
import com.isima.F2.Tp2_java_pro.services.ProduitServiceImpl;
import com.isima.F2.Tp2_java_pro.services.RuleService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProduitServiceTests {

    private ProduitRepository produitRepository;
    private RuleService ruleService;
    private NutritionScoreService nutritionScoreService;
    private ProduitServiceImpl produitService;
    private Faker faker;

    @BeforeEach
    public void setUp() {
        produitRepository = mock(ProduitRepository.class);
        ruleService = mock(RuleService.class);
        nutritionScoreService = mock(NutritionScoreService.class);
        produitService = new ProduitServiceImpl(produitRepository, ruleService, nutritionScoreService);
        faker = new Faker();
    }

    @Test
    public void ProduitSergvice_GetProduit_ReturnsProduit() throws Exception {

        Produit produit = Produit.builder()
                .Name(faker.commerce().productName())
                .BarCode("3608580844136")
                .NutritionScore(faker.number().numberBetween(1, 30))
                .Classe(faker.options().option("Trop Bon", "Bon", "Mangeable", "Mouai", "Degueu"))
                .Color(faker.commerce().color()).build();

        when(produitRepository.findByBarCode(produit.getBarCode())).thenReturn(produit);

        Produit ProduitFound = produitService.getProduit(produit.getBarCode());

        Assertions.assertThat(ProduitFound).isNotNull();
        Assertions.assertThat(ProduitFound.getName()).isEqualTo(produit.getName());
        Assertions.assertThat(ProduitFound.getBarCode()).isEqualTo(produit.getBarCode());
        Assertions.assertThat(ProduitFound.getNutritionScore()).isEqualTo(produit.getNutritionScore());
        Assertions.assertThat(ProduitFound.getClasse()).isEqualTo(produit.getClasse());
        Assertions.assertThat(ProduitFound.getColor()).isEqualTo(produit.getColor());

        Assertions.assertThatThrownBy(() -> produitService.getProduit(faker.number().digits(13)))
                .isInstanceOf(ProductNotFoundException.class);
        verify(ruleService, never()).getPoint(anyString(), anyDouble());
    }
}
