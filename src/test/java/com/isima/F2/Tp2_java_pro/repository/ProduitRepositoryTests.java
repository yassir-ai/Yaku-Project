package com.isima.F2.Tp2_java_pro.repository;

import com.github.javafaker.Faker;
import com.isima.F2.Tp2_java_pro.models.Produit;
import com.isima.F2.Tp2_java_pro.repositories.ProduitRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProduitRepositoryTests {
    @Autowired
    private ProduitRepository produitRepository;

    @Test
    public void ProduitRepository_ExistsByBarCode_ReturnBoolean()
    {
        Faker faker = new Faker();

        Produit produit = Produit.builder()
                .Name(faker.commerce().productName())
                .BarCode(faker.number().digits(13))
                .NutritionScore(faker.number().numberBetween(1, 30))
                .Classe(faker.options().option("Trop Bon", "Bon", "Mangeable", "Mouai", "Degueu"))
                .Color(faker.commerce().color()).build();

        produitRepository.save(produit);

        boolean ProduitExists = produitRepository.existsByBarCode(produit.getBarCode());
        boolean ProduitNotExists = produitRepository.existsByBarCode(faker.number().digits(13));

        Assertions.assertThat(ProduitExists).isTrue();
        Assertions.assertThat(ProduitNotExists).isFalse();
    }

    @Test
    public void ProduitRepository_FindByBarCode_ReturnProduit()
    {
        Faker faker = new Faker();

        Produit produit = Produit.builder()
                .Name(faker.commerce().productName())
                .BarCode(faker.number().digits(13))
                .NutritionScore(faker.number().numberBetween(1, 30))
                .Classe(faker.options().option("Trop Bon", "Bon", "Mangeable", "Mouai", "Degueu"))
                .Color(faker.commerce().color()).build();

        produitRepository.save(produit);

        Produit ProduitFound = produitRepository.findByBarCode(produit.getBarCode());
        Produit ProduitNotFound = produitRepository.findByBarCode(faker.number().digits(13));

        Assertions.assertThat(ProduitFound).isNotNull();
        Assertions.assertThat(ProduitFound.getName()).isEqualTo(produit.getName());
        Assertions.assertThat(ProduitFound.getBarCode()).isEqualTo(produit.getBarCode());
        Assertions.assertThat(ProduitFound.getNutritionScore()).isEqualTo(produit.getNutritionScore());
        Assertions.assertThat(ProduitFound.getClasse()).isEqualTo(produit.getClasse());
        Assertions.assertThat(ProduitFound.getColor()).isEqualTo(produit.getColor());
        Assertions.assertThat(ProduitNotFound).isNull();
    }
}
