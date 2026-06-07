package fr.miageamiens.isi05.sudoku.modele.exceptions;

public class GrilleException extends Exception {
   protected GrilleException(Exception e) {
      super(e);
   }

   protected GrilleException(String msg) {
      super(msg);
   }
}
