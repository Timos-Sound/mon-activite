package fr.miageamiens.isi05.sudoku.solver.badsolvers;

import fr.miageamiens.isi05.sudoku.modele.Grille;
import fr.miageamiens.isi05.sudoku.modele.ValeurDeCase;
import fr.miageamiens.isi05.sudoku.modele.exceptions.GrilleException;
import fr.miageamiens.isi05.sudoku.solver.Solver;
import fr.miageamiens.isi05.sudoku.solver.SolverException;
import java.util.ArrayList;
import java.util.List;

public final class RogueSolver implements Solver {
   public boolean solve(Grille grille) throws SolverException {
      try {
         List<ValeurDeCase> valeursAutorisees = new ArrayList(grille.getValeursAutorisees());
         ValeurDeCase valeurDeCase = (ValeurDeCase)valeursAutorisees.get(0);

         for(int i = 0; i < grille.getDimension(); ++i) {
            for(int j = 0; j < grille.getDimension(); ++j) {
               if (!grille.isValeurInitiale(i, j)) {
                  grille.setValeur(i, j, valeurDeCase);
               }
            }
         }

         return true;
      } catch (GrilleException e) {
         throw new SolverException(e);
      }
   }
}
