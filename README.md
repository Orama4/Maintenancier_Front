# 📱 Application Mobile - Maintenancier (Frontend Kotlin)

Ce dépôt contient le **code source de l’application Android** destinée aux maintenanciers. Elle est développée en **Kotlin** avec **Jetpack Compose** en suivant l’architecture **MVVM (Model - ViewModel - Repository)**.

---

## 🧩 Fonctionnalités principales

1. **Authentification sécurisée**
   - Connexion avec email et mot de passe
   - Inscription avec vérification par code OTP
   - Réinitialisation et changement de mot de passe

2. **Tableau de bord (HomeScreen)**
   - Nombre total de dispositifs
   - Nombre de dispositifs connectés
   - Nombre de dispositifs déconnectés
   - Liste restreinte des dispositifs avec statut et MAC
   - Bouton « Plus d’infos » pour chaque dispositif
   - Historique des interventions par dispositif

3. **Liste des Tâches**
   - Tâches générées automatiquement lors de pannes
   - Statuts : `Not taken`, `In progress`, `Completed`
   - Prendre une tâche en définissant une date
   - Marquer une tâche comme terminée avec description

4. **Gestion de Profil**
   - Visualisation et modification des informations
   - Changement de mot de passe
   - Suppression du compte

5. **Assistance & Support**
   - Section FAQ
   - Contact Support

6. **Notifications Push (via Firebase - si activé)**

7. **Déconnexion accessible depuis toutes les pages**

---

## 🏗️ Architecture du projet

Le projet suit une organisation claire en couches MVVM :

app/
├── api/ # Interfaces Retrofit pour les appels réseau
│ └── AuthApiService.kt, DeviceApiService.kt...
├── entities/ # Modèles de données : Device, Task, User...
├── repositories/ # Logique de récupération des données API
├── viewmodels/ # Logique de présentation pour chaque écran
├── ui/
│ ├── screens/ # Écrans Compose : Login, Dashboard, etc.
│ └── components/ # Composants UI réutilisables
├── navigation/ # Système de navigation entre écrans
├── utils/ # Fonctions utilitaires
├── theme/ # Thèmes et styles Material 3
└── MainActivity.kt # Point d’entrée de l’application


Vérifier l’URL de base de l’API dans RetrofitClient.kt :
Dans le fichier api/RetrofitClient changer la valeur de la variable BASE_URL

private const val BASE_URL = "http://<adresse-de-votre-backend>"
