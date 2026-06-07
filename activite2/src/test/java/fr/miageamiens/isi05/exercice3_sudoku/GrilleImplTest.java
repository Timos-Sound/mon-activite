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
    private final ValeurDeCase VAL_2 = new ValeurDeCaseAsChar('2');
    private final ValeurDeCase VAL_9 = new ValeurDeCaseAsChar('9');
    private final ValeurDeCase VAL_VIDE = new ValeurDeCaseAsChar('.');
    private final ValeurDeCase VAL_BAD = new ValeurDeCaseAsChar('X'); // Valeur non autorisée

    @Before
    public void setUp() {
        grille = new GrilleImpl(); 
    }

    // --- Tests de base, dimensions et structures ---
    
    @Test
    public void testGetDimension() { 
        assertEquals(9, grille.getDimension()); 
    }

    @Test
    public void testGetSousDimension() { 
        assertEquals(3, grille.getSousDimension()); 
    }
    
    @Test
    public void testGetValeursAutorisees() {
        assertTrue(grille.getValeursAutorisees().contains(VAL_VIDE));
        assertEquals(10, grille.getValeursAutorisees().size());
    }

    @Test
    public void testGetValeursPossibles() {
        ValeurDeCase[] possibles = grille.getValeursPossibles();
        assertNotNull(possibles);
        assertEquals(9, possibles.length);
        assertEquals(VAL_1, possibles[0]);
    }
    
    // --- Couverture approfondie de verifierBornes (Branches de conditions complexes) ---

    @Test(expected = IllegalArgumentException.class)
    public void testSetValeur_X_TropGrand() { 
        grille.setValeur(9, 0, VAL_1); 
    } 

    @Test(expected = IllegalArgumentException.class)
    public void testGetValeur_X_Negatif() { 
        grille.getValeur(-1, 0); 
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetValeur_Y_Negatif() { 
        grille.getValeur(0, -1); 
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetValeur_Y_TropGrand() { 
        grille.getValeur(0, 9); 
    }

    @Test(expected = HorsBornesException.class)
    public void testIsValeurInitiale_HorsBornes() throws HorsBornesException {
        grille.isValeurInitiale(9, 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSetValeur_ValeurInvalide() {
        grille.setValeur(0, 0, VAL_BAD);
    }
    
    // --- Couverture des états de transition de setValeur (Branches combinatoires) ---
    
    @Test
    public void testSetValeur_CaseVideVersRemplie() throws HorsBornesException {
        // Condition : Vide ET Nouvelle valeur Non Vide -> isInitial devient true
        grille.setValeur(0, 0, VAL_1); 
        assertTrue(grille.isValeurInitiale(0, 0));
    }
    
    @Test
    public void testSetValeur_CaseDejaRemplieVersAutre() throws HorsBornesException {
        // Condition : Non Vide ET Nouvelle valeur Non Vide
        grille.setValeur(0, 0, VAL_1); 
        grille.setValeur(0, 0, VAL_9); 
        assertTrue(grille.isValeurInitiale(0, 0)); 
    }
    
    @Test
    public void testSetValeur_CaseVideVersVide() throws HorsBornesException {
        // Condition : Vide ET Nouvelle valeur Vide
        assertFalse(grille.isValeurInitiale(0, 1));
        grille.setValeur(0, 1, VAL_VIDE); 
        assertFalse(grille.isValeurInitiale(0, 1));
    }

    @Test
    public void testSetValeur_CaseRemplieVersVide() throws HorsBornesException {
        // Condition : Non Vide ET Nouvelle valeur Vide
        grille.setValeur(0, 0, VAL_1);
        grille.setValeur(0, 0, VAL_VIDE);
        assertEquals(VAL_VIDE, grille.getValeur(0, 0));
    }
    
    // --- Tests de complétion de la grille ---
    
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

    // --- Tests sur la méthode isPossible et validations des contraintes Sudoku ---

    @Test
    public void testIsPossible_Succes() throws Exception {
        assertTrue(grille.isPossible(0, 0, VAL_1)); 
    }
    
    @Test
    public void testIsPossible_ValeurVide() throws Exception {
        assertTrue(grille.isPossible(0, 0, VAL_VIDE)); 
    }
    
    @Test(expected = ValeurInterditeException.class)
    public void testIsPossible_ValeurInterdite() throws Exception {
        grille.isPossible(0, 0, VAL_BAD); 
    }

    @Test
    public void testIsPossible_VioleeParLigne() throws Exception {
        grille.setValeur(0, 5, VAL_1);
        assertFalse(grille.isPossible(0, 0, VAL_1));
    }

    @Test
    public void testIsPossible_VioleeParColonne() throws Exception {
        grille.setValeur(5, 0, VAL_1);
        assertFalse(grille.isPossible(0, 0, VAL_1));
    }

    @Test
    public void testIsPossible_VioleeParBloc() throws Exception {
        grille.setValeur(1, 1, VAL_1); 
        assertFalse(grille.isPossible(0, 0, VAL_1)); 
    }
    
    @Test
    public void testIsPossible_SurCaseDejaRemplieRestauration() throws Exception {
        grille.setValeur(0, 0, VAL_1); 
        assertTrue(grille.isPossible(0, 0, VAL_9)); 
        assertEquals(VAL_1, grille.getValeur(0, 0)); 
    }

    // --- Tests sur la méthode métier alternative setValeur1 ---
    
    @Test
    public void testSetValeur1_Succes() throws Exception {
        grille.setValeur1(1, 1, VAL_9);
        assertEquals(VAL_9, grille.getValeur(1, 1));
    }
    
    @Test
    public void testSetValeur1_SupprimerValeur() throws Exception {
        grille.setValeur1(1, 1, VAL_9); 
        grille.setValeur1(1, 1, VAL_VIDE); 
        assertEquals(VAL_VIDE, grille.getValeur(1, 1));
    }
    
    @Test(expected = ValeurInitialeModificationException.class)
    public void testSetValeur1_ModificationInitialeInterdite() throws Exception {
        grille.setValeur(0, 0, VAL_1); 
        grille.setValeur1(0, 0, VAL_9); 
    }

    @Test(expected = ValeurInterditeException.class)
    public void testSetValeur1_ValeurInterdite() throws Exception {
        grille.setValeur1(0, 0, VAL_BAD);
    }

    @Test(expected = ValeurImpossibleException.class)
    public void testSetValeur1_ValeurImpossibleSudoku() throws Exception {
        grille.setValeur1(0, 0, VAL_1); 
        grille.setValeur1(0, 1, VAL_1); 
    }
}
