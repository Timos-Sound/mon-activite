package fr.miageamiens.isi05.exercice3_sudoku;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import fr.miageamiens.isi05.sudoku.modele.ValeurDeCase;
import fr.miageamiens.isi05.sudoku.modele.exceptions.HorsBornesException;
import fr.miageamiens.isi05.sudoku.modele.exceptions.ValeurInterditeException;
import fr.miageamiens.isi05.sudoku.modele.exceptions.ValeurImpossibleException;
import fr.miageamiens.isi05.sudoku.modele.exceptions.ValeurInitialeModificationException;

public class GrilleImplTest {

    private GrilleImpl grille;
    private final ValeurDeCase VAL_1 = new ValeurDeCaseAsChar('1');
    private final ValeurDeCase VAL_9 = new ValeurDeCaseAsChar('9');
    private final ValeurDeCase VAL_VIDE = new ValeurDeCaseAsChar('.');
    private final ValeurDeCase VAL_BAD = new ValeurDeCaseAsChar('X'); // Valeur non autorisée

    @Before
    public void setUp() {
        grille = new GrilleImpl(); 
    }

    // --- Tests de base et de dimension ---
    
    @Test
    public void testGetDimension() { assertEquals(9, grille.getDimension()); }
    
    @Test
    public void testGetValeursAutorisees() {
        assertTrue(grille.getValeursAutorisees().contains(VAL_VIDE));
        assertEquals(10, grille.getValeursAutorisees().size());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSetValeurHorsLimites() { grille.setValeur(9, 0, VAL_1); } 

    @Test(expected = IllegalArgumentException.class)
    public void testSetValeurInvalideType() {
        grille.setValeur(0, 0, VAL_BAD);
    }
    
    // --- Couverture setValeur---
    
    @Test
    public void testSetValeur_SetInitialTrue() throws HorsBornesException {
        // Couvre: case vide ET nouvelle valeur non vide -> isInitial = true
        grille.setValeur(0, 0, VAL_1); 
        assertTrue(grille.isValeurInitiale(0, 0));
    }
    
    @Test
    public void testSetValeur_CaseDejaRemplie() throws HorsBornesException {
        // Couvre: case non vide ET nouvelle valeur non vide -> if ignoré, isInitial inchangé
        grille.setValeur(0, 0, VAL_1); 
        grille.setValeur(0, 0, VAL_9); 
        assertTrue(grille.isValeurInitiale(0, 0)); 
    }
    
    @Test
    public void testSetValeur_RendreVideCaseVide() throws HorsBornesException {
        // Couvre: case vide ET nouvelle valeur vide -> if ignoré, isInitial inchangé
        assertFalse(grille.isValeurInitiale(0, 1));
        grille.setValeur(0, 1, VAL_VIDE); 
        assertFalse(grille.isValeurInitiale(0, 1));
    }
    
    // --- Tests de complétion et isPossible ---
    
    @Test
    public void testIsComplete() {
        assertFalse(grille.isComplete()); 
        GrilleImpl grillePleine = new GrilleImpl();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grillePleine.setValeur(i, j, VAL_1); 
            }
        }
        assertTrue(grillePleine.isComplete());
    }

    @Test
    public void testIsPossible_Succes() throws HorsBornesException, ValeurInterditeException {
        assertTrue(grille.isPossible(0, 0, VAL_1)); 
    }
    
    @Test
    public void testIsPossible_ValeurVide() throws HorsBornesException, ValeurInterditeException {
        // Couvre la branche if (valeur.equals(VAL_VIDE))
        assertTrue(grille.isPossible(0, 0, VAL_VIDE)); 
    }
    
    @Test(expected = ValeurInterditeException.class)
    public void testIsPossible_ValeurInterdite() throws HorsBornesException, ValeurInterditeException {
        grille.isPossible(0, 0, VAL_BAD); 
    }

    @Test
    public void testIsPossible_VioleeParBloc() throws HorsBornesException, ValeurInterditeException {
        grille.setValeur(1, 1, VAL_1); // Place '1' dans le bloc (0,0)
        assertFalse(grille.isPossible(0, 0, VAL_1)); // '1' n'est plus possible à (0, 0)
    }
    
    @Test
    public void testIsPossible_SurCaseDejaRemplie() throws HorsBornesException, ValeurInterditeException {
        // Couvre: if (!oldValue.equals(VAL_VIDE)) dans isPossible
        grille.setValeur(0, 0, VAL_1); 
        assertTrue(grille.isPossible(0, 0, VAL_9)); // 9 est possible (1 est retiré temporairement pour le test)
        assertEquals('1', ((ValeurDeCaseAsChar) grille.getValeur(0, 0)).getCaractere()); // Vérifie la restauration
    }

    // --- Tests de setValeur1 (Exceptions Métier) ---
    
    @Test
    public void testSetValeur1_Succes() throws Exception {
        grille.setValeur1(1, 1, VAL_9);
        assertEquals('9', ((ValeurDeCaseAsChar) grille.getValeur(1, 1)).getCaractere());
    }
    
    @Test
    public void testSetValeur1_SupprimerValeur() throws Exception {
        // Couvre la branche 'else' (valeur.equals(VAL_VIDE)) dans setValeur1
        grille.setValeur1(1, 1, VAL_9); 
        grille.setValeur1(1, 1, VAL_VIDE); 
        assertEquals('.', ((ValeurDeCaseAsChar) grille.getValeur(1, 1)).getCaractere());
    }
    
    @Test(expected = ValeurInitialeModificationException.class)
    public void testSetValeur1_ModificationInitiale() throws Exception {
        grille.setValeur(0, 0, VAL_1); // Rend la case initiale
        grille.setValeur1(0, 0, VAL_9); // Tenter de modifier
    }

    @Test(expected = ValeurImpossibleException.class)
    public void testSetValeur1_ValeurImpossible() throws Exception {
        grille.setValeur1(0, 0, VAL_1); 
        grille.setValeur1(0, 1, VAL_1); // Viole la contrainte de ligne
    }
}