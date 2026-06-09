# Pixel Tracer Java

## Présentation

Pixel Tracer Java est une application de dessin vectoriel en mode texte développée en Java. Le programme permet de créer et d'afficher différentes formes géométriques directement dans la console à l'aide d'un système de rastérisation basé sur l'algorithme de Bresenham.

L'application fonctionne comme un éditeur graphique simplifié où l'utilisateur peut créer des zones de dessin, organiser les formes par couches (layers) et afficher le résultat sous forme de caractères dans le terminal.

---

## Objectifs du projet

Les principaux objectifs du projet sont :

* Manipuler les concepts de programmation orientée objet en Java.
* Implémenter des formes géométriques et leurs relations.
* Utiliser un système de couches pour organiser les éléments graphiques.
* Mettre en œuvre un algorithme de rastérisation pour transformer des formes vectorielles en pixels affichables.
* Proposer une interface en ligne de commande permettant l'interaction avec l'utilisateur.

---

## Architecture du projet

Le projet est organisé en plusieurs packages :

### app

Contient la logique de l'application :

* Command
* CommandParser
* CommandExecutor
* PixelTracerApp

Ces classes permettent d'interpréter et d'exécuter les commandes saisies par l'utilisateur.

### shape

Contient les différentes formes géométriques :

* Point
* Line
* Rectangle
* Square
* Circle
* Polygon
* Curve (courbe de Bézier)

Toutes les formes héritent de la classe abstraite Shape.

### pixel

Gère la rastérisation :

* Pixel
* Rasterizer
* BresenhamRasterizer

L'algorithme de Bresenham est utilisé pour tracer les lignes et contours.

### scene

Gère l'organisation de la scène :

* Area : zone de dessin
* Layer : couche de dessin
* IdGenerator : génération d'identifiants uniques

### render

Assure l'affichage dans la console :

* ConsoleRenderer

---

## Fonctionnalités

### Dessin de formes

L'application permet de dessiner :

* Points
* Lignes
* Carrés
* Rectangles
* Cercles
* Polygones
* Courbes de Bézier

### Gestion des couches

Chaque forme est ajoutée à une couche active.

Fonctionnalités disponibles :

* Création de nouvelles couches
* Sélection d'une couche
* Affichage ou masquage d'une couche
* Suppression de formes

### Gestion des zones de dessin

L'utilisateur peut :

* Créer plusieurs zones de dessin
* Sélectionner une zone active
* Travailler indépendamment sur plusieurs espaces graphiques

### Personnalisation de l'affichage

Il est possible de modifier :

* Le caractère utilisé pour les pixels dessinés
* Le caractère utilisé pour l'arrière-plan

---

## Commandes principales

### Contrôle

```text
help
clear
plot
exit
```

### Dessin

```text
point x y
line x1 y1 x2 y2
square x y longueur
rectangle x y largeur hauteur
circle x y rayon
polygon x1 y1 x2 y2 ...
curve x1 y1 x2 y2 x3 y3 x4 y4
```

### Gestion des éléments

```text
list areas
list layers
list shapes

new area
new layer

select area id
select layer id
select shape id

delete shape id
```

### Paramétrage

```text
set char border ascii_code
set char background ascii_code

set layer visible id
set layer unvisible id
```

---

## Prérequis

* Java 21
* Maven 3.x

---

## Compilation et exécution

Compilation et lancement :

```bash
mvn compile exec:java
```

---

## Exécution des tests

```bash
mvn test
```

---

## Exemple d'utilisation

Création d'un rectangle :

```text
rectangle 10 5 20 10
```

Création d'un cercle :

```text
circle 40 15 8
```

Affichage des formes :

```text
plot
```

Liste des formes présentes dans la couche active :

```text
list shapes
```

---

## Conclusion

Pixel Tracer Java est un projet pédagogique mettant en œuvre plusieurs concepts fondamentaux du développement logiciel : programmation orientée objet, architecture modulaire, gestion d'une scène graphique, algorithmes de dessin et interaction utilisateur par ligne de commande. Il constitue une base solide pour l'étude des systèmes de rendu graphique et des éditeurs vectoriels simplifiés.
