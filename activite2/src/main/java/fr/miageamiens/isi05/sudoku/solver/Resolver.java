package fr.miageamiens.isi05.sudoku.solver;

import fr.miageamiens.isi05.sudoku.modele.Grille;
import fr.miageamiens.isi05.sudoku.modele.ValeurDeCase;
import fr.miageamiens.isi05.sudoku.modele.exceptions.GrilleException;

/**
 * Implémentation de l'interface Solver pour résoudre les grilles de Sudoku.
 */
public class Resolver implements Solver {

    /**
     * Point d'entrée de la résolution demandé par l'interface Solver.
     *
     * @param grille La grille de Sudoku à résoudre.
     * @return true si la grille est résolue avec succès, sinon false.
     * @throws SolverException Si une erreur survient pendant le calcul.
     */
    @Override
    public boolean solve(Grille grille) throws SolverException {
        if (grille == null) {
            throw new SolverException(new IllegalArgumentException("La grille ne peut pas être nulle."));
        }
        try {
            // Lancement du traitement à partir de la première case (0, 0)
            return resoudreSudoku(grille, 0, 0);
        } catch (GrilleException e) {
            throw new SolverException(e);
        }
    }

    /**
     * Méthode récursive de parcours en profondeur (Backtracking).
     */
    private boolean resoudreSudoku(Grille grille, int ligne, int colonne) throws GrilleException {
        int dimension = grille.getDimension();

        // Si nous avons dépassé la dernière ligne, la grille complète est résolue
        if (ligne == dimension) {
            return true;
        }

        // Calcul automatique des coordonnées de la case suivante (balayage horizontal)
        int prochaineLigne = (colonne == dimension - 1) ? ligne + 1 : ligne;
        int prochaineColonne = (colonne == dimension - 1) ? 0 : colonne + 1;

        // Si la case contient déjà une valeur initiale ou fixe, on passe à la suivante
        if (grille.isValeurInitiale(ligne, colonne) || grille.getValeur(ligne, colonne) != null) {
            return resoudreSudoku(grille, prochaineLigne, prochaineColonne);
        }

        // Parcours de toutes les valeurs autorisées fournies directement par l'objet Grille
        for (ValeurDeCase option : grille.getValeursAutorisees()) {
            
            // Utilisation de la méthode métier officielle pour valider la possibilité du placement
            if (grille.isPossible(ligne, colonne, option)) {
                
                // On applique l'option candidate
                grille.setValeur(ligne, colonne, option);

                // Analyse récursive du reste de la grille avec cette option
                if (resoudreSudoku(grille, prochaineLigne, prochaineColonne)) {
                    return true;
                }

                // BACKTRACKING : En cas d'échec ultérieur, on réinitialise la case à vide (null)
                grille.setValeur(ligne, colonne, null);
            }
        }

        // Aucune option ne convient, déclenchement du retour en arrière vers la case précédente
        return false;
    }
}
