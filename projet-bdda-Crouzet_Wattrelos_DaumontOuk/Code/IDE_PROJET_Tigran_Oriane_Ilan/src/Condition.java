public class Condition { //Represente les conditions de la commande "select"
    private String nomColonne, valeur;

    public Condition(String nomColonne, String valeur) {
        this.nomColonne = nomColonne;
        this.valeur = valeur;
    }

    public String getNomColonne() {
        return nomColonne;
    }
    public String getValeur() {
        return valeur;
    }
}
