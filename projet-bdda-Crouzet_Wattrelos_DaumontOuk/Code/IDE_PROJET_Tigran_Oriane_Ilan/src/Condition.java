public class Condition { //Represente les conditions de la commande "select"
    private String nomColonne, valeur,operateur;

    public Condition(String nomColonne, String operateur, String valeur){
        this.nomColonne = nomColonne;
        this.operateur = operateur;
        this.valeur = valeur;
    }

    public String getNomColonne() {
        return nomColonne;
    }
    public String getValeur() {
        return valeur;
    }
    public String getOperateur() {
        return operateur;
    }
}
