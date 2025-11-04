
package celebrites;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.HashMap;


public class Celebrities {
	public static void main(String[] args) {
		String[] guestsList = {"Albert","Benedicte","CHristophe","Delphine","Edouard","Francoise","Gaston","Heloise"};
		Map <Integer, int []> guestRelations = new HashMap<Integer, int[]>();
		Map <Integer, boolean []> guestsKnowledge = new HashMap <Integer, boolean[]>();
		
        guestRelations.put(0, new int[]{1,4,5});          
        guestRelations.put(1, new int[]{2,4,5});           
        guestRelations.put(2, new int[]{1,3,4,5});         
        guestRelations.put(3, new int[]{0,1,4,5,7});       
        guestRelations.put(4, new int[]{5});              
        guestRelations.put(5, new int[]{4});              
        guestRelations.put(6, new int[]{1,2,4,5,7});      
        guestRelations.put(7, new int[]{1,3,4,5,6});
        
        guestsKnowledge.put(0, new boolean[8]);
        guestsKnowledge.put(1, new boolean[8]);
        guestsKnowledge.put(2, new boolean[8]);
        guestsKnowledge.put(3, new boolean[8]);
        guestsKnowledge.put(4, new boolean[8]);
        guestsKnowledge.put(5, new boolean[8]);
        guestsKnowledge.put(6, new boolean[8]);
        guestsKnowledge.put(7, new boolean[8]);
        
        
        //System.out.println(getCelebrities(guestRelations,guestsKnowledge));
        
        isKnownByEveryOne(4,guestRelations,guestsKnowledge);
        
        guestsKnowledge.get(4);
        
        for (boolean isKnown : guestsKnowledge.get(4)) {
        	System.out.println(isKnown);
        	
        }
        
        
     
		
	}
	
	

	private static Map <Integer, boolean []> isKnownByEveryOne(int guestId,Map<Integer, int[]> guestRelations, Map <Integer, boolean []> guestsKnowledge ) {
		// On boucle sur les invités
		for (Entry<Integer, int []> relation : guestRelations.entrySet()) {
				// On s'assure de ne pas boucler sur l'invité que l'on traite en param de la methode
			 	if(relation.getKey() != guestId) {
			 		// Pour chaque invité on recupere la liste des invités qu'il connait
					int[]knownGuests = relation.getValue();
					
						
						boolean[] arr = new boolean[8];
						arr = guestsKnowledge.get(relation.getKey());
						for (int i=0; i<knownGuests.length; i++) {
							 
							
							
							if(knownGuests[i] == guestId) {									
								arr[knownGuests[i]] = true;
								guestsKnowledge.put(guestId, arr);
							}							
						
						}
							
			}
			 	
		}
		return guestsKnowledge;
	}



	public static ArrayList <Integer> getCelebrities(Map<Integer, int[]> guestRelations, Map<Integer,boolean[]> guestsKnowledge) {
		Boolean isKnown = false;
		Boolean isKnownByAll = false;
		ArrayList <Integer> celebList = new ArrayList<Integer>();
		for (Entry<Integer, int []> relation : guestRelations.entrySet()) {					
        	 int[] knownGuests = relation.getValue();
        	 for (Entry<Integer, int []> relation2 : guestRelations.entrySet()){
        		 isKnown = false;
        		 if(relation.getKey() != relation2.getKey()) {
        			 for (int i=0; i<knownGuests.length; i++) {
        				 if(knownGuests[i] == relation2.getKey()) {
        					 isKnown = true;
        					 break;
        				 }        				
        			 }
        		 }
        		 
        	 }
        	 if(isKnown) isKnownByAll = true;
        	 if(isKnownByAll == true) celebList.add(relation.getKey());
        }
		return celebList;
	}
	
	
}

