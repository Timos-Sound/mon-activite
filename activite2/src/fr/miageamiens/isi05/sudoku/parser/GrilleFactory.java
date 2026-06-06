package fr.miageamiens.isi05.sudoku.parser;

import fr.miageamiens.isi05.sudoku.modele.Grille;
import fr.miageamiens.isi05.sudoku.modele.ValeurDeCase;
import java.util.Collection;

public interface GrilleFactory {
   Grille create(Collection<ValeurDeCase> var1, ValeurDeCase[][] var2);
}
