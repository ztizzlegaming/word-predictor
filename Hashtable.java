import java.util.List;
import java.util.ArrayList;

import ch06.lists.RefSortedList;

/**
 * Hashtable class, stores a table of RefSortedLists of HashData
 * Strings are hashed by using the string's own hashCode method
 *
 * @author Jordan Turley
 */
public class Hashtable {
	private final int SIZE;

	private List<RefSortedList<HashData>> table;

	/**
	 * Creates a new Hashtable with a given max length
	 * @param length The length of the hashtable
	 */
	public Hashtable(int length) {
		SIZE = length;
		table = new ArrayList<RefSortedList<HashData>>(SIZE);

		for (int i1 = 0; i1 < SIZE; i1++) {
			table.add(i1, new RefSortedList<HashData>());
		}
	}

	/**
	 * Hashes a string, using it's hashCode method.
	 * Takes the absolute value, because sometimes hashCode() returns a negative number
	 * @param s The string to hash
	 * @return An integer for the hash of the given string
	 */
	private int hash(String s) {
		return Math.abs(s.hashCode() % SIZE);
	}

	/**
	 * Checks if a given String (prefix) is in the table
	 * @param s The string to check if it is in the table
	 * @return True or false, if the string is in the table
	 */
	public boolean contains(String s) {
		int idx = hash(s);
		RefSortedList<HashData> list = table.get(idx);
		HashData tempData = new HashData(s);
		return list.contains(tempData);
	}

	/**
	 * Adds a prefix to the Hashtable, with the WordChoice for the full word of the prefix
	 * @param prefix The prefix of the word to add
	 * @param wc The WordChoice for the full word and it's count
	 */
	public void add(String prefix, WordChoice wc) {
		int idx = hash(prefix);
		RefSortedList<HashData> list = table.get(idx);
		HashData temp = new HashData(prefix);
		if (list.contains(temp)) {
			HashData realData = list.get(temp);
			realData.addWordChoice(wc);
		} else {
			list.add(temp);
			temp.addWordChoice(wc);
		}
	}

	/**
	 * Gets the 3 most popular words for a given prefix
	 * @param prefix The prefix to get the words for
	 * @return An ASL3 of the three most popular words for the given prefix. An empty list is returned if the word is not found.
	 */
	public ASL3<WordChoice> get(String prefix) {
		int idx = hash(prefix);
		RefSortedList<HashData> list = table.get(idx);
		HashData temp = new HashData(prefix);
		HashData realData = list.get(temp);
		if (realData == null) {
		    return new ASL3<WordChoice>();
		}
		return realData.getWords();
	}

	/**
	 * Calculates the average size of each RefSortedList in the ArrayList
	 * I just did this out of curiosity, it isn't actually used for anything.
	 * @return The average size of each list in the ArrayList
	 */
	public double getAverageBucketSize() {
		int count = 0;
		for (RefSortedList<HashData> list : table) {
			count += list.size();
		}
		return (double) count / SIZE;
	}

	/**
	 * @return A String containing everything in this Hashtable
	 */
	public String toString() {
		String result = "";
		for (RefSortedList<HashData> rsl : table) {
			rsl.reset();
			for (int i1 = 0; i1 < rsl.size(); i1++) {
				result += rsl;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		WordChoice test3 = new WordChoice("the", 1);
		WordChoice test1 = new WordChoice("dog", 2);
		WordChoice test2 = new WordChoice("you", 4);

		Hashtable table = new Hashtable(100);
		table.add("the", test3);
		table.add("do", test1);
		table.add("you", test2);
		table.add("the", test3);
		table.add("y", test2);
		System.out.println(table);
	}
}
