package com.follydev.gestiondestock.utils;

public interface Constants {

    public String APP_ROOT = "gestiondestock/v1";

    String COMMANDE_FOURNISSEUR_ENDPOINT = APP_ROOT + "/commandeFournisseurs";
    String CREATE_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/create";
    String FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT= COMMANDE_FOURNISSEUR_ENDPOINT + "/{idCommandeFournisseur}";
    String FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/{codeCommandeFournisseur}";
    String FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/all";
    String DELETE_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/delete/{idCommandeFournisseur}";

    String ENTREPRISE_ENDPOINT = APP_ROOT + "/entreprises";
    String CREATE_ENTREPRISE_ENDPOINT = ENTREPRISE_ENDPOINT + "/create";
    String FIND_ENTREPRISE_BY_ID_ENDPOINT= ENTREPRISE_ENDPOINT + "/{idEntreprise}";
    String FIND_ENTREPRISE_BY_CODE_ENDPOINT = ENTREPRISE_ENDPOINT + "/{codeEntreprise}";
    String FIND_ALL_ENTREPRISE_ENDPOINT = ENTREPRISE_ENDPOINT + "/all";
    String DELETE_ENTREPRISE_ENDPOINT = ENTREPRISE_ENDPOINT + "/delete/{idEntreprise}";

    String FOURNISSEUR_ENDPOINT = APP_ROOT + "/fournisseurs";
    String CREATE_FOURNISSEUR_ENDPOINT = FOURNISSEUR_ENDPOINT + "/create";
    String FIND_FOURNISSEUR_BY_ID_ENDPOINT= FOURNISSEUR_ENDPOINT + "/{idFournisseur}";
    String FIND_FOURNISSEUR_BY_CODE_ENDPOINT = FOURNISSEUR_ENDPOINT + "/{codeFournisseur}";
    String FIND_ALL_FOURNISSEUR_ENDPOINT = FOURNISSEUR_ENDPOINT + "/all";
    String DELETE_FOURNISSEUR_ENDPOINT = FOURNISSEUR_ENDPOINT + "/delete/{idFournisseur}";

    String VENTE_ENDPOINT = APP_ROOT + "/ventes";
    String CREATE_VENTE_ENDPOINT = VENTE_ENDPOINT + "/create";
    String FIND_VENTE_BY_ID_ENDPOINT= VENTE_ENDPOINT + "/{idVente}";
    String FIND_VENTE_BY_CODE_ENDPOINT = VENTE_ENDPOINT + "/{codeVente}";
    String FIND_ALL_VENTE_ENDPOINT = VENTE_ENDPOINT + "/all";
    String DELETE_VENTE_ENDPOINT = VENTE_ENDPOINT + "/delete/{idVente}";

    String UTILISATEUR_ENDPOINT = APP_ROOT + "/utilisateurs";
    String CREATE_UTILISATEUR_ENDPOINT = UTILISATEUR_ENDPOINT + "/create";
    String FIND_UTILISATEUR_BY_ID_ENDPOINT= UTILISATEUR_ENDPOINT + "/{idUtilisateur}";
    String FIND_UTILISATEUR_BY_CODE_ENDPOINT = UTILISATEUR_ENDPOINT + "/{codeUtilisateur}";
    String FIND_ALL_UTILISATEUR_ENDPOINT = UTILISATEUR_ENDPOINT + "/all";
    String DELETE_UTILISATEUR_ENDPOINT = UTILISATEUR_ENDPOINT + "/delete/{idUtilisateur}";

    String AUTHENTICATION_ENDPOINT = APP_ROOT + "/auth";
}
