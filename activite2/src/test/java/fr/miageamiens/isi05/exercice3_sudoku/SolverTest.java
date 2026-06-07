import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.IOException;

public class SolverTest {

    private Solveur solveur;
    private GrilleFactory grilleFactory;

    @BeforeEach
    public void setUp() {
        solveur = new Resolveur();
        grilleFactory = new GrilleFactoryImpl();
    }

    @Test
    public void testSolveGrilleMoyen() {
        testSolveGrille("/sudoku16-moyen-complete.txt", true);
    }

    @Test
    public void testSolveGrilleExpert() {
        testSolveGrille("/sudoku16-expert.txt", true);
    }

    @Test
    public void testSolveGrilleDebutant() {
        testSolveGrille("/sudoku16-debutant.txt", true);
    }

    @Test
    public void testSolveGrille25() {
        testSolveGrille("/sudoku25-i.txt", true);
    }

    // @Test
    // public void testSolveGrilleImpossible() {
    //     testSolveGrille("/sudoku16-impossible.txt", false);
    // }

    private void testSolveGrille(String filePath, boolean expectedResult) {
        try (InputStream is = getClass().getResourceAsStream(filePath)) {
            if (is == null) {
                fail("Le fichier " + filePath + " n'a pas été trouvé");
            }
            Grille grille = GrilleParser.parse(is, grilleFactory);
            assertEquals(expectedResult, solveur.solve(grille));
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception lors du parsing de la grille");
        } catch (SolveurException e) {
            e.printStackTrace();
            fail("Exception lors de la résolution de la grille");
        } catch (ElementInterditException | ValeurInitialeModificationException | HorsBornesException | ValeurImpossibleException e) {
            e.printStackTrace();
            fail("Exception lors de la création de la grille");
        }
    }
}
