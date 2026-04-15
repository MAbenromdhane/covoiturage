# 🚗 Projet Covoiturage - Suivi des Tâches

Ce fichier permet de suivre l'avancement du projet étape par étape. Chaque tâche doit être cochée une fois terminée.

---

## 👥 Répartition
- **USER**: Développement de l'application Android
- **FRIEND**: Développement du Backend (API & Base de données)

---

## 📋 Liste des Tâches

### Phase 1 : Système d'Authentification (En cours)
- [ ] Interface d'Inscription (Layout XML) - **USER**
- [ ] Logique d'Inscription (Validation & Retrofit) - **USER**
- [ ] Création de la table `users` (SQL) - **FRIEND**
- [ ] API d'Inscription (`register.php`) - **FRIEND**
- [ ] Interface de Connexion (Layout XML) - **USER**
- [ ] Logique de Connexion (Retrofit & Session) - **USER**
- [ ] API de Connexion (`login.php`) - **FRIEND**

### Phase 2 : Tableaux de Bord (Dashboard)
- [ ] Interface Dashbord Passager - **USER**
- [ ] Interface Dashboard Conducteur - **USER**
- [ ] Logique de redirection selon le rôle - **USER**
- [ ] API de profil utilisateur - **FRIEND**

### Phase 3 : Publication de Trajets (Conducteur)
- [ ] Formulaire de publication (Départ, Arrivée, Prix, Places) - **USER**
- [ ] API de création de trajet (`create_ride.php`) - **FRIEND**
- [ ] Liste des trajets publiés par le conducteur - **USER**
- [ ] API pour récupérer les trajets - **FRIEND**

### Phase 4 : Recherche et Réservation (Passager)
- [ ] Barre de recherche (Filtres source/destination) - **USER**
- [ ] API de recherche de trajets - **FRIEND**
- [ ] Détails d'un trajet et bouton de réservation - **USER**
- [ ] API de réservation (`book_seat.php`) - **FRIEND**

### Phase 5 : Finalisation
- [ ] Gestion des erreurs et messages utilisateur - **USER & FRIEND**
- [ ] Icône de l'application et Design - **USER**
- [ ] Tests finaux et Déploiement - **USER & FRIEND**

---

## 📝 Mode d'emploi
Modifiez ce fichier sur GitHub en remplaçant `[ ]` par `[x]` quand vous terminez une tâche, puis validez le commit.
