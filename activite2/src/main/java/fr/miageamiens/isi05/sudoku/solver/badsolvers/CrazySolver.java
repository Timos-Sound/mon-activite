package fr.miageamiens.isi05.sudoku.solver.badsolvers;

import fr.miageamiens.isi05.sudoku.modele.Grille;
import fr.miageamiens.isi05.sudoku.solver.Solver;
import fr.miageamiens.isi05.sudoku.solver.Stoppable;

public final class CrazySolver implements Solver, Stoppable {
   private volatile boolean stop = false;

   public boolean solve(Grille grille) {
      long waitTimeInLoop = 1000L;

      while(!this.stop) {
         try {
            Thread.sleep(1000L);
         } catch (InterruptedException e) {
            throw new RuntimeException(e);
         }
      }

      return false;
   }

   public void stop() {
      this.stop = true;
   }
}
