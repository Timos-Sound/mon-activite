package fr.miageamiens.isi05.exercice3_sudoku;

import static org.junit.Assert.*;
import org.junit.Test;

public class ValeurDeCaseAsCharTest {

    @Test
    public void testGetValeurEtCaractere() {
        ValeurDeCaseAsChar case1 = new ValeurDeCaseAsChar('5');
        // Vérifie getValeur() (retourne Character)
        assertEquals('5', case1.getValeur()); 
        // Vérifie getCaractere() (retourne char)
        assertEquals('5', case1.getCaractere());
    }

    @Test
    public void testToString() {
        ValeurDeCaseAsChar case1 = new ValeurDeCaseAsChar('9');
        assertEquals("9", case1.toString());
    }
    
    @Test
    public void testEqualsEtHashCode() {
        ValeurDeCaseAsChar v1a = new ValeurDeCaseAsChar('1');
        ValeurDeCaseAsChar v1b = new ValeurDeCaseAsChar('1');
        ValeurDeCaseAsChar v2 = new ValeurDeCaseAsChar('2');
        
        // Couverture: this == obj
        assertTrue(v1a.equals(v1a)); 
        
        // Couverture: Comparaison réussie et HashCode cohérent
        assertTrue(v1a.equals(v1b)); 
        assertEquals(v1a.hashCode(), v1b.hashCode());
        
        // Couverture: Valeur différente
        assertFalse(v1a.equals(v2)); 
        
        // Couverture: Objet null
        assertFalse(v1a.equals(null)); 
        
        // Couverture: Type d'objet incorrect
        assertFalse(v1a.equals(new Object())); 
    }
}