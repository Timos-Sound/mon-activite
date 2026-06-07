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
        return solveSudoku(grille, 0, 0);
    }

    /**
     * Méthode récursive pour résoudre la grille de Sudoku.
     *
     * @param grille La grille de Sudoku.
     * @param row La ligne actuelle.
     * @param col La colonne actuelle.
     * @return true si la grille est résolue, sinon false.
     */
    private boolean solveSudoku(Grille grille, int row, int col) {
        // Trouver la prochaine case vide
        if (!findEmptyCell(grille, row, col)) {
            return true; // Toutes les cases sont remplies, la grille est résolue
        }

        for (int num = 1; num <= 9; num++) {
            // Vérifier si le nombre peut être attribué
            if (isValid(grille, row, col, num)) {
                // Attribuer le nombre et récursion
                grille.setValue(row, col, new ElementDeGrilleImplAsChar((char) (num + '0')));

                // Résoudre récursivement le reste de la grille
                if (solveSudoku(grille, row, col)) {
                    return true; // La grille est résolue
                }

                // Backtracking
                grille.setValue(row, col, new ElementDeGrilleImplAsChar('.'));
            }
        }

        return false; // Aucune solution trouvée pour cette case
    }

    /**
     * Trouve la prochaine case vide dans la grille.
     *
     * @param grille La grille de Sudoku.
     * @param row La ligne actuelle.
     * @param col La colonne actuelle.
     * @return true si une case vide est trouvée, sinon false.
     */
    private boolean findEmptyCell(Grille grille, int row, int col) {
        int dimension = grille.getDimension();

        for (; row < dimension; col = 0, row++) {
            for (; col < dimension; col++) {
                try {
                    if (grille.getValue(row, col) != null && grille.getValue(row, col).getValue() == '.') {
                        return true; // Case vide trouvée
                    }
                } catch (HorsBornesException e) {
                    e.printStackTrace();
                }
            }
        }

        return false; // Aucune case vide trouvée
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
        // Vérifiez s'il n'y a pas de doublons dans la ligne, la colonne et la région
        return !isInRow(grille, row, num) && !isInCol(grille, col, num) && !isInRegion(grille, row - row % 3, col - col % 3, num);
    }

    /**
     * Vérifie s'il y a un doublon dans la ligne.
     *
     * @param grille La grille de Sudoku.
     * @param row La ligne à vérifier.
     * @param num Le nombre à vérifier.
     * @return true s'il y a un doublon, sinon false.
     */
    private boolean isInRow(Grille grille, int row, int num) {
        for (int col = 0; col < grille.getDimension(); col++) {
            if (grille.getValue(row, col).getValue() == (char) (num + '0')) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie s'il y a un doublon dans la colonne.
     *
     * @param grille La grille de Sudoku.
     * @param col La colonne à vérifier.
     * @param num Le nombre à vérifier.
     * @return true s'il y a un doublon, sinon false.
     */
    private boolean isInCol(Grille grille, int col, int num) {
        for (int row = 0; row < grille.getDimension(); row++) {
            if (grille.getValue(row, col).getValue() == (char) (num + '0')) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie s'il y a un doublon dans la région 3x3.
     *
     * @param grille La grille de Sudoku.
     * @param startRow La ligne de départ de la région.
     * @param startCol La colonne de départ de la région.
     * @param num Le nombre à vérifier.
     * @return true s'il y a un doublon, sinon false.
     */
    private boolean isInRegion(Grille grille, int startRow, int startCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (grille.getValue(row + startRow, col + startCol).getValue() == (char) (num + '0')) {
                    return true;
                }
            }
        }
        return false;
    }
}
