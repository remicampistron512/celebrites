
package celebrites;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;


public class Celebrities {
    public static void main(final String[] args) {
        final String[] guestsList = {"Albert", "Benedicte", "CHristophe", "Delphine", "Edouard", "Francoise", "Gaston", "Heloise"};

        // hashmap contenant les connaisances de chaque invité
        final Map<Integer, int[]> guestRelations = new HashMap<Integer, int[]>();
        guestRelations.put(0, new int[]{1, 4, 5});
        guestRelations.put(1, new int[]{2, 4, 5});
        guestRelations.put(2, new int[]{1, 3, 4, 5});
        guestRelations.put(3, new int[]{0, 1, 4, 5, 7});
        guestRelations.put(4, new int[]{5,1});
        guestRelations.put(5, new int[]{4});
        guestRelations.put(6, new int[]{1, 2, 4, 5, 7});
        guestRelations.put(7, new int[]{1, 3, 4, 5, 6});

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
            System.out.println(Arrays.toString(guest.getValue()));

        }
        System.out.println(Celebrities.getCelebrities(guestsKnowledge));

    }

    private static ArrayList<Integer> getCelebrities(final Map<Integer, boolean[]> guestsKnowledge) {
        /* Si la personne est connue de tous, c'est une célébrité donc on parcourt la hashmap
         guestsKnowledge pour rechercher les tableaux complètement true. */
        final ArrayList<Integer> celebritiesList = new ArrayList<>();
        for(final Map.Entry<Integer, boolean[]> guest : guestsKnowledge.entrySet()) {
            final int guestId = guest.getKey();
            final boolean[] knownBy = guest.getValue();

            boolean allKnown = true;
            for (final boolean known : knownBy) {
                if (!known) {
                    allKnown = false;
                    break;
                }
            }

            if (allKnown) {
                celebritiesList.add(guestId);
            }
        }

        return celebritiesList;


    }


    private static void getKnowers(final int guestId, final Map<Integer, int[]> guestRelations, final Map<Integer, boolean[]> guestsKnowledge) {
        // On boucle sur les invités
        final boolean[] arr = guestsKnowledge.getOrDefault(guestId, new boolean[8]);
        for (final Map.Entry<Integer, int[]> relation : guestRelations.entrySet()) {
            final int knower = relation.getKey();
            // On s'assure de ne pas boucler sur l'invité que l'on traite en param de la methode
            if (knower == guestId){
                arr[knower] = true;
                continue;
            }
            // Pour chaque invité, on récupère la liste des invités qu'il connait
            final int[] knownGuests = relation.getValue();

            for (final int knownGuest : knownGuests) {
                if (knownGuest == guestId) {
                    System.out.println(knownGuest);
                    arr[knower] = true;
                    break;
                }
            }
        }
        guestsKnowledge.put(guestId, arr);
    }
}

