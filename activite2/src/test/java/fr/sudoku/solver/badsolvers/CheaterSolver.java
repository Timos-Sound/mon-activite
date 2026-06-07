package fr.miageamiens.isi05.sudoku.solver.badsolvers;

import fr.miageamiens.isi05.sudoku.modele.Grille;
import fr.miageamiens.isi05.sudoku.modele.ValeurDeCase;
import fr.miageamiens.isi05.sudoku.modele.exceptions.GrilleException;
import fr.miageamiens.isi05.sudoku.solver.Solver;
import fr.miageamiens.isi05.sudoku.solver.SolverException;
import java.util.ArrayList;
import java.util.List;

public final class CheaterSolver implements Solver {
   public boolean solve(Grille grille) throws SolverException {
      try {
         for(int i = 0; i < grille.getDimension(); ++i) {
            for(int j = 0; j < grille.getDimension(); ++j) {
               grille.setValeur(i, j, (ValeurDeCase)null);
            }
         }

         List<ValeurDeCase> valeursAutorisees = new ArrayList(grille.getValeursAutorisees());

         for(int i = 0; i < grille.getDimension(); ++i) {
            int shift = i * grille.getSousDimension() + i / grille.getSousDimension();

            for(int j = 0; j < grille.getDimension(); ++j) {
               int p = (shift + j) % valeursAutorisees.size();
               grille.setValeur(i, j, (ValeurDeCase)valeursAutorisees.get(p));
            }
         }

         return true;
      } catch (GrilleException e) {
         throw new SolverException(e);
      }
   }
}
