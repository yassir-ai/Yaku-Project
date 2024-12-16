package com.isima.F2.Tp2_java_pro.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.isima.F2.Tp2_java_pro.controllers.PanierController;
import com.isima.F2.Tp2_java_pro.controllers.ProduitController;
import com.isima.F2.Tp2_java_pro.models.Panier;
import com.isima.F2.Tp2_java_pro.models.Produit;
import com.isima.F2.Tp2_java_pro.services.PanierService;
import com.isima.F2.Tp2_java_pro.services.PanierServiceImpl;
import com.isima.F2.Tp2_java_pro.services.ProduitService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PanierController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PanierControllerTests {
    @Autowired
    private MockMvc mockMvc;
    private Panier panier;

    @MockBean
    private PanierService panierService;

    @BeforeEach
    public void init()
    {
        Faker faker = new Faker();
        panier = Panier.builder()
                .Email(faker.internet().emailAddress())
                .Score(faker.number().numberBetween(1, 30))
                .Classe(faker.options().option("Trop Bon", "Bon", "Mangeable", "Mouai", "Degueu"))
                .Couleur(faker.commerce().color())
                .build();
    }


    @Test
    public void PanierController_GetPanier_ReturnPanier() throws Exception {
        when(panierService.getPanier(panier.getEmail())).thenReturn(panier);

        ResultActions response = mockMvc.perform(get("/api/panier/email")
                .param("email", panier.getEmail()));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(panier.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.score", CoreMatchers.is(panier.getScore())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.couleur", CoreMatchers.is(panier.getCouleur())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.classe", CoreMatchers.is(panier.getClasse())));
    }

    @Test
    public void PanierController_CreatePanier_ReturnPanier() throws Exception {
        given(panierService.createPanier(panier))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/panier/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(panier)));

        response.andExpect(status().isOk());
    }

    @Test
    public void PanierController_DeletePanier_ReturnNoContent() throws Exception {
        String email = "test@example.com";

        doNothing().when(panierService).deletePanier(email);

        ResultActions response = mockMvc.perform(delete("/api/panier/deletePanier")
                .param("email", email));

        response.andExpect(status().isNoContent());
    }
}
