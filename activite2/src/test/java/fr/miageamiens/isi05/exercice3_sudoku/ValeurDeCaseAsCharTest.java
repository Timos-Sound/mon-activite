package fr.miageamiens.isi05.exercice3_sudoku;

import static org.junit.Assert.*;
import org.junit.Test;

public class ValeurDeCaseAsCharTest {

    @Test
    public void testGetCaractere() {
        ValeurDeCaseAsChar v = new ValeurDeCaseAsChar('5');
        assertEquals('5', v.getCaractere());
    }

    @Test
    public void testToString() {
        ValeurDeCaseAsChar v = new ValeurDeCaseAsChar('A');
        assertEquals("A", v.toString());
    }

    @Test
    public void testEquals_MemeInstance() {
        ValeurDeCaseAsChar v = new ValeurDeCaseAsChar('1');
        // Test de la branche 'if (this == o) return true;'
        assertTrue(v.equals(v));
    }

    @Test
    public void testEquals_NullEtAutreClasse() {
        ValeurDeCaseAsChar v = new ValeurDeCaseAsChar('1');
        // Test de la branche 'if (o == null || getClass() != o.getClass()) return false;'
        assertFalse(v.equals(null));
        assertFalse(v.equals("Une simple chaine"));
    }

    @Test
    public void testEquals_ValeursIdentiquesEtDifferentes() {
        ValeurDeCaseAsChar v1 = new ValeurDeCaseAsChar('9');
        ValeurDeCaseAsChar v2 = new ValeurDeCaseAsChar('9');
        ValeurDeCaseAsChar v3 = new ValeurDeCaseAsChar('2');

        // Cas nominal vrai
        assertTrue(v1.equals(v2));
        // Cas nominal faux (caractère différent)
        assertFalse(v1.equals(v3));
    }

    @Test
    public void testHashCode() {
        ValeurDeCaseAsChar v1 = new ValeurDeCaseAsChar('4');
        ValeurDeCaseAsChar v2 = new ValeurDeCaseAsChar('4');
        ValeurDeCaseAsChar v3 = new ValeurDeCaseAsChar('7');

        // Deux objets égaux doivent retourner le même contrat de hashcode
        assertEquals(v1.hashCode(), v2.hashCode());
        // Deux objets différents ont généralement des hashcodes différents
        assertNotEquals(v1.hashCode(), v3.hashCode());
    }
}
