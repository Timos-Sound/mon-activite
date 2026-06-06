/**
 * Interface représentant une grille de jeu.
 */
public interface Grille {
    /**
     * Récupère l'élément de grille aux coordonnées spécifiées.
     * @param x L'indice de ligne.
     * @param y L'indice de colonne.
     * @return L'élément de grille aux coordonnées spécifiées.
     * @throws HorsBornesException Si les coordonnées
     * sont en dehors des limites de la grille.
     */
    ElementDeGrille getValue(int x, int y) throws HorsBornesException;

    /**
     * Modifie l'élément de grille aux coordonnées spécifiées.
     * @param x L'indice de ligne.
     * @param y L'indice de colonne.
     * @param element Le nouvel élément de grille.
     * @throws HorsBornesException Si les coordonnées sont
     * en dehors des limites de la grille.
     * @throws ValeurImpossibleException Si l'élément ne respecte
     * pas les règles de la grille.
     * @throws ElementInterditException Si l'élément ne peut pas
     * être placé dans la grille.
     * @throws ValeurInitialeModificationException Si une tentative est faite pour
     * modifier une valeur initiale de la grille.
     */
    void setValue(int x, int y, ElementDeGrille element) throws HorsBornesException, ValeurImpossibleException, ElementInterditException, ValeurInitialeModificationException;

   /**
     * Récupère la dimension de la grille.
     *
     * @return La dimension de la grille.
     */
    int getDimension();
}
