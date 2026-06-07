package fr.miageamiens.isi05.exercice3_sudoku;

import fr.miageamiens.isi05.sudoku.modele.Grille;
import fr.miageamiens.isi05.sudoku.modele.ValeurDeCase;
import fr.miageamiens.isi05.sudoku.modele.exceptions.HorsBornesException;
import fr.miageamiens.isi05.sudoku.modele.exceptions.ValeurImpossibleException;
import fr.miageamiens.isi05.sudoku.modele.exceptions.ValeurInitialeModificationException;
import fr.miageamiens.isi05.sudoku.modele.exceptions.ValeurInterditeException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Implémentation de la grille de Sudoku 9x9.
 */
public class GrilleImpl implements Grille {
  /** Dimension de la grille. */
  private static final int DIMENSION = 9;
  /** Dimension des sous-blocs. */
  private static final int SOUS_DIMENSION = 3;
  /** Valeur représentant une case vide. */
  private static final ValeurDeCase VAL_VIDE = new ValeurDeCaseAsChar('.');

  private final ValeurDeCase[][] grille;
  private final boolean[][] isInitial;
  private final Set<ValeurDeCase> valeursAutorisees;
  private final ValeurDeCase[] valeursPossibles;

  /**
   * Constructeur. Initialise une grille 9x9 vide.
   */
  public GrilleImpl() {
    this.grille = new ValeurDeCase[DIMENSION][DIMENSION];
    this.isInitial = new boolean[DIMENSION][DIMENSION];

    // Initialisation des valeurs autorisées ('1' à '9' et '.')
    this.valeursAutorisees = new HashSet<>();
    this.valeursAutorisees.add(VAL_VIDE);
    this.valeursPossibles = new ValeurDeCase[DIMENSION];

    for (int i = 0; i < DIMENSION; i++) {
      char c = (char) ('1' + i);
      ValeurDeCase v = new ValeurDeCaseAsChar(c);
      this.valeursAutorisees.add(v);
      this.valeursPossibles[i] = v;
    }

    // Initialisation des cases à VAL_VIDE
    for (int i = 0; i < DIMENSION; i++) {
      for (int j = 0; j < DIMENSION; j++) {
        this.grille[i][j] = VAL_VIDE;
        this.isInitial[i][j] = false;
      }
    }
  }

    // -------------------------------------------------------------------------
    // Méthodes de Vérification des Bornes
    // -------------------------------------------------------------------------

    /**
     * Vérifie si les coordonnées (x, y) sont dans les limites [0, 8].
     *
     * @param x Coordonnée x (ligne).
     * @param y Coordonnée y (colonne).
     * @throws HorsBornesException Si les coordonnées sont invalides.
     */
  private void verifierBornes(final int x, final int y) throws HorsBornesException {
    if (x < 0 || x >= DIMENSION || y < 0 || y >= DIMENSION) {
      throw new HorsBornesException(
     "Coordonnées (" + x + ", " + y + ") hors des bornes [0," + (DIMENSION - 1) + "]");
    }
  }

    // -------------------------------------------------------------------------
    // Implémentation de l'interface Grille
    // -------------------------------------------------------------------------

  @Override
  public int getDimension() {
    return DIMENSION;
  }

  @Override
  public int getSousDimension() {
    return SOUS_DIMENSION;
  }

  @Override
  public Collection<ValeurDeCase> getValeursAutorisees() {
    return this.valeursAutorisees;
  }

  /**
   * Retourne le tableau des valeurs possibles (de '1' à '9').
   * @return Tableau des valeurs possibles.
   */
  public ValeurDeCase[] getValeursPossibles() {
    return this.valeursPossibles;
  }

  @Override
  public ValeurDeCase getValeur(final int x, final int y) {
    try {
      this.verifierBornes(x, y);
    } catch (HorsBornesException e) {
      // Conversion en IllegalArgumentException
      throw new IllegalArgumentException(e.getMessage(), e);
      }
    return this.grille[x][y];
  }

    /**
     * Définit la valeur. Marque la case comme 'initiale' si elle devient remplie
     * alors qu'elle était vide.
     * @param x Coordonnée x (ligne).
     * @param y Coordonnée y (colonne).
     * @param valeur La valeur à définir.
     */
  @Override
  public void setValeur(final int x, final int y, final ValeurDeCase valeur) {
    try {
	  this.verifierBornes(x, y);
	} catch (HorsBornesException e) {
	  throw new IllegalArgumentException(e.getMessage(), e);
	  }

	  // Vérifie que la valeur est autorisée
	  if (!this.getValeursAutorisees().contains(valeur)) {
		  throw new IllegalArgumentException("Valeur non autorisée : " + valeur.toString());
	  }

	  // Marque la case comme 'initiale' si elle était vide ET la nouvelle valeur est non-vide.
	  if (this.grille[x][y].equals(VAL_VIDE) && !valeur.equals(VAL_VIDE)) {
		  this.isInitial[x][y] = true;
	  }

	  this.grille[x][y] = valeur;
  }

    @Override
    public boolean isComplete() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if (this.grille[i][j].equals(VAL_VIDE)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isValeurInitiale(final int x, final int y) throws HorsBornesException {
        this.verifierBornes(x, y);
        return this.isInitial[x][y];
    }

    @Override
    public boolean isPossible(final int x, final int y, final ValeurDeCase valeur)
            throws HorsBornesException, ValeurInterditeException {
        this.verifierBornes(x, y);

        // Cas où la valeur est vide
        if (valeur.equals(VAL_VIDE)) {
            return true;
        }

        // Cas où la valeur n'est pas autorisée
        if (!this.valeursAutorisees.contains(valeur)) {
            throw new ValeurInterditeException("Valeur non autorisée : " + valeur.toString());
        }

        // Simule l'effacement de la case actuelle pour vérifier les contraintes externes
        ValeurDeCase oldValue = this.grille[x][y];

        if (!oldValue.equals(VAL_VIDE)) {
            this.grille[x][y] = VAL_VIDE; // Simule l'effacement
        }

        boolean isSafe = this.isSafe(x, y, valeur);

        // Restaure la valeur originale
        if (!oldValue.equals(VAL_VIDE)) {
            this.grille[x][y] = oldValue;
        }

        return isSafe;
    }

    /**
     * Vérifie les règles Sudoku (lignes, colonnes, blocs) autour de (row, col) pour
     * 'val'.
     * @param row Ligne de la case.
     * @param col Colonne de la case.
     * @param val Valeur à vérifier.
     * @return true si la valeur ne viole aucune contrainte, false sinon.
     */
    private boolean isSafe(final int row, final int col, final ValeurDeCase val) {
        // Vérification de la ligne
        for (int c = 0; c < DIMENSION; c++) {
            if (c != col && this.grille[row][c].equals(val)) {
                return false; // Contrainte de ligne violée
            }
        }

        // Vérification de la colonne
        for (int r = 0; r < DIMENSION; r++) {
            if (r != row && this.grille[r][col].equals(val)) {
                return false; // Contrainte de colonne violée
            }
        }

        // Vérification du bloc 3x3
        int startRow = row - row % SOUS_DIMENSION;
        int startCol = col - col % SOUS_DIMENSION;
        for (int r = 0; r < SOUS_DIMENSION; r++) {
            for (int c = 0; c < SOUS_DIMENSION; c++) {
                int currRow = startRow + r;
                int currCol = startCol + c;

                // Vérifie que ce n'est pas la case actuelle et qu'il y a un conflit
                if (!(currRow == row && currCol == col) && this.grille[currRow][currCol].equals(val)) {
                    return false; // Contrainte de bloc violée
                }
            }
        }

        return true;
    }

    /**
     * Définit la valeur, propageant les exceptions métier spécifiques.
     * @param x Coordonnée x (ligne).
     * @param y Coordonnée y (colonne).
     * @param valeur La valeur à définir.
     * @throws ValeurImpossibleException Si la valeur viole une contrainte Sudoku.
     * @throws ValeurInterditeException Si la valeur n'est pas autorisée.
     * @throws HorsBornesException Si les coordonnées sont hors limites.
     * @throws ValeurInitialeModificationException Si on tente de modifier une case initiale.
     */
    public void setValeur1(final int x, final int y, final ValeurDeCase valeur)
            throws ValeurImpossibleException, ValeurInterditeException,
            HorsBornesException, ValeurInitialeModificationException {

        this.verifierBornes(x, y);

        // 1. Vérification de la modification d'une case initiale
        if (this.isValeurInitiale(x, y)) {
            throw new ValeurInitialeModificationException(
                    "La case (" + x + ", " + y + ") est une valeur initiale et ne peut être modifiée.");
        }

        // 2. Vérification de l'autorisation générale de la valeur
        if (!this.getValeursAutorisees().contains(valeur)) {
            throw new ValeurInterditeException("Valeur non reconnue : " + valeur.toString());
        }

        // 3. Logique de définition de la valeur

        if (!valeur.equals(VAL_VIDE)) {
            // Tentative de remplir (cas non-vide)

            ValeurDeCase oldValue = this.grille[x][y];
            this.grille[x][y] = VAL_VIDE; // Retrait temporaire pour la vérification isSafe

            if (!this.isSafe(x, y, valeur)) {
                this.grille[x][y] = oldValue; // Restaure l'ancienne valeur
                throw new ValeurImpossibleException("La valeur " + valeur.toString()
                        + " est impossible à la position (" + x + ", " + y + ") selon les règles du Sudoku.");
            }
            // Si la valeur est sûre, on la définit
            this.grille[x][y] = valeur;
        } else {
            // Couverture: Tentative d'effacer (cas VAL_VIDE)
            this.grille[x][y] = VAL_VIDE;
        }
    }
}
