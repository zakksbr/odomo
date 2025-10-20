package odomo;

class Odomo {

    /**
     * Mode courant : météo ou chauffage.
     */
    static String mode;

    /**
     * Mode "météo".
     */
    final static String MODE_METEO = "météo";

    /**
     * Mode "chauffage".
     */
    final static String MODE_CHAUFFAGE = "chauffage";

    /**
     * Mode "saisie des créneaux de chauffage".
     */
    final static String MODE_SAISIE_CHAUFFAGE = "saisie des créneaux de chauffage";

    /**
     * Les jours de la semaine.
     */
    final static String[] JOURS = {"lun", "mar", "mer", "jeu", "ven", "sam", "dim"};

    /**
     * Méthode principale.
     *
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        System.out.print("Initialisation... ");
        // configuration par défaut au démarrage
        mode = MODE_METEO;
        Ecran.typeHisto = Ecran.TYPE_HISTO_PLUVIO_HEURE;
        // chargement des données
        Meteo.initialiser();
        Chauffage.initialiser();
        System.out.println("terminée.");
        // boucle principale
        boolean fin;
        do {
            Ecran.ecranPrincipal();
            char choix = choixUtilisateur();
            fin = traiterChoix(choix);
        } while (!fin);
    }

    /**
     * Demander à l'utilisateur de choisir une action.
     *
     * @return l'action choisie
     */
    static char choixUtilisateur() {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        char choix = ' ';
        boolean saisieCorrecte;
        do {
            System.out.print("Action : ");
            String saisie = sc.next();
            saisieCorrecte = false;
            if (saisie.length() == 1) {
                choix = saisie.charAt(0);
                saisieCorrecte = true;
            }
        } while (!saisieCorrecte);
        return choix;
    }

    /**
     * À partir d'un choix (correct) de l'utilisateur, effectue l'action
     * correspondante.
     *
     * @param choix le choix de l'utilisateur
     * @return vrai si le programme doit ensuite s'arrêter
     */
    static boolean traiterChoix(char choix) {
        boolean fin = false;
        switch (choix) {
            case 'a', 'A' -> aide();
            case 'C', 'c' -> {
                mode = MODE_CHAUFFAGE;
                Ecran.typeHisto = Ecran.TYPE_HISTO_CHAUFFAGE;
            }
            case 'm', 'M', 'p', 'P' -> {
                mode = MODE_METEO;
                Ecran.typeHisto = Ecran.TYPE_HISTO_PLUVIO_HEURE;
            }
            case 'n', 'N' -> {
                mode = MODE_SAISIE_CHAUFFAGE;
                Ecran.typeHisto = Ecran.TYPE_HISTO_CHAUFFAGE;
                Chauffage.saisieCreneaux();
            }
            case 'q', 'Q' -> fin = true;
            case 't', 'T' -> {
                mode = MODE_METEO;
                Ecran.typeHisto = Ecran.TYPE_HISTO_TEMPER_MINUTES;
            }
            default -> aide();
        }
        return fin;
    }

    static void aide() {
        System.out.println("""
            Actions possibles :
            - (A)ide
            - mode (M)étéo :
                       affichage (P)luvio ou (T)empérature
            - mode (C)hauffage :
                       (N) pour modifier un créneau de chauffage en mode Normal
            - (Q)uitter
            """);
    }

    /**
     * Cherche le numéro d'un jour donné (0 pour "lun", 1 pour "mar", etc).
     * Renvoie -1 si le jour est incorrect.
     *
     * @param nomJour le nom du jour sur 3 lettres
     * @return le numéro du jour, ou -1 s'il est incorrect
     */
    static int numeroJour(String nomJour) {
        int numJour = 0;
        while (numJour < 7 && !JOURS[numJour].equals(nomJour)) {
            numJour++;
        }
        if (numJour == 7) {
            numJour = -1;
        }
        return numJour;
    }

}
