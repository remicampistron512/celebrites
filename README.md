# Célébrités

## Table des matières

1. [Introduction](#introduction)
2. [Installation](#installation)
3. [Utilisation](#utilisation)
4. [Fonctionnalités](#fonctionnalités)
5. [Structure-du-code](#structure-du-code)

---

## Introduction

Ce projet identifie un **groupe de célébrités** parmi des invités d’une fête.
Un invité est **célébrité** si :

* chaque invité le **connaît** (R1), et
* il ne **connaît** que des célébrités (R2).
  Avec le jeu de données fourni, le groupe attendu est **{Edouard, Françoise}**.

> Variante : si Édouard connaît aussi Bénédicte (un quidam), le groupe devient **vide**.

---

## Installation

* **Pré-requis** : Java 11+
* **Compilation**

  ```bash
  javac celebrites/Celebrities.java
  ```
* **Exécution**

  ```bash
  java celebrites.Celebrities
  ```

---

## Utilisation

Le programme trace des informations via `java.util.logging` (format simple, sans timestamp) et affiche :

* les candidats “connus de tous”,
* le groupe final après filtrage (ne connaître que des célébrités).

### Jeux de données (dans `main`)

* **Cas attendu** (recommandé) :

  ```java
  guestRelations.put(4, new int[] {5}); // Edouard -> [Françoise]
  ```
* **Variante vide** (pour test R2) :

  ```java
  guestRelations.put(4, new int[] {5, 1}); // Edouard -> [Françoise, Bénédicte]
  ```

**Mapping indices ↔ noms**
`0: Albert, 1: Bénédicte, 2: Christophe, 3: Delphine, 4: Edouard, 5: Françoise, 6: Gaston, 7: Heloïse`

---

## Fonctionnalités

* **Détection des candidats** : identifie les invités **connus de tous** (R1).
* **Filtrage itératif** : retire tout candidat qui **connaît** un non-candidat jusqu’à stabilisation (R2).
* **Journalisation** : affichage des étapes (matrice “qui connaît qui”, candidats, résultat final).
* **Jeux de données** : cas “attendu” et variante “groupe vide” inclus en un changement de ligne.

---

## Structure-du-code

* **Fichier principal** : `celebrites/Celebrities.java`

### Principales méthodes

* `getKnowers(int guestId, Map<Integer,int[]> guestRelations, Map<Integer,boolean[]> guestsKnowledge)`
  Construit la matrice “qui connaît **guestId**” (ligne `guestId` de `guestsKnowledge`).

* `getCelebritiesCandidates(Map<Integer,boolean[]> guestsKnowledge)`
  Retourne la liste des indices **connus de tous** (candidats selon R1).

* `onlyKnowCelebrities(ArrayList<Integer> candidates, Map<Integer,int[]> guestRelations)`
  Filtre les candidats qui **connaissent** au moins un non-candidat, jusqu’à convergence (R2).
