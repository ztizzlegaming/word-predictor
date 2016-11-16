/**
 * HashData class that stores a prefix and the 3 most popular words for that prefix.
 * Two HashData objects are equal if they have the same prefix.
 *
 * @author Jordan Turley
 */
public class HashData implements Comparable<HashData> {
	private String prefix; //The prefix
	private ASL3<WordChoice> words; //The three most popular words starting with that prefix

	/**
	 * Creates a new HashData with a given prefix, and an empty ASL3, for words that start with that prefix
	 * @param prefix The prefix of the word for this HashData
	 */
	public HashData(String prefix) {
		this.prefix = prefix;
		this.words = new ASL3<WordChoice>();
	}

	/**
	 * @return The prefix of this HashData
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Adds a WordChoice (A word and a count) to the ASL3 of this HashData, if the WordChoice isn't already in the list
	 * @param wc The WordChoice to add to the ASL3
	 */
	public void addWordChoice(WordChoice wc) {
		if (!words.contains(wc)) {
			words.addBd(wc);
		}
	}

	/**
	 * @return The ASL3 of the 3 most popular words for this prefix
	 */
	public ASL3<WordChoice> getWords() {
		return words;
	}

	/**
	 * Checks if this HashData is equal to another Object
	 * @param o The object to compare it to
	 * @return True or false, if they are equal
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof HashData) {
			HashData hd = (HashData) o;
			return prefix.equals(hd.getPrefix());
		}
		return false;
	}

	/**
	 * Compares two HashData objects, depending on the strings they contain
	 * @param hd The HashData to compare this HashData to
	 * @return A negative number if this one comes before hd, 0 if they are equal, or positive if hd comes before this
	 */
	public int compareTo(HashData hd) {
		return prefix.compareTo(hd.getPrefix());
	}

	/**
	 * @return A string representation of this HashData, with its prefix and the 3 most popular words that go with it.
	 */
	public String toString() {
		return prefix + "\n" + words;
	}
}
