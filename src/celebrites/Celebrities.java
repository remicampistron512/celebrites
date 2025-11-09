package celebrites;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.HashMap;
import java.util.stream.*;
import java.util.logging.*;

public class Celebrities {
  private static final Logger log = Logger.getLogger(Celebrities.class.getName());

  public static void main(final String[] args) {
    final String[] guestsList = {
      "Albert", "Benedicte", "CHristophe", "Delphine", "Edouard", "Francoise", "Gaston", "Heloise"
    };

    // Permet au logger de ne pas afficher le timestamp
    System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s %3$s - %5$s%6$s%n");
    Logger root = Logger.getLogger("");
    root.setLevel(Level.INFO);
    for (Handler h : root.getHandlers()) {
      h.setLevel(Level.INFO);
      h.setFormatter(new SimpleFormatter());
    }

    // hashmap contenant les connaisances de chaque invité
    final Map<Integer, int[]> guestRelations = new HashMap<Integer, int[]>();
    guestRelations.put(0, new int[] {1, 4, 5});
    guestRelations.put(1, new int[] {2, 4, 5});
    guestRelations.put(2, new int[] {1, 3, 4, 5});
    guestRelations.put(3, new int[] {0, 1, 4, 5, 7});
    guestRelations.put(4, new int[] {5,1});
    guestRelations.put(5, new int[] {4});
    guestRelations.put(6, new int[] {1, 2, 4, 5, 7});
    guestRelations.put(7, new int[] {1, 3, 4, 5, 6});

    /* hashmap determinant si l'invité est connu ou pas de l'invité correspondant.
     * l'indice du tableau de booleen correspond à l'indice du tableau des invités
     *  (ex: true en 3eme pos signifie que le 3eme invité le connait
     */

    final int n = guestsList.length;
    final Map<Integer, boolean[]> guestsKnowledge = new HashMap<>(n);
    for (int i = 0; i < n; i++) {
      guestsKnowledge.put(i, new boolean[n]);
    }

    for (final Map.Entry<Integer, boolean[]> guest : guestsKnowledge.entrySet()) {
      Celebrities.getKnowers(guest.getKey(), guestRelations, guestsKnowledge);
    }

    for (final Map.Entry<Integer, boolean[]> guest : guestsKnowledge.entrySet()) {
      log.log(Level.INFO, "celebrities={0}", Arrays.toString(guest.getValue()));
    }

    ArrayList<Integer> celebritiesCandidates =
        Celebrities.getCelebritiesCandidates(guestsKnowledge);
    log.log(Level.INFO, "celebrities candidates list={0}", celebritiesCandidates);

    log.log(
        Level.INFO,
        "celebrities list={0}",
        onlyKnowCelebrities(celebritiesCandidates, guestRelations));
  }

  private static ArrayList<Integer> onlyKnowCelebrities(
      ArrayList<Integer> celebritiesCandidates, Map<Integer, int[]> guestRelations) {

    boolean changed;
    // boucle de recursion
    do {
      // flag pour savoir si la liste a été changé
      changed = false;
      // le tableau des invités que l'on va enlever de la liste a chaque iteration
      final ArrayList<Integer> toRemove = new ArrayList<>();
      // on parcourt la liste des candidats
      for (int candidate : celebritiesCandidates) {
        // récupère les relations du candidat
        int[] knownList = guestRelations.get(candidate);
        // flag pour determine s'il ne connait que des candidats
        boolean onlyKnowsCandidates = true;
        for (int known : knownList) {
          // si la connaissance n'est pas dans la liste alors cette connaissance
          // n'est pas une célébrité, dès lors le candidat n'est plus une célébrité
          // la connaissance est un candidat, on passe à la prochaine iteration
          if (known != candidate && !celebritiesCandidates.contains(known)) {
            onlyKnowsCandidates = false;
            break; // single control-flow statement
          }

        } if (!onlyKnowsCandidates) {
          // le candidat ne connait pas que des célébrités, il est ajouté à toRemove
          // pour être retiré par la suite
          toRemove.add(candidate);
        }
      }

      // On vide la liste des candidats si il y a toujours des candidats
      if (!toRemove.isEmpty()) {
        celebritiesCandidates.removeAll(toRemove);
        // on reboucle vu que la liste de candidats a changé, il faut revérifier
        changed = true;
      }
    } while (changed);

    return celebritiesCandidates;
  }

  private static ArrayList<Integer> getCelebritiesCandidates(
      final Map<Integer, boolean[]> guestsKnowledge) {
    /* Si la personne est connue de tous, c'est une célébrité donc on parcourt la hashmap
    guestsKnowledge pour rechercher les tableaux complètement true. */
    final ArrayList<Integer> celebritiesCandidatesList = new ArrayList<>();
    for (final Map.Entry<Integer, boolean[]> guest : guestsKnowledge.entrySet()) {
      final int guestId = guest.getKey();
      final boolean[] knownBy = guest.getValue();

      boolean allKnown =
          IntStream.range(0, knownBy.length).filter(i -> i != guestId).allMatch(i -> knownBy[i]);

      if (allKnown) {
        celebritiesCandidatesList.add(guestId);
      }
    }

    return celebritiesCandidatesList;
  }

  private static void getKnowers(
      final int guestId,
      final Map<Integer, int[]> guestRelations,
      final Map<Integer, boolean[]> guestsKnowledge) {
    // On boucle sur les invités
    final boolean[] arr = guestsKnowledge.getOrDefault(guestId, new boolean[8]);
    for (final Map.Entry<Integer, int[]> relation : guestRelations.entrySet()) {
      final int knower = relation.getKey();
      // On s'assure de ne pas boucler sur l'invité que l'on traite en param de la methode
      if (knower == guestId) {

        continue;
      }
      // Pour chaque invité, on récupère la liste des invités qu'il connait
      final int[] knownGuests = relation.getValue();

      for (final Integer knownGuest : knownGuests) {
        if (knownGuest == guestId) {
          log.log(Level.INFO, "knownGuest={0}", knownGuest);
          arr[knower] = true;
          break;
        }
      }
    }
    guestsKnowledge.put(guestId, arr);
  }
}
