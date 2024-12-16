package com.isima.F2.Tp2_java_pro.controller;

import com.github.javafaker.Faker;
import com.isima.F2.Tp2_java_pro.controllers.ProduitController;
import com.isima.F2.Tp2_java_pro.models.Produit;
import com.isima.F2.Tp2_java_pro.services.ProduitService;
import okhttp3.MediaType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(controllers = ProduitController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProduitControllerTests {

    @Autowired
    private MockMvc mockMvc;
    private Produit produit;

    @MockBean
    private ProduitService produitService;

    @BeforeEach
    public void init()
    {
        Faker faker = new Faker();
        produit = Produit.builder()
                .Name(faker.commerce().productName())
                .BarCode(faker.number().digits(13))
                .NutritionScore(faker.number().numberBetween(1, 30))
                .Classe(faker.options().option("Trop Bon", "Bon", "Mangeable", "Mouai", "Degueu"))
                .Color(faker.commerce().color()).build();
    }


    @Test
    public void ProduitController_GetProduit_ReturnProduit() throws Exception {
        when(produitService.getProduit(produit.getBarCode())).thenReturn(produit);

        ResultActions response = mockMvc.perform(get("/api/produit/{id}", produit.getBarCode()));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(produit.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.barCode", CoreMatchers.is(produit.getBarCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", CoreMatchers.is(produit.getColor())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.classe", CoreMatchers.is(produit.getClasse())));
    }

    @Test
    public void ProduitController_GetProduit_ReturnInt() throws Exception {
        when(produitService.getScore(produit.getBarCode())).thenReturn(produit.getNutritionScore());

        ResultActions response = mockMvc.perform(get("/api/produit/{id}/score", produit.getBarCode()));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
