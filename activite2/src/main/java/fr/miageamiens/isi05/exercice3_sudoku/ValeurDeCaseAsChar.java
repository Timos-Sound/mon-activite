package fr.miageamiens.isi05.exercice3_sudoku;

import fr.miageamiens.isi05.sudoku.modele.ValeurDeCase;

/**
 * Implementation d'un élément de grille qui utilise un caractère ('1'...'9' ou '.').
 * La comparaison entre objets se fait via la méthode standard equals().
 */
public class ValeurDeCaseAsChar implements ValeurDeCase {
    
    private final char valeur;

    /**
     * Constructeur.
     */
    public ValeurDeCaseAsChar(char valeur) {
        this.valeur = valeur;
    }

    /**
     * Retourne la valeur de la case sous forme d'objet (Character).
     */
    public Object getValeur() {
        return this.valeur;
    }

    /**
     * Retourne la valeur de la case sous forme de caractère primitif.
     */
    public char getCaractere() {
        return this.valeur;
    }
    
    // NOTE: La méthode isEgal(ValeurDeCase autre) a été supprimée
    // car elle n'existe pas dans l'interface ValeurDeCase.
    // Toute comparaison doit utiliser la méthode standard equals().

    /**
     * Surcharge de la méthode standard equals() pour garantir l'égalité basée sur la valeur du caractère.
     * @return true si les valeurs des deux objets sont identiques.
     */
    @Override
    public boolean equals(Object obj) {
        // 1. Couverture: Même référence
        if (this == obj) return true; 

        // 2. Couverture: obj est null OU le type est incorrect
        if (obj == null || !(obj instanceof ValeurDeCaseAsChar)) return false;
        
        ValeurDeCaseAsChar that = (ValeurDeCaseAsChar) obj;
        
        // 3. Couverture: Comparaison de la valeur interne
        return this.valeur == that.valeur;
    }

    /**
     * Surcharge de hashCode() en cohérence avec equals().
     */
    @Override
    public int hashCode() {
        return Character.hashCode(valeur);
    }
    
    /**
     * Retourne la représentation en chaîne de caractères.
     */
    @Override
    public String toString() {
        return String.valueOf(valeur);
    }
}
