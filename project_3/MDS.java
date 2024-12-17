package mxn220038;

import java.util.*;

//If you want to create additional classes, place them in this file as subclasses of MDS

public class MDS {
	
	// Add fields of MDS here
	
	public Map<Integer, Item> items;
	public Map<Integer, Set<Integer>> descriptionMap;
	
	 // Constructors
	public MDS() {
		items = new HashMap<>();
	    descriptionMap = new HashMap<>();
	}
	
	// helper class to store item details
	public static class Item {
	    int id;
	    int price;
	    List<Integer> description;

	    Item(int id, int price, List<Integer> description) {
	        this.id = id;
	        this.price = price;
	        this.description = description;
	    }
	}

	 // Public methods of MDS. Do not change their signatures.

	 public int insert(int id, int price, java.util.List<Integer> list) {
		
		    // seeing if the item with the given id already exists
		    boolean isNew = !items.containsKey(id);

		    if (isNew || (list != null && !list.isEmpty())) {
		        // if the item exists, remove old descriptions 
		        if (!isNew) {
		            Item existingItem = items.get(id);
		            for (int num : existingItem.description) {
		                descriptionMap.get(num).remove(id);
		                // if no ids are left for that description, remove the entry from descriptionMap 
		                if (descriptionMap.get(num).isEmpty()) {
		                    descriptionMap.remove(num);
		                }
		            }
		        }

		        // create a new description set
		        List<Integer> description = (list != null && !list.isEmpty()) 
		            ? new ArrayList<>(list) 
		            : new ArrayList<>();

		        // create or update the item
		        Item newItem = new Item(id, price, description);
		        items.put(id, newItem);

		        // update the descriptionMap for the new descriptions
		        for (int num : description) {
		            descriptionMap.putIfAbsent(num, new HashSet<>());
		            descriptionMap.get(num).add(id);
		        }
		    } else {
		        // update the price of the existing item
		        Item existingItem = items.get(id);
		        existingItem.price = price;
		    }

		    return isNew ? 1 : 0;
	 }
	
	 // b. Find(id): return price of item with given id (or 0, if not found).
	 
	 public int find(int id) {
		// check if the item exists in the items map
	    if (items.containsKey(id)) {
	        // return price
	        return items.get(id).price;
	    }
	    // if the item does not exist, return 0
	    return 0;
	 }
	
	 /* 
	    c. Delete(id): delete item from storage.  Returns the sum of the
	    ints that are in the description of the item deleted,
	    or 0, if such an id did not exist.
	 */
	 
	 public int delete(int id) {

		    // see if the item exists in the items map
		    if (!items.containsKey(id)) {
		        return 0; //does not exist
		    }

		    // get and remove the item from the items map
		    Item item = items.remove(id);

		    // calculate the sum of the integers in the item's description
		    int descriptionSum = 0;
		    for (int num : item.description) {
		        descriptionSum += num;
		        // remove id from descriptionMap for this number
		        descriptionMap.get(num).remove(id);

		        // if no more ids are associated with this number, remove the number from descriptionMap
		        if (descriptionMap.get(num).isEmpty()) {
		            descriptionMap.remove(num);
		        }
		    }

		    // return the sum of description
		    return descriptionSum; 
	 }
	
	 /* 
	    d. FindMinPrice(n): given an integer, find items whose description
	    contains that number (exact match with one of the ints in the
	    item's description), and return lowest price of those items.
	    Return 0 if there is no such item.
	 */
	 public int findMinPrice(int n) {
		    // check if the number exists in descriptionMap
		    if (!descriptionMap.containsKey(n)) {
		        return 0; // Return 0 if no items contain this number
		    }

		    // get the set of ids associated with that number
		    Set<Integer> ids = descriptionMap.get(n);

		    // Find the minimum price among the items
		    int minPrice = Integer.MAX_VALUE;
		    for (int id : ids) {
		        int price = items.get(id).price;
		        minPrice = Math.min(minPrice, price);
		    }

		    // if no prices were found , return 0
		    return minPrice == Integer.MAX_VALUE ? 0 : minPrice;
	 }
	
	 /* 
	    e. FindMaxPrice(n): given an integer, find items whose description
	    contains that number, and return highest price of those items.
	    Return 0 if there is no such item.
	 */
	 public int findMaxPrice(int n) {
		 
		    // check if the number exists in descriptionMap
		    if (!descriptionMap.containsKey(n)) {
		        return 0;
		    }

		    // get the set of ids associated with the number
		    Set<Integer> ids = descriptionMap.get(n);

		    // find the maximum price of all the items
		    int maxPrice = Integer.MIN_VALUE;
		    for (int id : ids) {
		        int price = items.get(id).price;
		        maxPrice = Math.max(maxPrice, price);
		    }

		    // if no prices were found, return 0
		    return maxPrice == Integer.MIN_VALUE ? 0 : maxPrice;
	 }
	
	 /* 
	    f. FindPriceRange(n,low,high): given int n, find the number
	    of items whose description contains n, and in addition,
	    their prices fall within the given range, [low, high].
	 */
	 public int findPriceRange(int n, int low, int high) {
		    // see if the number exists in descriptionMap
		    if (!descriptionMap.containsKey(n)) {
		        return 0;
		    }

		    // get the set of ids associated with the number
		    Set<Integer> ids = descriptionMap.get(n);

		    // count the items with prices in the range, from low to high
		    int count = 0;
		    for (int id : ids) {
		        int price = items.get(id).price;
		        if (price >= low && price <= high) {
		            count++;
		        }
		    }

		    // return the count of items
		    return count;
	 }
	
	 /*
	   g. RemoveNames(id, list): Remove elements of list from the description of id.
	   It is possible that some of the items in the list are not in the
	   id's description.  Return the sum of the numbers that are actually
	   deleted from the description of id.  Return 0 if there is no such id.
	 */
	 public int removeNames(int id, java.util.List<Integer> list) {
		    // Check if the item exists in the items map
		    if (!items.containsKey(id)) {
		        return 0; // Return 0 if the item does not exist
		    }

		    // Retrieve the item
		    Item item = items.get(id);
          List<Integer> descToRemove = new ArrayList<>();

		    // Calculate the sum of the numbers actually removed
		    int removedSum = 0;
		    for (int num : list) {
		        if (item.description.contains(num)) { // Remove num from the item's description
		            removedSum += num;
                  descToRemove.add(num);

		            // Update the descriptionMap
		            Set<Integer> ids = descriptionMap.get(num);
		            ids.remove(id);
		            // If no more IDs are associated with the number, remove it from descriptionMap
		            if (ids.isEmpty()) {
		                descriptionMap.remove(num);
		            }
		        }
		    }

          // Remove the numbers from the item's description
          item.description.removeAll(descToRemove);

		    // Return the sum of removed numbers
		    return removedSum;
	 }
}