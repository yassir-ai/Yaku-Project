# Yaku

## Introduction

Yaku est un projet de développement d’application backend basée sur Spring Boot, inspiré de l’application mobile Yuka. Son objectif est de fournir une API REST permettant aux utilisateurs d’accéder à des informations nutritionnelles précises sur les produits alimentaires, ainsi que des analyses avancées comme le calcul du score nutritionnel, le classement des aliments ou encore la synthèse d’un panier.

Cette API repose sur les données publiques fournies par Open Food Facts et propose des fonctionnalités avancées telles que l’enregistrement des données des utilisateurs pour leur fournir des services personnalisés.

---

## Fonctionnalités

### 1. Récupération des informations nutritionnelles d'un produit

- **Description** : Permet de récupérer les informations nutritionnelles d’un produit via son code-barres.
- **Dépendances** : L’API Open Food Facts est interrogée pour obtenir les informations.
- **Exemple de requête** :

  ```http
  GET /product/7622210449283
  ```
  **Exemple de réponse :**
  ```json
  {
      "id": 0,
      "barCode": "7622210449283",
      "name": "Prince Goût Chocolat au Blé Complet",
      "nutritionScore": 9
  }
  ```

---

### 2. Calcul du score nutritionnel

- **Description** : Fournit un score nutritionnel calculé à partir des données Open Food Facts. Ce score repose sur deux composantes :
  - Composante négative (densité énergétique, sucres, graisses saturées, sel).
  - Composante positive (fibres et protéines).

- **Mise en oeuvre** :
  - Les données de calcul du score sont enregistrées dans une base de données relationnelle.
  - Le calcul suit les spécifications décrites dans l’[Annexe 1](#annexe-1---score-nutritionnel).

---

### 3. Classement de l’aliment

- **Description** : Attribue une classe nutritionnelle à un produit ("Trop Bon", "Bon", "Mangeable", etc.) et un code couleur en fonction de son score nutritionnel.
- **Exemple de réponse :**

  ```json
  {
      "id": 0,
      "barCode": "7622210449283",
      "name": "Prince Goût Chocolat au Blé Complet",
      "nutritionScore": 9,
      "classe": "Mangeable",
      "color": "yellow"
  }
  ```

---

### 4. Synthèse de panier

- **Description** : Fournit une analyse nutritionnelle globale d’un panier composé de plusieurs produits.
- **Fonctionnalités clés** :
  - Calcul du score moyen du panier.
  - Répartition des classes nutritionnelles des produits.
  - Sauvegarde des paniers liés aux utilisateurs identifiés par leur email.

---

### 5. Gestion des additifs (Facultatif)

- **Description** : Indique les additifs toxiques présents dans les produits.
- **Source des données** : Un fichier CSV contenant les niveaux de toxicité des principaux additifs est utilisé.

---

### 6. Documentation de l'API REST

- **Description** : Utilisation de Swagger pour générer une documentation automatique et visuelle des endpoints de l’API REST. Cela garantit une accessibilité et une facilité d’utilisation pour les développeurs tiers.

---

## Prérequis techniques

- **Framework** : Spring Boot
- **Langage** : Java (version 11 ou supérieure recommandée).
- **Gestionnaire de dépendances** : Maven
- **Base de données** : H2 (in-memory pour développement rapide) ou tout autre système au choix.
- **Format d’échange** : JSON

---

## Bonnes pratiques de développement

1. **Tests unitaires** :
   - Couverture de tests suffisante pour garantir la fiabilité.
   - Tests pour chaque fonctionnalité (endpoints, calculs, etc.).

2. **Organisation du code** :
   - Modularité et respect des principes SOLID.
   - Faible couplage entre les composants pour une évolutivité facilitée.

3. **Utilisation optimale de Spring Boot** :
   - Injection de dépendances.
   - Utilisation des starters appropriés (e.g., `spring-boot-starter-data-jpa`, `spring-boot-starter-web`).

---

## Annexes

### Annexe 1 - Score nutritionnel

Le score nutritionnel est calculé comme suit :

**Score nutritionnel = Composante Négative (N) - Composante Positive (P)**

#### Composante Négative (N)

| Points | Densité énergétique (kJ/100g) | Graisses saturées (g/100g) | Sucres simples (g/100g) | Sodium (mg/100g) |
|--------|---------------------------------|-----------------------------|-------------------------|-----------------|
| 0      | <= 335                         | <= 1                        | <= 4,5                 | <= 90          |
| 10     | > 3350                         | > 10                        | > 45                   | > 900          |

#### Composante Positive (P)

| Points | Fibres (g/100g) | Protéines (g/100g) |
|--------|-----------------|---------------------|
| 0      | <= 0,9          | <= 1,6             |
| 5      | > 4,7           | > 8,0              |

### Annexe 2 - Classement nutritionnel

| Classe     | Bornes du score | Couleur       |
|------------|-----------------|---------------|
| Trop Bon   | Min à -1       | green         |
| Bon        | 0 à 2          | light green   |
| Mangeable  | 3 à 10         | yellow        |
| Mouai      | 11 à 18        | orange        |
| Degueu     | 19 à Max       | red           |

---

## Installation et exécution

1. Clonez le projet :
   ```bash
   git clone <url-du-repository>
   ```

2. Importez-le dans votre IDE Java préféré.

3. Assurez-vous d’avoir Maven installé. Compilez le projet :
   ```bash
   mvn clean install
   ```

4. Lancez l’application :
   ```bash
   mvn spring-boot:run
   ```

5. Accédez à la documentation Swagger :
   ```
   http://localhost:8080/swagger-ui.html
   ```

