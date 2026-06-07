package fr.miageamiens.isi05.sudoku.solver;

import fr.miageamiens.isi05.sudoku.modele.Grille;

public interface Solver {
   boolean solve(Grille var1) throws SolverException;
}
