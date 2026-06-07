

/**
 * Implémentation de l'interface Solveur pour résoudre les grilles de Sudoku.
 */
public class Resolveur implements Solver {

    /**
     * Résout la grille de Sudoku donnée.
     *
     * @param grille La grille de Sudoku à résoudre.
     * @return true si la grille est résolue avec succès, sinon false.
     * @throws SolveurException Si une erreur se produit lors de la résolution.
     */
    @Override
    public boolean solve(Grille grille) throws SolveurException {
        // On commence la résolution à la case (0, 0)
        return solveSudoku(grille, 0, 0);
    }

    /**
     * Méthode récursive pour résoudre la grille de Sudoku.
     * Gère la progression ligne par ligne et colonne par colonne.
     *
     * @param grille La grille de Sudoku.
     * @param row La ligne actuelle.
     * @param col La colonne actuelle.
     * @return true si la grille est résolue, sinon false.
     */
    private boolean solveSudoku(Grille grille, int row, int col) {
        int dimension = grille.getDimension();

        // Si on a atteint la fin de la ligne, on passe à la ligne suivante
        if (col == dimension) {
            col = 0;
            row++;
        }

        // Si on a parcouru toutes les lignes, la grille est résolue avec succès
        if (row == dimension) {
            return true;
        }

        try {
            // Si la case actuelle n'est pas vide (déjà remplie par l'énoncé), 
            // on passe directement à la case suivante sur la même ligne
            if (grille.getValue(row, col) != null && grille.getValue(row, col).getValue() != '.') {
                return solveSudoku(grille, row, col + 1);
            }

            // Si la case est vide, on tente d'y placer un chiffre de 1 à 9
            for (int num = 1; num <= 9; num++) {
                if (isValid(grille, row, col, num)) {
                    
                    // On applique le chiffre (conversion de int à char)
                    grille.setValue(row, col, new ElementDeGrilleImplAsChar((char) (num + '0')));

                    // Appel récursif pour la case suivante (colonne + 1)
                    if (solveSudoku(grille, row, col + 1)) {
                        return true; // Solution trouvée, on remonte la pile d'appels
                    }

                    // BACKTRACKING : Si le chiffre testé mène à un blocage, 
                    // on réinitialise la case à '.' et on passe au chiffre suivant
                    grille.setValue(row, col, new ElementDeGrilleImplAsChar('.'));
                }
            }
        } catch (HorsBornesException e) {
            // Gestion de l'exception liée à la manipulation de la grille
            e.printStackTrace();
        }

        return false; // Aucune solution valide trouvée pour cette branche, on revient en arrière
    }

    /**
     * Vérifie si un nombre peut être placé dans une position donnée de la grille.
     *
     * @param grille La grille de Sudoku.
     * @param row La ligne de la position.
     * @param col La colonne de la position.
     * @param num Le nombre à vérifier.
     * @return true si le nombre peut être placé, sinon false.
     */
    private boolean isValid(Grille grille, int row, int col, int num) {
        // Un chiffre est valide s'il n'est présent ni dans sa ligne, ni dans sa colonne, ni dans sa région 3x3
        return !isInRow(grille, row, num) && 
               !isInCol(grille, col, num) && 
               !isInRegion(grille, row - row % 3, col - col % 3, num);
    }

    /**
     * Vérifie s'il y a un doublon dans la ligne.
     */
    private boolean isInRow(Grille grille, int row, int num) {
        try {
            for (int col = 0; col < grille.getDimension(); col++) {
                if (grille.getValue(row, col) != null && grille.getValue(row, col).getValue() == (char) (num + '0')) {
                    return true;
                }
            }
        } catch (HorsBornesException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Vérifie s'il y a un doublon dans la colonne.
     */
    private boolean isInCol(Grille grille, int col, int num) {
        try {
            for (int row = 0; row < grille.getDimension(); row++) {
                if (grille.getValue(row, col) != null && grille.getValue(row, col).getValue() == (char) (num + '0')) {
                    return true;
                }
            }
        } catch (HorsBornesException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Vérifie s'il y a un doublon dans la région 3x3.
     */
    private boolean isInRegion(Grille grille, int startRow, int startCol, int num) {
        try {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (grille.getValue(row + startRow, col + startCol) != null && 
                        grille.getValue(row + startRow, col + startCol).getValue() == (char) (num + '0')) {
                        return true;
                    }
                }
            }
        } catch (HorsBornesException e) {
            e.printStackTrace();
        }
        return false;
    }
}
