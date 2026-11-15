package com.dsi3.isetkb.tp4auth;

/**
 * Adresse du serveur PHP.
 *  - Émulateur Android : 10.0.2.2 désigne le PC hôte (localhost du PC).
 *  - Téléphone réel    : remplacez par l'adresse IP locale du PC (ex. 192.168.1.10),
 *                        le PC et le téléphone doivent être sur le même Wi-Fi.
 * Le dossier "tp4" doit être placé dans le dossier www d'EasyPHP.
 */
public final class ServerConfig {

    public static final String HOST = "10.0.2.2";
    public static final String BASE_URL = "http://" + HOST + "/tp4/";

    public static final String LOGIN_URL = BASE_URL + "index.php";
    public static final String REGISTER_URL = BASE_URL + "register.php";

    private ServerConfig() {
    }
}
