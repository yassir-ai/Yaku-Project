package com.isima.F2.Tp2_java_pro.controllers;

import com.isima.F2.Tp2_java_pro.models.Panier;
import com.isima.F2.Tp2_java_pro.services.PanierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/panier/")
public class PanierController {

    private PanierService panierService;

    @Autowired
    public PanierController(PanierService panierService) {
        this.panierService = panierService;
    }

    @Operation(summary = "Récupérer un panier par son adresse e-mail")
    @ApiResponse(responseCode = "200", description = "Panier trouvé", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Panier.class))
    })
    @ApiResponse(responseCode = "404", description = "Panier non trouvé")
    @GetMapping("email")
    public ResponseEntity<?> getPanier(
            @Parameter(description = "Adresse e-mail du panier à récupérer", required = true)
            @RequestParam("email") String email) {
        try {
            return ResponseEntity.ok(panierService.getPanier(email));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Créer un nouveau panier")
    @ApiResponse(responseCode = "200", description = "Panier créé", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Panier.class))
    })
    @ApiResponse(responseCode = "502", description = "Erreur lors de la création du panier")
    @PostMapping("create")
    public ResponseEntity<?> createPanier(
            @Parameter(description = "Panier à créer", required = true)
            @RequestBody Panier panier) {
        try {
            return ResponseEntity.ok(panierService.createPanier(panier));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @Operation(summary = "Ajouter un produit à un panier")
    @ApiResponse(responseCode = "200", description = "Produit ajouté au panier", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Panier.class))
    })
    @ApiResponse(responseCode = "502", description = "Erreur lors de l'ajout du produit au panier")
    @PutMapping("add")
    public ResponseEntity<?> ajouterProduit(
            @Parameter(description = "Code-barres du produit à ajouter", required = true)
            @RequestParam String barCode,
            @Parameter(description = "Adresse e-mail du panier", required = true)
            @RequestParam String email) {
        try {
            return ResponseEntity.ok(panierService.ajouterProduit(email, barCode));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @Operation(summary = "Supprimer un produit d'un panier")
    @ApiResponse(responseCode = "200", description = "Produit supprimé du panier", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Panier.class))
    })
    @ApiResponse(responseCode = "502", description = "Erreur lors de la suppression du produit du panier")
    @DeleteMapping("delete")
    public ResponseEntity<?> supprimerProduit(
            @Parameter(description = "Code-barres du produit à supprimer", required = true)
            @RequestParam String barCode,
            @Parameter(description = "Adresse e-mail du panier", required = true)
            @RequestParam String email) {
        try {
            return ResponseEntity.ok(panierService.supprimerProduit(email, barCode));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @Operation(summary = "Supprimer un panier par son adresse e-mail")
    @ApiResponse(responseCode = "204", description = "Panier supprimé")
    @ApiResponse(responseCode = "502", description = "Erreur lors de la suppression du panier")
    @DeleteMapping("deletePanier")
    public ResponseEntity<?> deletePanier(
            @Parameter(description = "Adresse e-mail du panier à supprimer", required = true)
            @RequestParam String email) {
        try {
            panierService.deletePanier(email);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}