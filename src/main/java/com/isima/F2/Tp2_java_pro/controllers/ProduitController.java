package com.isima.F2.Tp2_java_pro.controllers;

import com.isima.F2.Tp2_java_pro.models.Produit;
import com.isima.F2.Tp2_java_pro.services.ProduitService;
import com.isima.F2.Tp2_java_pro.services.ProduitServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produit/")
public class ProduitController
{
    private ProduitService produitService;

    @Autowired
    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @Operation(summary = "Récupérer un produit par son ID")
    @ApiResponse(responseCode = "200", description = "Produit trouvé", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Produit.class))
    })
    @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    @GetMapping("{id}")
    public ResponseEntity<?> getProduit(
            @Parameter(description = "ID du produit à récupérer", required = true)
            @PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(produitService.getProduit(id));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Récupérer le score d'un produit par son ID")
    @ApiResponse(responseCode = "200", description = "Score du produit trouvé", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))
    })
    @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    @GetMapping("{id}/score")
    public ResponseEntity<?> getScore(
            @Parameter(description = "ID du produit pour lequel récupérer le score", required = true)
            @PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(produitService.getScore(id));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Récupérer la classe et la couleur d'un produit par son ID")
    @ApiResponse(responseCode = "200", description = "Classe couleur du produit trouvée", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProduitServiceImpl.ClasseCouleur.class))
    })
    @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    @GetMapping("{id}/classecouleur")
    public ResponseEntity<?> getClasseCouleur(
            @Parameter(description = "ID du produit pour lequel récupérer la classe couleur", required = true)
            @PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(produitService.getClasseCouleur(id));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}