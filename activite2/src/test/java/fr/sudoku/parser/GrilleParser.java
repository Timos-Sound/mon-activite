package fr.miageamiens.isi05.sudoku.parser;

import fr.miageamiens.isi05.sudoku.modele.Grille;
import fr.miageamiens.isi05.sudoku.modele.ValeurDeCase;
import fr.miageamiens.isi05.sudoku.modele.exceptions.GrilleException;
import fr.miageamiens.isi05.sudoku.modele.exceptions.ValeurImpossibleException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GrilleParser {
   static Grille parse(File grilleFile, GrilleFactory grilleFactory, ValeurDeCaseFactory valeurDeGrilleFactory) throws GrilleParserException {
      try {
         InputStream in = new FileInputStream(grilleFile);

         Grille var4;
         try {
            var4 = parse(in, grilleFactory, valeurDeGrilleFactory);
         } catch (Throwable var7) {
            try {
               in.close();
            } catch (Throwable var6) {
               var7.addSuppressed(var6);
            }

            throw var7;
         }

         in.close();
         return var4;
      } catch (IOException e) {
         throw new GrilleParserException(e);
      }
   }

   static Grille parse(String resourcePath, GrilleFactory grilleFactory, ValeurDeCaseFactory valeurDeGrilleFactory) throws GrilleParserException {
      try {
         InputStream in = GrilleParser.class.getResourceAsStream(resourcePath);

         Grille var4;
         try {
            var4 = parse(in, grilleFactory, valeurDeGrilleFactory);
         } catch (Throwable var7) {
            if (in != null) {
               try {
                  in.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }
            }

            throw var7;
         }

         if (in != null) {
            in.close();
         }

         return var4;
      } catch (IOException e) {
         throw new GrilleParserException(e);
      }
   }

   static Grille parse(InputStream in, GrilleFactory grilleFactory, ValeurDeCaseFactory valeurDeGrilleFactory) throws GrilleParserException {
      try {
         BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

         Grille var20;
         try {
            String line = reader.readLine();
            if (line == null || line.isEmpty()) {
               throw new IllegalArgumentException("pas de première ligne");
            }

            int dimension = line.length() - 1;
            char vide = line.charAt(0);
            Map<Character, ValeurDeCase> valeurDeCaseHashMap = new HashMap();

            for(int i = 1; i < line.length(); ++i) {
               char value = line.charAt(i);
               if (value != vide) {
                  if (valeurDeCaseHashMap.containsKey(value)) {
                     throw new IllegalArgumentException("valeur possible dupliquée : " + value);
                  }

                  valeurDeCaseHashMap.put(value, valeurDeGrilleFactory.create(value));
               }
            }

            if (valeurDeCaseHashMap.size() != dimension) {
               throw new IllegalArgumentException("pas le bon nombre de valeurs possibles");
            }

            List<ValeurDeCase> valeursDeCasesAutorisees = new ArrayList(valeurDeCaseHashMap.values());
            ValeurDeCase[][] grilleTab = new ValeurDeCase[dimension][dimension];

            for(int i = 0; i < dimension; ++i) {
               line = reader.readLine();
               if (line == null || line.length() != dimension) {
                  throw new IOException("pas le bon nombre sur la ligne : " + line);
               }

               for(int j = 0; j < dimension; ++j) {
                  char c = line.charAt(j);
                  if (c != vide) {
                     ValeurDeCase valeurDeCase = (ValeurDeCase)valeurDeCaseHashMap.get(c);
                     if (valeurDeCase == null) {
                        throw new ValeurImpossibleException(c + " impossible");
                     }

                     grilleTab[i][j] = valeurDeCase;
                  }
               }
            }

            var20 = grilleFactory.create(valeursDeCasesAutorisees, grilleTab);
         } catch (Throwable var15) {
            try {
               reader.close();
            } catch (Throwable var14) {
               var15.addSuppressed(var14);
            }

            throw var15;
         }

         reader.close();
         return var20;
      } catch (GrilleException | IOException e) {
         throw new GrilleParserException(e);
      }
   }
}
