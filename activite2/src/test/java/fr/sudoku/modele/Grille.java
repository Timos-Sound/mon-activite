package fr.miageamiens.isi05.sudoku.modele;

import fr.miageamiens.isi05.sudoku.modele.exceptions.HorsBornesException;
import fr.miageamiens.isi05.sudoku.modele.exceptions.ValeurImpossibleException;
import fr.miageamiens.isi05.sudoku.modele.exceptions.ValeurInitialeModificationException;
import fr.miageamiens.isi05.sudoku.modele.exceptions.ValeurInterditeException;
import java.util.Collection;

public interface Grille {
   Collection<ValeurDeCase> getValeursAutorisees();

   int getDimension();

   int getSousDimension();

   void setValeur(int var1, int var2, ValeurDeCase var3) throws ValeurImpossibleException, ValeurInterditeException, HorsBornesException, ValeurInitialeModificationException;

   ValeurDeCase getValeur(int var1, int var2) throws HorsBornesException;

   boolean isComplete();

   boolean isPossible(int var1, int var2, ValeurDeCase var3) throws HorsBornesException, ValeurInterditeException;

   boolean isValeurInitiale(int var1, int var2) throws HorsBornesException;
}
