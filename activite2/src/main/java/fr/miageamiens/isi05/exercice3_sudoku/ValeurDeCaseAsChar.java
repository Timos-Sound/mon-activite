package fr.miageamiens.isi05.exercice3_sudoku;

import fr.miageamiens.isi05.sudoku.modele.ValeurDeCase;
import java.util.Objects;

public class ValeurDeCaseAsChar implements ValeurDeCase {
    private final char caractere;

    public ValeurDeCaseAsChar(char caractere) {
        this.caractere = caractere;
    }

    public char getCaractere() {
        return this.caractere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValeurDeCaseAsChar that = (ValeurDeCaseAsChar) o;
        return caractere == that.caractere;
    }

    @Override
    public int hashCode() {
        return Objects.hash(caractere);
    }

    @Override
    public String toString() {
        return String.valueOf(caractere);
    }
}
