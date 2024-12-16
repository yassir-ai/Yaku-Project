package com.isima.F2.Tp2_java_pro.repository;

import com.github.javafaker.Faker;
import com.isima.F2.Tp2_java_pro.models.Panier;
import com.isima.F2.Tp2_java_pro.repositories.PanierRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PanierRepositoryTests {
    @Autowired
    private PanierRepository panierRepository;

    @Test
    public void PanierRepository_FindByEmail_ReturnPanier()
    {
        Faker faker = new Faker();

        Panier panier = Panier.builder()
                .Email(faker.internet().emailAddress())
                .Score(faker.number().numberBetween(1, 30))
                .Classe(faker.options().option("Trop Bon", "Bon", "Mangeable", "Mouai", "Degueu"))
                .Couleur(faker.commerce().color())
                .build();

        panierRepository.save(panier);

        Panier panierFound = panierRepository.findByEmail(panier.getEmail());
        Panier panierNotFound = panierRepository.findByEmail(faker.internet().emailAddress());

        Assertions.assertThat(panierFound).isNotNull();
        Assertions.assertThat(panier.getEmail()).isEqualTo(panierFound.getEmail());
        Assertions.assertThat(panier.getScore()).isEqualTo(panierFound.getScore());
        Assertions.assertThat(panier.getCouleur()).isEqualTo(panierFound.getCouleur());
        Assertions.assertThat(panier.getClasse()).isEqualTo(panierFound.getClasse());
        Assertions.assertThat(panierNotFound).isNull();
    }
}
